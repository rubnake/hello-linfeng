package thread.future;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class T1 {

	private final ExecutorService exec = Executors.newFixedThreadPool(3);


	public void deal() {
		
		Callable<Person> task = new Callable<Person>() {

			@Override
			public Person call() throws Exception {
				Person result = new Person();
				result.setName("lf");
				return result;
			}
		};

		Future<Person> f = exec.submit(task);
		
		Person b = null;
		try {
			 b = f.get();
			 System.out.println(b.getName());
		} catch (InterruptedException e) {
			e.printStackTrace();
			f.cancel(true);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	
	public class Person {

		public Person() {}
		
		String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}
	
	public class PeronCallable implements Callable<Person>{
		
		int index;
		public PeronCallable(int index){
			this.index = index;
		}
		
		@Override
		public Person call() throws Exception {
			Person result = new Person();
			result.setName("lf"+index);
			Thread.sleep(1000*index);
			return result;
		}
		
	} 
	
	public List<Callable<Person>> getTasks(){
		List<Callable<Person>> tasks = new ArrayList<Callable<Person>>();
		for ( int i=0;i<10;i++){
			Callable<Person> task = new PeronCallable(i);
			tasks.add(task);
		}
		return tasks;
	}
	
	public void deal2all()  {
		List<Callable<Person>> tasks = getTasks();
		long startTime = new Date().getTime();
		List<Future<Person>> result = null;
		try {
			result = exec.invokeAll(tasks,4,TimeUnit.SECONDS);//
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("invokeAll error");
		}
		long endTime = new Date().getTime();
		System.out.println("deal2all time:\t"+(endTime-startTime)/1000);
		System.out.println("!!!!!result:\t"+result.size());
		
		
		for (Future<Person> one:result){
			
			try {
				if (one.isCancelled()){
					
				}else{
					System.out.println(one.get().getName());
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("InterruptedException error");
			} catch (Exception e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				System.out.println("Exception error");
			}
		}

	}
	
	public void shutdown(){
		exec.shutdown();
	}

	public static void main(String[] args) {
		T1 a = new T1();
		long startTime = new Date().getTime();
		a.deal2all();
		long endTime = new Date().getTime();
		System.out.println("FFF time:\t"+(endTime-startTime)/1000);
		a.shutdown();
	}
}
