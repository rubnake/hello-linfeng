package pattern.principle.srp;
import java.util.Scanner;

public class Input {
	private String username;
	private String upassword;
	
	//��ÿͻ�������
	public void shuru() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("�������û���");
		username = scanner.nextLine();
		System.out.println("����������");
		upassword = scanner.nextLine();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUpassword() {
		return upassword;
	}

	public void setUpassword(String upassword) {
		this.upassword = upassword;
	}
}
