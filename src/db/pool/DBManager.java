package db.pool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DBManager {
	private final static Log log = LogFactory.getLog(DBManager.class);
	private final static ThreadLocal conns = new ThreadLocal();
	private static boolean show_sql = true;

	public final static Connection getConnection() throws SQLException {
		Connection conn = (Connection) conns.get();
		if (conn == null || conn.isClosed()) {
			// 这里使用我定义的一个简单的 ConnectionProvider 替代 dataSource 获取Connection
			conn = null;//todo ConnectionProvider.getConnection();
			conns.set(conn);
		}
		return (show_sql && !Proxy.isProxyClass(conn.getClass())) ? new _DebugConnection(
				conn).getConnection() : conn;
	}

	public final static void closeConnection() {
		Connection conn = (Connection) conns.get();
		try {
			if (conn != null && !conn.isClosed()) {
				conn.setAutoCommit(true);
				conn.close();
			}
		} catch (SQLException e) {
			log.error("Unabled to close connection!!! ", e);
		}
		conns.set(null);
	}

	static class _DebugConnection implements InvocationHandler {
		private final static Log log = LogFactory
				.getLog(_DebugConnection.class);
		private Connection conn = null;

		public _DebugConnection(Connection conn) {
			this.conn = conn;
		}

		public Connection getConnection() {
			return (Connection) Proxy.newProxyInstance(conn.getClass()
					.getClassLoader(), conn.getClass().getInterfaces(), this);
		}

		public Object invoke(Object proxy, Method m, Object[] args)
				throws Throwable {
			try {
				String method = m.getName();
				if ("prepareStatement".equals(method)
						|| "createStatement".equals(method)) {
					log.info("[SQL] >>> " + args[0]);
				}
				return m.invoke(conn, args);
			} catch (InvocationTargetException e) {
				throw e.getTargetException();
			}
		}
	}
}