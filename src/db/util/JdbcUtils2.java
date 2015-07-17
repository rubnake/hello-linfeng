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
* @Description: ���ݿ����ӹ�����
* @author: 
* @date: 2014-10-4 ����6:04:36
*
*/ 
public class JdbcUtils2 {
    
	private static DataSource dataSource = null;
	
	//ʹ��ThreadLocal�洢��ǰ�߳��е�Connection����
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
    * @Description: ������Դ�л�ȡ���ݿ�����
    * @Anthor:
    * @return Connection
    * @throws SQLException
    */ 
    public static Connection getConnection() throws SQLException{
        //�ӵ�ǰ�߳��л�ȡConnection
        Connection conn = threadLocal.get();
        if(conn==null){
            //������Դ�л�ȡ���ݿ�����
            conn = getDataSource().getConnection();
            //��conn�󶨵���ǰ�߳�
            threadLocal.set(conn);
        }
        return conn;
    }
    
    /**
    * @Method: startTransaction
    * @Description: ��������
    * @Anthor:
    *
    */ 
    public static void startTransaction(){
        try{
            Connection conn =  threadLocal.get();
            if(conn==null){
                conn = getConnection();
                 //�� conn�󶨵���ǰ�߳���
                threadLocal.set(conn);
            }
            //��������
            conn.setAutoCommit(false);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
    * @Method: rollback
    * @Description:�ع�����
    * @Anthor:
    *
    */ 
    public static void rollback(){
        try{
            //�ӵ�ǰ�߳��л�ȡConnection
            Connection conn = threadLocal.get();
            if(conn!=null){
                //�ع�����
                conn.rollback();
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
    * @Method: commit
    * @Description:�ύ����
    * @Anthor:
    *
    */ 
    public static void commit(){
        try{
            //�ӵ�ǰ�߳��л�ȡConnection
            Connection conn = threadLocal.get();
            if(conn!=null){
                //�ύ����
                conn.commit();
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
    * @Method: close
    * @Description:�ر����ݿ�����(ע�⣬��������Ĺرգ����ǰ����ӻ������ݿ����ӳ�)
    * @Anthor:
    *
    */ 
    public static void close(){
        try{
            //�ӵ�ǰ�߳��л�ȡConnection
            Connection conn = threadLocal.get();
            if(conn!=null){
                conn.close();
                 //�����ǰ�߳��ϰ�conn
                threadLocal.remove();
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
    * @Method: getDataSource
    * @Description: ��ȡ����Դ
    * @Anthor:
    * @return DataSource
    */ 
    public static DataSource getDataSource(){
        //������Դ�л�ȡ���ݿ�����
        return dataSource;
    }
}