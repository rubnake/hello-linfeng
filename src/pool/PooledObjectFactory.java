package pool;

public class PooledObjectFactory {
	private int counter = 100;
	
	public Element makeObject() throws Exception {
		Element one = new Element();
		one.setId(counter++);
		one.setObj(new Object());
//		System.out.println("object is create :\t"+counter);
        sleep(500);
        return one;
    }

    public void destroyObject(Element obj) throws Exception {
        sleep(250);
    }
    
    private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}
}
