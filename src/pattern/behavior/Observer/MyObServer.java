package pattern.behavior.Observer;

import java.util.Observable;
import java.util.Observer;

public class MyObServer implements Observer {

	public void update(Observable o, Object arg) {
		Article art = (Article)arg;
		
		System.out.println("�����������µ����£���ȥ����!");
		System.out.println("���ͱ���Ϊ��" + art.getArticleTitle());
		System.out.println("��������Ϊ:" + art.getArticleContent());
	}

}
