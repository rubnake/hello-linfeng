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
 * ref��http://blog.csdn.net/a9529lty/article/details/7174265
 * http://blog.csdn.net/kongxx/article/details/7259465
 * @author linfeng 2014-3-25 05:24:13
 * �������϶�socketʹ�ú�socket����httpЭ���2ƪ����
 * ����һ����Ϊ˫��while����һֱ����ȴ���bug
 */
public class SimpleHttpServer {
	ServerSocket server = null;
	public static int PORT = 80;//��׼HTTP�˿�

	public static String encoding = "GBK";
	
	public SimpleHttpServer(){
		try {
			server = new ServerSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("HTTP��������������,�˿�:" + PORT);
		while (true) {
			try {
				Socket socket = server.accept();
				System.out.println("���ӵ����������û�:" + socket);
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

						System.out.println("�ͻ��˷��͵�������Ϣ: >>>>>>>>>>>>>>>>>>>>>>>>>");
						// ��ȡ��һ��, �����ַ
						String line = readLine(in, 0);
						//��ӡ������
						System.out.print(line);
						// < Method > < URL > < HTTP Version > <\r\n>  ȡ����URL����
						String resource = line.substring(line.indexOf('/'), line
								.lastIndexOf('/') - 5);
						//����������Դ�ĵ�ַ
						resource = URLDecoder.decode(resource, encoding);//������ URL ��ַ
						String method = new StringTokenizer(line).nextElement()
								.toString();
//						System.out.println("resource\t"+resource+"\tmethod:"+method);
						StringBuffer headers = new StringBuffer();
						
						int contentLength = 0;//���ΪPOST�������������Ϣ�峤��
						
						// ��ȡ������������͹������������ͷ����Ϣ
						do {
							line = readLine(in, 0);
							//�����Content-Length��Ϣͷʱȡ��
							if (line.startsWith("Content-Length")) {
								contentLength = Integer.parseInt(line.split(":")[1]
										.trim());
							}
							//��ӡ����head��Ϣ
							System.out.print(line);
							headers.append(";").append(line);
							//���������һ�������Ļس����У����ʾ����ͷ����
						} while (!line.equals("\r\n"));
						
						//�����POST��������������
						byte[] inputStream = null;
						if ("POST".equalsIgnoreCase(method)) {
							//ע������ֻ�Ǽ򵥵Ĵ�����ύ�Ĳ���
							inputStream = readLine(in, contentLength).getBytes();
						}
						
						SimpleHttpRequest request = new SimpleHttpRequest(method,resource,null,headers.toString(),inputStream);
						
						//match the request url
						if (request.getUrl().equals("/")){
						
							System.out.println("out!!-------------");
							// �� writer �Կͻ��� socket ���һ�� HTML ����
							out.println("HTTP/1.0 200 OK");//����Ӧ����Ϣ,������Ӧ��
							out.println("Content-Type:text/html;charset=" + encoding);
							out.println();// ���� HTTP Э��, ���н�����ͷ��Ϣ
	
							out.println("<h1> Hello world</h1>");
							out.flush();
						}else{
							System.out.println("out error!!-------------");
							out.println("HTTP/1.0 404 Not found");//����Ӧ����Ϣ,������Ӧ��
							out.println();// ���� HTTP Э��, ���н�����ͷ��Ϣ
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
	 * ���������Լ�ģ���ȡһ�У���Ϊ���ʹ��API�е�BufferedReaderʱ�����Ƕ�ȡ��һ���س����к�
	 * �ŷ��أ��������û�ж�ȡ����һֱ��������͵������ΪPOST����ʱ�����е�Ԫ�ػ�����Ϣ�崫�ͣ�
	 * ��ʱ����Ϣ����ĩ����׼��û�лس����еģ������ʱ��ʹ��BufferedReader����ʱ����POST�ύ
	 * ʱ�������������POST�ύʱ���ǰ�����Ϣ��ĳ���Content-Length����ȡ��Ϣ�壬�����Ͳ�������
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
			} while (total < contentLe);//��Ϣ�����δ����
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
		/* http�����header����һ��Referer���ԣ�������Ե���˼���������ǰ�����Ǵӱ��ҳ�����ӹ�
		 * ���ģ��Ǹ����Ծ����Ǹ�ҳ���url����������url��ֱ�Ӵ��������ַ������ľ�û�����ֵ����
		 * �����ֵ����ʵ�ֺܶ����õĹ��ܣ��������������¼������Դ�Լ���ס�ղŷ��ʵ����ӵȡ����⣬�
		 * �����������Referer����ʱ����̶���UTF-8����ģ�������GBK�³������룬�������������һ��
		 */
		if (tmpStr.startsWith("Referer")) {//�����Refererͷʱ��ʹ��UTF-8����
			tmpStr = new String(tmpByteArr, "UTF-8");
		}
		return tmpStr;
	}
}