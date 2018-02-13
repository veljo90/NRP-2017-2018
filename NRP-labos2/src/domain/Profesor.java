package domain;

public class Profesor {
	private int id;
	
	public Profesor(int id){
		this.id = id;
	}
	
	public int getId(){
		return this.id;
	}

	public int compareTo(Profesor profesor) {
		if (this.id < profesor.getId())
			return -1;
		else if (this.id == profesor.getId())
			return 0;
		else return 1;
	}
}
