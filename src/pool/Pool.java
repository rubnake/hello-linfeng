package pool;

public interface Pool {
	public Element getObject();
	
	public void returnObject(Element e);
}
