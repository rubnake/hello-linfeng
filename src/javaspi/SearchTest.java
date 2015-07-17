package javaspi;

import java.util.Iterator;
import java.util.ServiceLoader;

import javaspi.interfaces.Search;

/*
 * ServiceLoader.load(Search.class); 
 * 读取的是文件 ：src/META-INF/services/javaspi.interfaces。Search  servcices后面跟的是接口Search的class路径
 * 此文件内容为 此接口的一个具体实现类 比如 javaspi.impl.FileSearch
 */
/*
 * java spi机制的思想。
 * 我们系统里抽象的各个模块，往往有很多不同的实现方案，比如日志模块的方案，xml解析模块、jdbc模块的方案等。面向的对象的设计里，
 * 我们一般推荐模块之间基于接口编程，模块之间不对实现类进行硬编码。一旦代码里涉及具体的实现类，就违反了可拔插的原则，如果需要替换一种实现，就需要修改代码。
 * 
 * 为了实现在模块装配的时候能不在程序里动态指明，这就需要一种服务发现机制。
 * java spi就是提供这样的一个机制：为某个接口寻找服务实现的机制。有点类似IOC的思想，就是将装配的控制权移到程序之外，在模块化设计中这个机制尤其重要。
 * 
 * java spi的具体约定如下 ：
 * 当服务的提供者，提供了服务接口的一种实现之后，在jar包的META-INF/services/目录里同时创建一个以服务接口命名的文件。该文件里就是实现该服务接口的具体实现类。
 * 而当外部程序装配这个模块的时候，就能通过该jar包META-INF/services/里的配置文件找到具体的实现类名，并装载实例化，完成模块的注入。 
 * 
 * 基于这样一个约定就能很好的找到服务接口的实现类，而不需要再代码里制定。
 * jdk提供服务实现查找的一个工具类：java.util.ServiceLoader
 * 
 * ref :http://singleant.iteye.com/blog/1497259 例子common-logging
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
