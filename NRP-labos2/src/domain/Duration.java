package domain;

public class Duration {
	private int minutes;
	
	public Duration(int minutes){
		this.minutes = minutes;
	}
	
	public int getMinutes(){
		return this.minutes;
	}
	
	public void setMinutes(int minutes){
		this.minutes = minutes;
	}

	public int compareTo(Duration duration) {
		if(this.minutes < duration.getMinutes())
			return -1;
		else if (this.minutes == duration.getMinutes())
			return 0;
		else
			return 1;
	}

	@Override
	public String toString() {
		return "" + this.minutes;
	}
}
