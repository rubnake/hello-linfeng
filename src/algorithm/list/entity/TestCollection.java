package algorithm.list.entity;

import java.util.ArrayList;
import java.util.List;


public class TestCollection {
	
	public boolean isExitInTree(List<Person> toSaveList,String  name){
	    	
		  Person one = new Person();
	    	one.setName(name);
	    	return toSaveList.contains(one);
	    }
	  
	 public void doPrint(List<Person> toSaveList){
		 for (Person one:toSaveList){
			 System.out.println(one.getId()+"\t"+one.getName());
		 }
	 }

	public static void main(String[] args) {
		TestCollection test = new TestCollection();
		
		List<Person> toSaveList = new ArrayList<Person>();
		for (int i=0;i<10;i++){
			Person one = new Person();
			one.setId(""+i);
			one.setName("name"+(i/3));
			if (test.isExitInTree(toSaveList,"name"+(i/3))){
				
			}else{
				toSaveList.add(one);
			}
		}
		
		
		test.doPrint(toSaveList);
		Person value = new Person(""+2,"name0");
		System.out.println(test.isExitInTree(toSaveList, "name0"));
		
		System.out.println(toSaveList.contains(value));
		
		Person value1 = new Person(""+1,"name0");
		
		
	}

}
