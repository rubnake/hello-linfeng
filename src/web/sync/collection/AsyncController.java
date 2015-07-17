package web.sync.collection;

/*
 * javaFuture�첽���� 
 ��jdk1.5��ʼ���ǿ�������Future�������첽����Ľ�����ڴ�֮ǰ���߳�Ҫ���ù����̣߳��첽�����̣߳��Ľ��
 �ǱȽ��鷳�����飬��Ҫ���ǽ�������ĳ���ṹ��ƣ��ȽϷ����������׳���
 ����Future���ǾͿ�����Ƴ��Ƚ����ŵ��첽�������ṹģ�ͣ�
 ���ݷֶ���֮��˼�룬���ǿ��԰��첽������̰߳���ְ���Ϊ3�ࣺ

 1. �첽����ķ����̣߳������̣߳��������첽��������ķֽ�ͷ��𣬰ѷֽ�õ����񽻸��첽�����work�߳�ȥִ�У�
            �����첽����󣬷����߳̿��Ի��Futrue�ļ��ϣ��Ӷ����Ը����첽������

 2. �첽����work�̣߳��������ļ�������

 3. �첽�������ռ��̣߳��ӷ����߳�������Future�ļ��ϣ���������Future��״̬��
          ����Future��״̬�������첽����Ľ����

 */
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import util.UtilTools;
  
public class AsyncController {   
  
    //�̳߳�   
    private ExecutorService executorService;   
  
    //�����첽�����Future   
    private FutureContext<String> context;
    
    
  
    public AsyncController() {   
        this.executorService = Executors.newFixedThreadPool(10);   
        this.context = new FutureContext<String>();   
    }   
  
    public static void main(String[] args) {   
        //�����첽����   
        AsyncController controller = new AsyncController();   
        controller.startAsyncCompution();   
  
        //�����첽����������̣߳����߳�ɨ���첽����Futrue��״̬������Ѿ���ɣ�������첽������   
        OutputResult output = new OutputResult();   
        output.setFutureContext(controller.getFutureContext());   
        Thread resultThread = new Thread(output);   
        resultThread.start();   
        
        System.out.println("I am dead!");
        try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        System.out.println (resultThread.isAlive());
    }   
  
    public FutureContext<String> getFutureContext() {   
        return this.context;   
    }   
  
    public void startAsyncCompution() {   
        /**  
         * ����100���첽���㣬ÿ���첽�����߳����sleep������ģ������ʱ��  
         */  
        final Random random = new Random();   
        for (int i = 0; i < 10; i++) {   
            Future<String> future = this.executorService   
                    .submit(new Callable<String>() {   
                        @Override  
                        public String call() throws Exception {   
                            int randomInt = random.nextInt(10);   
                            Thread.sleep(randomInt * 1000);   
                            return "" + randomInt;   
                        }   
                    });   
            //ÿ���첽����Ľ�������context��   
            this.context.addFuture(future);   
        }   
    }   
  
    public static class FutureContext<T> {   
  
        private ArrayBlockingQueue<Future<T>> futureList = new ArrayBlockingQueue<Future<T>>(10);   
  
        public void addFuture(Future<T> future) {   
            this.futureList.add(future);   
        }   
  
        public ArrayBlockingQueue<Future<T>> getFutureList() {   
            return this.futureList;   
        }   
    }   
  
    public static class OutputResult implements Runnable {   
  
        private FutureContext<String> context;   
  
        public void setFutureContext(FutureContext<String> context) {   
            this.context = context;   
        }   
  
        @Override  
        public void run() {   
        	long startDate = new Date().getTime();
            System.out.println("start to output result:"+UtilTools.dateFormat(new Date(startDate), "yyyy-MM-dd HH:mm:ss"));   
            ArrayBlockingQueue<Future<String>> list = this.context.getFutureList();   
  
            for (Future<String> future : list) {   
                this.outputResultFromFuture(future);   
            }   
            
            long endDate = new Date().getTime();
            System.out.println("finish to output result.\t"+UtilTools.dateFormat(new Date(endDate), "yyyy-MM-dd HH:mm:ss")
            		+"\t"+(endDate-startDate)/1000);   
        }   
  
        private void outputResultFromFuture(Future<String> future) {   
            try {   
                while (true) {   
                    if (future.isDone() && !future.isCancelled()) {   
                        System.out.println("Future:" + future + ",Result:"  
                                + future.get());   
                        break;   
                    } else {   
                        Thread.sleep(1000);   
                    }   
                }   
            } catch (Exception e) {   
                e.printStackTrace();   
            }   
        }   
    }   
}  
