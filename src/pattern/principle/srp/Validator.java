package pattern.principle.srp;
public class Validator {
	//��������У��
	public boolean validate(String username, String upassword) {
		if(username == null || "".equals(username.trim())) {
			System.out.println("�û�������Ϊ��");
			return false;
		}
		if(upassword == null || "".equals(upassword.trim())) {
			System.out.println("���벻��Ϊ��");
			return false;
		}
		return true;
	}
}
