package xml.dom4j;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class XmlUtil {

	// 读取XML文件,获得document对象
	public static Document load(String filename) throws DocumentException{
		Document document = null;
		SAXReader saxReader = new SAXReader();
		document = saxReader.read(new File(filename));
		return document;
	}
	// 读取XML文件,获得document对象
	public static Document load(URL url) throws DocumentException {
		Document document = null;
		SAXReader saxReader = new SAXReader();
		document = saxReader.read(url);
		return document;

	}
	// List list=document.selectNodes("/books/book/@show");

	public static void main(String[] args) throws Exception{
		Document doc = XmlUtil.load("config/defaults.xml");
		List list=doc.selectNodes("/defaults/transaction/mapping/property");
		for (int i=0;i<list.size();i++){
			Node node = (Node)list.get(i);
			String name = node.valueOf("@name");
			String value = node.valueOf("@value");
			System.out.println(name+"\t"+value);
		}
		System.out.println("package size:\t"+list.size());
		
		List pool=doc.selectNodes("/defaults/datasource/pool");
		for (int i=0;i<pool.size();i++){
			Node node = (Node)pool.get(i);
			System.out.println(node.valueOf("@name"));
		}
		System.out.println("pool size:\t"+pool.size());
	}

}
