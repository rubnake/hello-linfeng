package javaspi;

import java.util.Iterator;
import java.util.ServiceLoader;

import javaspi.interfaces.Search;

/*
 * ServiceLoader.load(Search.class); 
 * ��ȡ�����ļ� ��src/META-INF/services/javaspi.interfaces��Search  servcices��������ǽӿ�Search��class·��
 * ���ļ�����Ϊ �˽ӿڵ�һ������ʵ���� ���� javaspi.impl.FileSearch
 */
/*
 * java spi���Ƶ�˼�롣
 * ����ϵͳ�����ĸ���ģ�飬�����кܶ಻ͬ��ʵ�ַ�����������־ģ��ķ�����xml����ģ�顢jdbcģ��ķ����ȡ�����Ķ��������
 * ����һ���Ƽ�ģ��֮����ڽӿڱ�̣�ģ��֮�䲻��ʵ�������Ӳ���롣һ���������漰�����ʵ���࣬��Υ���˿ɰβ��ԭ�������Ҫ�滻һ��ʵ�֣�����Ҫ�޸Ĵ��롣
 * 
 * Ϊ��ʵ����ģ��װ���ʱ���ܲ��ڳ����ﶯָ̬���������Ҫһ�ַ����ֻ��ơ�
 * java spi�����ṩ������һ�����ƣ�Ϊĳ���ӿ�Ѱ�ҷ���ʵ�ֵĻ��ơ��е�����IOC��˼�룬���ǽ�װ��Ŀ���Ȩ�Ƶ�����֮�⣬��ģ�黯������������������Ҫ��
 * 
 * java spi�ľ���Լ������ ��
 * ��������ṩ�ߣ��ṩ�˷���ӿڵ�һ��ʵ��֮����jar����META-INF/services/Ŀ¼��ͬʱ����һ���Է���ӿ��������ļ������ļ������ʵ�ָ÷���ӿڵľ���ʵ���ࡣ
 * �����ⲿ����װ�����ģ���ʱ�򣬾���ͨ����jar��META-INF/services/��������ļ��ҵ������ʵ����������װ��ʵ���������ģ���ע�롣 
 * 
 * ��������һ��Լ�����ܺܺõ��ҵ�����ӿڵ�ʵ���࣬������Ҫ�ٴ������ƶ���
 * jdk�ṩ����ʵ�ֲ��ҵ�һ�������ࣺjava.util.ServiceLoader
 * 
 * ref :http://singleant.iteye.com/blog/1497259 ����common-logging
 */
public class SearchTest {

	public static void main(String[] args) {  
        ServiceLoader<Search> s = ServiceLoader.load(Search.class);  
        Iterator<Search> searchs = s.iterator();  
        if (searchs.hasNext()) {  
            Search curSearch = searchs.next();  
            curSearch.search("test");  
        }  
        
        System.out.println("f");
    }  
}
