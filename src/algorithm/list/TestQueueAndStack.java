package algorithm.list;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class TestQueueAndStack {
	
	/*
	 * ���нṹ 1 ʹ��LinkedList
	 * 
	 * queueʹ��
	 *  ʹ��offer ����� {�ײ����}
	 *  ʹ��poll  �õ�����{����������}
	 *  ʹ��peek  �õ����е�Ԫ��
	 *  
	 * another choice ����
	 * queue�ṹ��  1 ����ArrayDueue
	 * 
	 * queue ʹ�ã�
	 * ʹ��add �����  {�ײ�}     ����������� offer
	 * ʹ��remove  ������ {����}  �����������  poll
	 */
	public static void testQueueUsingList() {

		Queue<String> queue = new LinkedList<String>();
		queue.offer("A");// ��������Ԫ��
		queue.offer("B");
		queue.offer("C");
		System.out.println(queue);// [A, B, C]
		String head = queue.peek();// ȡ��Ԫ��
		System.out.println("head==" + head);
		System.out.println(queue);
		System.out.println("�������� " + queue.size() + " ��Ԫ��");

		/* �������� */
		// for(int
		// i=0;i<queue.size();i++)//���ɹ�����Ϊ��2������queue.size()����i�ı仯���仯�����²��ܷ��ʶ����е�ȫ��Ԫ��
		for (int i = queue.size(); i > 0; i--)// �ɹ�
		{
			System.out.println(queue.poll());
		}
		System.out.println(queue);
	}
	
	/* ջ�ṹ��  1 ����LinkedList
	 * 
	 * stack ʹ�ã�
	 * ʹ��push ��ջ {�������}
	 * ʹ��pop  ��ջ {�������}
	 * ʹ��peek �õ�ջ����Ԫ��
	 * 
	 * another choice ����
	 * ջ�ṹ��  1 ����ArrayDueue
	 * stack ʹ�ã�
	 * ʹ��push ��ջ
	 * ʹ��pop  ��ջ
	 */

	public static void testStackUsingList() {
		Deque<String> stack = new LinkedList<String>();
		/* ��ջ�С�ѹ�롰Ԫ�� */
		stack.push("A");
		stack.push("B");
		stack.push("C");
		stack.push("D");
		stack.push("E");
		stack.push("F");
		System.out.println(stack);// [F, E, D, C, B, A]
		/*
		 * pop()�������ڡ�������ջ��Ԫ�أ��������ջ��û��Ԫ�ػ����nullԪ��ʱ��ʹ��pop()�����������쳣��
		 * ����ͨ����forѭ��һ��һ��popȡԪ��
		 */
		for (int i = stack.size(); i > 0; i--) {
			String ss = stack.pop();
			System.out.println(ss);
		}
	}

	public static void main(String[] args) {
		
		testStackUsingList();
		
	}

}
