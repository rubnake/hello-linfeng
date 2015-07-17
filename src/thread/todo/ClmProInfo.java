package thread.todo;

public class ClmProInfo implements Runnable {

	int offerId;
	String billId;
	boolean result = false;

	ClmProInfo(int offerId, String billId) {
		this.offerId = offerId;
		this.billId = billId;
	}

	@Override
	public void run() {
		try {
			deal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void deal() throws InterruptedException {
		System.out.println(this.offerId + "\t done\t" + this.billId);
		Thread.sleep(1000);
		result = true;
	}
	
	public boolean getFlage(){
		return this.result;
	}
}
