package pattern.principle.LoD;

public class SomeOne {
	private Friend friend;
	private Stranger stranger;

	public Stranger getStranger() {
		return stranger;
	}

	public void setStranger(Stranger stranger) {
		this.stranger = stranger;
	}

	public Friend getFriend() {
		return friend;
	}

	public void setFriend(Friend friend) {
		this.friend = friend;
	}

	public void play() {
		System.out.println("someone play");
		friend.play();
		stranger.play();
	}

}
