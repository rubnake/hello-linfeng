package pattern.behavior.Interpreter;

public class PlusExpression extends Expression {

	public void interpret(Context context) {
		//��ʾ��Ϣ
		System.out.println("�Զ�����");
		//��������Ļ���
		String input = context.getInput();
		//��������ת��
		int intInput = Integer.parseInt(input);
		//���е���
//		intInput++;
		++intInput;
		//�������Ļ������½��и�ֵ
		context.setInput(String.valueOf(intInput));
		context.setOutput(intInput);
	}

}
