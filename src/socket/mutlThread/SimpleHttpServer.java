package socket.mutlThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.StringTokenizer;
/*
 * ref：http://blog.csdn.net/a9529lty/article/details/7174265
 * http://blog.csdn.net/kongxx/article/details/7259465
 * @author linfeng 2014-3-25 05:24:13
 * 整合网上对socket使用和socket解析http协议的2篇文章
 * 修正一个因为双重while导致一直处理等待的bug
 */
public class SimpleHttpServer {
	ServerSocket server = null;
	public static int PORT = 80;//标准HTTP端口

	public static String encoding = "GBK";
	
	public SimpleHttpServer(){
		try {
			server = new ServerSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("HTTP服务器正在运行,端口:" + PORT);
		while (true) {
			try {
				Socket socket = server.accept();
				System.out.println("连接到服务器的用户:" + socket);
				invoke(socket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		PORT=8080;
		new SimpleHttpServer();
	}
	
	private static void invoke(final Socket client) throws IOException {
		new Thread(new Runnable() {
			public void run() {
				InputStream in = null;
				PrintWriter out = null;
				try {
					in = client.getInputStream();
					out = new PrintWriter(client.getOutputStream(),true);
					
					if (true) {

						System.out.println("客户端发送的请求信息: >>>>>>>>>>>>>>>>>>>>>>>>>");
						// 读取第一行, 请求地址
						String line = readLine(in, 0);
						//打印请求行
						System.out.print(line);
						// < Method > < URL > < HTTP Version > <\r\n>  取的是URL部分
						String resource = line.substring(line.indexOf('/'), line
								.lastIndexOf('/') - 5);
						//获得请求的资源的地址
						resource = URLDecoder.decode(resource, encoding);//反编码 URL 地址
						String method = new StringTokenizer(line).nextElement()
								.toString();
//						System.out.println("resource\t"+resource+"\tmethod:"+method);
						StringBuffer headers = new StringBuffer();
						
						int contentLength = 0;//如果为POST方法，则会有消息体长度
						
						// 读取所有浏览器发送过来的请求参数头部信息
						do {
							line = readLine(in, 0);
							//如果有Content-Length消息头时取出
							if (line.startsWith("Content-Length")) {
								contentLength = Integer.parseInt(line.split(":")[1]
										.trim());
							}
							//打印请求head信息
							System.out.print(line);
							headers.append(";").append(line);
							//如果遇到了一个单独的回车换行，则表示请求头结束
						} while (!line.equals("\r\n"));
						
						//如果是POST请求，则有请求体
						byte[] inputStream = null;
						if ("POST".equalsIgnoreCase(method)) {
							//注，这里只是简单的处理表单提交的参数
							inputStream = readLine(in, contentLength).getBytes();
						}
						
						SimpleHttpRequest request = new SimpleHttpRequest(method,resource,null,headers.toString(),inputStream);
						
						//match the request url
						if (request.getUrl().equals("/")){
						
							System.out.println("out!!-------------");
							// 用 writer 对客户端 socket 输出一段 HTML 代码
							out.println("HTTP/1.0 200 OK");//返回应答消息,并结束应答
							out.println("Content-Type:text/html;charset=" + encoding);
							out.println();// 根据 HTTP 协议, 空行将结束头信息
	
							out.println("<h1> Hello world</h1>");
							out.flush();
						}else{
							System.out.println("out error!!-------------");
							out.println("HTTP/1.0 404 Not found");//返回应答消息,并结束应答
							out.println();// 根据 HTTP 协议, 空行将结束头信息
							out.flush();
						}
						
						
					}
				} catch(IOException ex) {
					ex.printStackTrace();
				} finally {
					try {
						in.close();
					} catch (Exception e) {e.printStackTrace();}
					try {
						out.close();
					} catch (Exception e) {e.printStackTrace();}
					try {
						client.close();
					} catch (Exception e) {e.printStackTrace();}
				}
			}
		}).start();
	}
	
	/*
	 * 这里我们自己模拟读取一行，因为如果使用API中的BufferedReader时，它是读取到一个回车换行后
	 * 才返回，否则如果没有读取，则一直阻塞，这就导致如果为POST请求时，表单中的元素会以消息体传送，
	 * 这时，消息体最末按标准是没有回车换行的，如果此时还使用BufferedReader来读时，则POST提交
	 * 时会阻塞。如果是POST提交时我们按照消息体的长度Content-Length来截取消息体，这样就不会阻塞
	 */
	private static String readLine(InputStream is, int contentLe) throws IOException {
		ArrayList lineByteList = new ArrayList();
		byte readByte;
		int total = 0;
		if (contentLe != 0) {
			do {
				readByte = (byte) is.read();
				lineByteList.add(Byte.valueOf(readByte));
				total++;
			} while (total < contentLe);//消息体读还未读完
		} else {
			do {
				readByte = (byte) is.read();
				lineByteList.add(Byte.valueOf(readByte));
			} while (readByte != 10);
		}

		byte[] tmpByteArr = new byte[lineByteList.size()];
		for (int i = 0; i < lineByteList.size(); i++) {
			tmpByteArr[i] = ((Byte) lineByteList.get(i)).byteValue();
		}
		lineByteList.clear();

		String tmpStr = new String(tmpByteArr, encoding);
		/* http请求的header中有一个Referer属性，这个属性的意思就是如果当前请求是从别的页面链接过
		 * 来的，那个属性就是那个页面的url，如果请求的url是直接从浏览器地址栏输入的就没有这个值。得
		 * 到这个值可以实现很多有用的功能，例如防盗链，记录访问来源以及记住刚才访问的链接等。另外，浏
		 * 览器发送这个Referer链接时好像固定用UTF-8编码的，所以在GBK下出现乱码，我们在这里纠正一下
		 */
		if (tmpStr.startsWith("Referer")) {//如果有Referer头时，使用UTF-8编码
			tmpStr = new String(tmpByteArr, "UTF-8");
		}
		return tmpStr;
	}
}