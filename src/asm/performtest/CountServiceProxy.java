package asm.performtest;

public class CountServiceProxy implements CountService{
	private CountServiceImpl realService;
	
	@Override
	public int count() {
		return realService.count();
	}

}
