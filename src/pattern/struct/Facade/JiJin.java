package pattern.struct.Facade;

public class JiJin {
	private Gupiao gupiao;
	private GuoZai guozai;
	private Qihuo qihuo;
	
	public JiJin() {
		this.guozai = new GuoZai();
		this.gupiao = new Gupiao();
		this.qihuo = new Qihuo();
	}
	
	public void maiJijinA() {
		this.guozai.mai();
		this.gupiao.mai();
	}
	
	public void maiJijinB() {
		this.guozai.mai();
		this.gupiao.mai();
		this.qihuo.chao();
	}
}
