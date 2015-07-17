package db.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import db.util.JdbcUtil;
import entity.Person;
/*
 * 
    ArrayHandler���ѽ�����еĵ�һ������ת�ɶ������顣
    ArrayListHandler���ѽ�����е�ÿһ�����ݶ�ת��һ���������飬�ٴ�ŵ�List�С�
    BeanHandler����������еĵ�һ�����ݷ�װ��һ����Ӧ��JavaBeanʵ���С�
    BeanListHandler����������е�ÿһ�����ݶ���װ��һ����Ӧ��JavaBeanʵ���У���ŵ�List�
    ColumnListHandler�����������ĳһ�е����ݴ�ŵ�List�С�
     KeyedHandler����������е�ÿһ�����ݶ���װ��һ��Map�Ȼ���ٸ���ָ����key��ÿ��Map�ٴ�ŵ�һ��Map�
     MapHandler����������еĵ�һ�����ݷ�װ��һ��Map�key��������value���Ƕ�Ӧ��ֵ��
     MapListHandler����������е�ÿһ�����ݶ���װ��һ��Map�Ȼ���ٴ�ŵ�List��
     ScalarHandler�����������ĳһ����¼������ĳһ�е����ݴ��Object��
     see http://www.cnblogs.com/yezhenhan/archive/2011/01/11/1932556.html
 */
public class PersonDAOImpl {
	private static PersonDAOImpl instance = new PersonDAOImpl();

    public static PersonDAOImpl getInstance() {
            return instance;
    }

    public static void main(String[] args) {

//            getInstance().save(null);
//            getInstance().update(null);
            getInstance().load(null);
//            getInstance().load4Map(null);
    }

    
    public Long save(String sql) {
            Long id = null;
            String ins_sql = "INSERT INTO person (NAME, age, address) VALUES ('aaa', 21, 'address001')";
            Connection conn = JdbcUtil.getConnection();
            QueryRunner qr = new QueryRunner();
            try {
                    qr.update(conn, ins_sql);
                    //��ȡ������¼����������
                    id = (Long) qr.query(conn, "SELECT LAST_INSERT_ID()", new ScalarHandler(1));
            } catch (SQLException e) {
                    e.printStackTrace();
            } finally {
//                    JdbcUtil.closeConnection(conn);
            	try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            return id;
    }

    
    public int delete(Long id) {
            int x = 0;
            Connection conn = JdbcUtil.getConnection();
            QueryRunner qr = new QueryRunner();
            try {
                    x = qr.update(conn, "DELETE FROM person WHERE id = ?", id);
            } catch (SQLException e) {
                    e.printStackTrace();
            } finally {
                    JdbcUtil.closeConnection(conn);
            }
            return x;
    }

    
    public int update(Person person) {
            int x = 0;
            Connection conn = JdbcUtil.getConnection();
            QueryRunner qr = new QueryRunner();
            try {
                    x = qr.update(conn, "UPDATE person SET NAME = ?, age = ?, address = ? WHERE id = ?", "xxx", 23, "ttt", 5);
            } catch (SQLException e) {
                    e.printStackTrace();
            } finally {
                    JdbcUtil.closeConnection(conn);
            }
            return x;

    }

    
    public Person load(Long id) {
            Connection conn = JdbcUtil.getConnection();
            QueryRunner qr = new QueryRunner();
            try {
                    Person person = (Person) qr.query(conn, "SELECT * FROM person where id = ?", new BeanHandler(Person.class), 3L);
                    System.out.println(person.getId() + "\t" + person.getName() + "\t" + person.getAge() + "\t" + person.getAddress());
            } catch (SQLException e) {
                    e.printStackTrace();
            }
            return null;
    }

    
    public List<Person> findPerson(String sql) {
            Connection conn = JdbcUtil.getConnection();
            QueryRunner qr = new QueryRunner();
            try {
                    List<Person> pset = (List) qr.query(conn, "SELECT * FROM person", new BeanListHandler(Person.class));
                    for (Person person : pset) {
                            System.out.println(person.getId() + "\t" + person.getName() + "\t" + person.getAge() + "\t" + person.getAddress());
                    }
            } catch (SQLException e) {
                    e.printStackTrace();
            }
            return null;
    }

    public Person load4Map(Long id) {
            Connection conn = JdbcUtil.getConnection();
            QueryRunner qr = new QueryRunner();
            try {
                    //�Ƚ������ֶ���Ϊnull
                    qr.update(conn, "update person set age = null,address =null where id =1");
                    Map<String, Object> map = qr.query(conn, "SELECT * FROM person where id = ?", new MapHandler(), 1L);
                    Person person = new Person();
                    person.setId((Integer) map.get("id"));
                    person.setName((String) map.get("name"));
                    person.setAge((Integer) map.get("age"));
                    person.setAddress((String) map.get("address"));
                    System.out.println(person.getId() + "\t" + person.getName() + "\t" + person.getAge() + "\t" + person.getAddress());
            } catch (SQLException e) {
                    e.printStackTrace();
            }
            return null;
    } 
}
