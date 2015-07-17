package db.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

/**
* @ClassName: JdbcUtils2
* @Description: 数据库连接工具类
* @author: 
* @date: 2014-10-4 下午6:04:36
*
*/ 
public class JdbcUtils2 {
    
	private static DataSource dataSource = null;
	
	//使用ThreadLocal存储当前线程中的Connection对象
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
    
	static{
//		String driver="com.mysql.jdbc.Driver";
//        String url="jdbc:mysql://10.10.140.96:3306/test";
//        String username="base";
//        String password="123456";
        
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties");
        Properties prop = new Properties();
            try {
				prop.load(in);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            String driver = prop.getProperty("driver");
            String url = prop.getProperty("url");
            String username = prop.getProperty("username");
            String password = prop.getProperty("password");

        dataSource = setupDataSource(driver,url,username,password);
        ((BasicDataSource)dataSource).setJmxName("jmx.example:type=mysql");
	}
	public static DataSource setupDataSource(String driver,String connectURI,String username,String password) {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(driver);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setUrl(connectURI);
        return ds;
    }
	
    
    
    
    /**
    * @Method: getConnection
    * @Description: 从数据源中获取数据库连接
    * @Anthor:
    * @return Connection
    * @throws SQLException
    */ 
    public static Connection getConnection() throws SQLException{
        //从当前线程中获取Connection
        Connection conn = threadLocal.get();
        if(conn==null){
            //从数据源中获取数据库连接
            conn = getDataSource().getConnection();
            //将conn绑定到当前线程
            threadLocal.set(conn);
        }
        return conn;
    }
    
    /**
    * @Method: startTransaction
    * @Description: 开启事务
    * @Anthor:
    *
    */ 
    public static void startTransaction(){
        try{
            Connection conn =  threadLocal.get();
            if(conn==null){
                conn = getConnection();
                 //把 conn绑定到当前线程上
                threadLocal.set(conn);
            }
            //开启事务
            conn.setAutoCommit(false);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
    * @Method: rollback
    * @Description:回滚事务
    * @Anthor:
    *
    */ 
    public static void rollback(){
        try{
            //从当前线程中获取Connection
            Connection conn = threadLocal.get();
            if(conn!=null){
                //回滚事务
                conn.rollback();
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
    * @Method: commit
    * @Description:提交事务
    * @Anthor:
    *
    */ 
    public static void commit(){
        try{
            //从当前线程中获取Connection
            Connection conn = threadLocal.get();
            if(conn!=null){
                //提交事务
                conn.commit();
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
    * @Method: close
    * @Description:关闭数据库连接(注意，并不是真的关闭，而是把连接还给数据库连接池)
    * @Anthor:
    *
    */ 
    public static void close(){
        try{
            //从当前线程中获取Connection
            Connection conn = threadLocal.get();
            if(conn!=null){
                conn.close();
                 //解除当前线程上绑定conn
                threadLocal.remove();
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
    * @Method: getDataSource
    * @Description: 获取数据源
    * @Anthor:
    * @return DataSource
    */ 
    public static DataSource getDataSource(){
        //从数据源中获取数据库连接
        return dataSource;
    }
}