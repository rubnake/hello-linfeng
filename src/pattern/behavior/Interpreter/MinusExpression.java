package pattern.behavior.Interpreter;

public class MinusExpression extends Expression {

	public void interpret(Context context) {
		System.out.println("�Զ��ݼ�");
		String input = context.getInput();
		int inInput = Integer.parseInt(input);
//		inInput--;
		--inInput;
		context.setInput(String.valueOf(inInput));
		context.setOutput(inInput);
	}

}
