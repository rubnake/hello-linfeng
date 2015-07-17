package pool;

public class Element {
	private int id;
	private Object obj;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	public boolean equals(Object another){
		if (another instanceof Element){
			Element value = (Element)another;
			return obj.equals(value.getObj());
		}
		return false;
	}
	
	public int hashCode(){
		return this.getId();
	}
}
