package socket.simpleThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
/*
 * 在这种情况下，这个server只会和一个客户端建立一个连接。
 */
public class SimpleServerIo {

	public static void main(String args[]) {
		ServerSocket server = null;
		Socket socket = null;
		try {
			try {
				server = new ServerSocket(4700);
				// 创建一个ServerSocket在端口4700监听客户请求
				socket = server.accept();
				// 使用accept()阻塞等待客户请求，有客户
				// 请求到来则产生一个Socket对象，并继续执行
			} catch (Exception e) {
				if (server!=null){
					server.close();
				}
				if (socket!=null){
					socket.close();
				}
				System.out.println("Error." + e);
			}
			String line;
			
			
			String retention = "retention";
			
			BufferedReader is = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			// 由Socket对象得到输入流，并构造相应的BufferedReader对象
			PrintWriter os = new PrintWriter(socket.getOutputStream());
			
			while (true){
				
				line = is.readLine();
				if (line!=null&&line.equals("stop")){
					retention = "stop";
				}
				if (line!=null&&line.equals("bye")){
					break;
				}
				
				//test
				line = line+socket.toString()+"\t"+retention;
				
				System.out.println("Client:" + line+"\tlf!");
				
				os.println(line);
				// 向客户端输出该字符串
				os.flush();
			}
			socket.close();
			

		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (server!=null){
					server.close();
				}
				if (socket!=null){
					socket.close();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Error:" + e.getMessage());
		}finally{
			try {
				if (server!=null){
					server.close();
				}
				if (socket!=null){
					socket.close();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
