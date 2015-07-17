package pattern.principle.srp;

/*
 * 
 * 单一职责原则(Single Responsibility Principle )：
       就一个类而言，应该仅有一个引起它变化的原因。

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
