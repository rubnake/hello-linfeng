package db.pool;

import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

/**
* @ClassName: JdbcPool
* @Description:��д���ݿ����ӳ�
* @author: �°�����
* @date: 2014-9-30 ����11:07:23
*
*/ 
public class JdbcPool implements DataSource{

    /**
    * @Field: listConnections
    *         ʹ��LinkedList������������ݿ����ӣ�
    *        ����ҪƵ����дList���ϣ���������ʹ��LinkedList�洢���ݿ����ӱȽϺ���
    */ 
    private static LinkedList<Connection> listConnections = new LinkedList<Connection>();
    
    static{
        //�ھ�̬������м���db.properties���ݿ������ļ�
        InputStream in = JdbcPool.class.getClassLoader().getResourceAsStream("db.properties");
        Properties prop = new Properties();
        try {
            prop.load(in);
            String driver = prop.getProperty("driver");
            String url = prop.getProperty("url");
            String username = prop.getProperty("username");
            String password = prop.getProperty("password");
            //���ݿ����ӳصĳ�ʼ����������С
            int jdbcPoolInitSize =Integer.parseInt(prop.getProperty("jdbcPoolInitSize"));
            //�������ݿ�����
            Class.forName(driver);
            for (int i = 0; i < jdbcPoolInitSize; i++) {
                Connection conn = DriverManager.getConnection(url, username, password);
                System.out.println("��ȡ��������" + conn);
                //����ȡ�������ݿ����Ӽ��뵽listConnections�����У�listConnections���ϴ�ʱ����һ����������ݿ����ӵ����ӳ�
                listConnections.add(conn);
            }
            System.out.println("listConnections���ݿ����ӳش�С��" + listConnections.size());
            
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }
    
    public void returnConn(Connection conn){
    	listConnections.add(conn);
    }
    

    /* ��ȡ���ݿ�����
     * @see javax.sql.DataSource#getConnection()
     */
    @Override
    public Connection getConnection() throws SQLException {
        //������ݿ����ӳ��е����Ӷ���ĸ�������0
        if (listConnections.size()>0) {
            //��listConnections�����л�ȡһ�����ݿ�����
            final Connection conn = listConnections.removeFirst();
            
//            //����Connection����Ĵ������
            return (Connection) Proxy.newProxyInstance(Thread
    				.currentThread().getContextClassLoader(), new Class[]{Connection.class}, new InvocationHandler(){
            	//����conn.getClass().getInterfaces() ��Ҫ�滻Ϊ   new Class[]{Connection.class} 
            	
                @Override
                public Object invoke(Object proxy, Method method, Object[] args)
                        throws Throwable {
                    if(!method.getName().equals("close")){
                        return method.invoke(conn, args);//��������ر�
                    }else{
                        //������õ���Connection�����close�������Ͱ�conn�������ݿ����ӳ�
                        listConnections.add(conn);
                        System.out.println(conn + "������listConnections���ݿ����ӳ��ˣ���");
                        System.out.println("listConnections���ݿ����ӳش�СΪ" + listConnections.size());
                        return null;
                    }
                }
            });
        }else {
            throw new RuntimeException("�Բ������ݿ�æ");
        }
    }

    @Override
    public Connection getConnection(String username, String password)
            throws SQLException {
        return null;
    }

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
	
	public static void main(String[] args) {
		JdbcPool pool = new JdbcPool();
		Connection conn;
		try {
			conn = pool.getConnection();
			System.out.println("xxxxxxxxxxxx");
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}
}