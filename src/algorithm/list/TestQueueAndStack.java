package algorithm.list;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class TestQueueAndStack {
	
	/*
	 * 队列结构 1 使用LinkedList
	 * 
	 * queue使用
	 *  使用offer 入队列 {底部入队}
	 *  使用poll  得到队列{顶部的数据}
	 *  使用peek  得到队列的元素
	 *  
	 * another choice 线性
	 * queue结构：  1 对于ArrayDueue
	 * 
	 * queue 使用：
	 * 使用add 入队列  {底部}     可替代方法： offer
	 * 使用remove  出队列 {顶部}  可替代方法：  poll
	 */
	public static void testQueueUsingList() {

		Queue<String> queue = new LinkedList<String>();
		queue.offer("A");// 向队列添加元素
		queue.offer("B");
		queue.offer("C");
		System.out.println(queue);// [A, B, C]
		String head = queue.peek();// 取首元素
		System.out.println("head==" + head);
		System.out.println(queue);
		System.out.println("队列中有 " + queue.size() + " 个元素");

		/* 遍历队列 */
		// for(int
		// i=0;i<queue.size();i++)//不成功，因为第2个条件queue.size()跟随i的变化而变化，导致不能访问队列中的全部元素
		for (int i = queue.size(); i > 0; i--)// 成功
		{
			System.out.println(queue.poll());
		}
		System.out.println(queue);
	}
	
	/* 栈结构：  1 对于LinkedList
	 * 
	 * stack 使用：
	 * 使用push 入栈 {顶部入队}
	 * 使用pop  出栈 {顶部入队}
	 * 使用peek 得到栈顶的元素
	 * 
	 * another choice 线性
	 * 栈结构：  1 对于ArrayDueue
	 * stack 使用：
	 * 使用push 入栈
	 * 使用pop  出栈
	 */

	public static void testStackUsingList() {
		Deque<String> stack = new LinkedList<String>();
		/* 向栈中”压入“元素 */
		stack.push("A");
		stack.push("B");
		stack.push("C");
		stack.push("D");
		stack.push("E");
		stack.push("F");
		System.out.println(stack);// [F, E, D, C, B, A]
		/*
		 * pop()方法用于”弹出“栈中元素，但是如果栈中没有元素或包含null元素时，使用pop()方法会引发异常，
		 * 所以通常用for循环一个一个pop取元素
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
