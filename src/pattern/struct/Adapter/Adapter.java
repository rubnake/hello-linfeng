package pattern.struct.Adapter;
public class Adapter extends Current{
	public void use18V() {
		System.out.println("ʹ��������");
		this.use220V();
	}
}
