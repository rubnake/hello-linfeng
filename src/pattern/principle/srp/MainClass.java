package pattern.principle.srp;

/*
 * 
 * ��һְ��ԭ��(Single Responsibility Principle )��
       ��һ������ԣ�Ӧ�ý���һ���������仯��ԭ��

 */
public class MainClass {
	public static void main(String[] args) {
		Input input = new Input();
		input.shuru();
		Validator validator = new Validator();
		if(validator.validate(input.getUsername(), input.getUpassword())){
			DBManager.getConnection();
			DAOImp dao = new DAOImp();
			dao.save(input.getUsername(), input.getUpassword());
		}
	}
}
