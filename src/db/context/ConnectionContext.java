package db.context;

import java.sql.Connection;

/**
* @ClassName: ConnectionContext
* @Description:���ݿ�����������
* @author: �°�����
* @date: 2014-10-7 ����8:36:01
*
*/ 
public class ConnectionContext {

    /**
     * ���췽��˽�л�����ConnectionContext��Ƴɵ���
     */
    private ConnectionContext(){
        
    }
    //����ConnectionContextʵ������
    private static ConnectionContext connectionContext = new ConnectionContext();
    
    /**
    * @Method: getInstance
    * @Description:��ȡConnectionContextʵ������
    * @Anthor:�°�����
    *
    * @return
    */ 
    public static ConnectionContext getInstance(){
        return connectionContext;
    }
    
    /**
    * @Field: connectionThreadLocal
    *         ʹ��ThreadLocal�洢���ݿ����Ӷ���
    */ 
    private ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<Connection>();
    
    /**
    * @Method: bind
    * @Description:����ThreadLocal�ѻ�ȡ���ݿ����Ӷ���Connection�͵�ǰ�̰߳�
    * @Anthor:�°�����
    *
    * @param connection
    */ 
    public void bind(Connection connection){
        connectionThreadLocal.set(connection);
    }
    
    /**
    * @Method: getConnection
    * @Description:�ӵ�ǰ�߳���ȡ��Connection����
    * @Anthor:�°�����
    *
    * @return
    */ 
    public Connection getConnection(){
        return connectionThreadLocal.get();
    }
    
    /**
    * @Method: remove
    * @Description: �����ǰ�߳��ϰ�Connection
    * @Anthor:�°�����
    *
    */ 
    public void remove(){
        connectionThreadLocal.remove();
    }
}