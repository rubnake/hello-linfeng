package socket.simpleThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
/*
 * ����������£����serverֻ���һ���ͻ��˽���һ�����ӡ�
 */
public class SimpleServerIo {

	public static void main(String args[]) {
		ServerSocket server = null;
		Socket socket = null;
		try {
			try {
				server = new ServerSocket(4700);
				// ����һ��ServerSocket�ڶ˿�4700�����ͻ�����
				socket = server.accept();
				// ʹ��accept()�����ȴ��ͻ������пͻ�
				// �����������һ��Socket���󣬲�����ִ��
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
			// ��Socket����õ�����������������Ӧ��BufferedReader����
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
				// ��ͻ���������ַ���
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
