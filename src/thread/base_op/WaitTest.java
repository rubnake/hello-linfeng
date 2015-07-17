package thread.base_op;
/*
 * t1�߳�����ִ�С����ڳ�ʼ״̬��shareVarΪ0��t1��ʹshareVar������1����shareVar��ֵΪ5ʱ��t1����wait() ������
t1��������Ϣ״̬��ͬʱ�ͷ�����־����ʱt2�õ�������־��ʼִ�У�shareVar��ֵ�Ѿ���Ϊ5������t2ֱ�����shareVar��ֵ��
Ȼ���ٵ���notify() ��������t1��t1�����ϴ���Ϣǰ�Ľ��ȼ���ִ�У���shareVar��ֵһֱ�ӵ�10�����ڴ˿�shareVar��ֵ��Ϊ0��
����t1������˿�shareVar��ֵ��Ȼ���ٵ���notify() ���������ڴ˿��Ѿ�û�еȴ�����־���̣߳����Դ˵�����䲻���κ����á�
�����������򵥵�ʾ����wait(), notify() ���÷������߻���Ҫ��ʵ���м���������
 */
public class WaitTest implements Runnable {
	public static int shareVar = 0;

	public synchronized void run() {
		if (shareVar == 0) {
			for (int i = 0; i < 10; i++) {
				shareVar++;
				if (shareVar == 5) {
					try {
						this.wait();
					} catch (Exception e) {
					}
				}
			}
		}
		System.out.println(Thread.currentThread().getName()+"   linfneg");
		if (shareVar != 0) {
			System.out.print(Thread.currentThread().getName());
			System.out.println(" shareVar = " + shareVar);
			this.notify();
		}
	}

	public static void main(String[] args) {
		Runnable r = new WaitTest();
		Thread t1 = new Thread(r, "t1");
		Thread t2 = new Thread(r, "t2");
		t1.start();
		t2.start();
	}
}
