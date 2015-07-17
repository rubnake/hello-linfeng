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
    ArrayHandler：把结果集中的第一行数据转成对象数组。
    ArrayListHandler：把结果集中的每一行数据都转成一个对象数组，再存放到List中。
    BeanHandler：将结果集中的第一行数据封装到一个对应的JavaBean实例中。
    BeanListHandler：将结果集中的每一行数据都封装到一个对应的JavaBean实例中，存放到List里。
    ColumnListHandler：将结果集中某一列的数据存放到List中。
     KeyedHandler：将结果集中的每一行数据都封装到一个Map里，然后再根据指定的key把每个Map再存放到一个Map里。
     MapHandler：将结果集中的第一行数据封装到一个Map里，key是列名，value就是对应的值。
     MapListHandler：将结果集中的每一行数据都封装到一个Map里，然后再存放到List。
     ScalarHandler：将结果集中某一条记录的其中某一列的数据存成Object。
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
                    //获取新增记录的自增主键
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
                    //先将两个字段置为null
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
