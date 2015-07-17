package jmx.example;

public class Hello implements HelloMBean {

	@Override
	public void sayHello() {
		System.out.println("hello, world size:\t"+this.cacheSize);

	}

	@Override
	public int add(int x, int y) {
		return x + y;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getCacheSize() {
		return this.cacheSize;
	}

	@Override
	public void setCacheSize(int size) {
		this.cacheSize = size;
		System.out.println("Cache size now " + this.cacheSize);

	}

	private final String name = "Reginald";
	private int cacheSize = DEFAULT_CACHE_SIZE;
	private static final int DEFAULT_CACHE_SIZE = 200;

}
