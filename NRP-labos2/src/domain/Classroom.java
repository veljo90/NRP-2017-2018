package domain;

public class Classroom implements Comparable{
	private int id;
	
	Classroom(int id){
		this.id = id;
	}
	
	int getId(){
		return this.id;
	}

	@Override
	public int compareTo(Object classroom) {
		if(!(classroom instanceof Classroom))
			throw new ClassCastException("A Classroom object expected.");
		
		if (this.id < ((Classroom) classroom).getId())
			return -1;
		else if (this.id > ((Classroom) classroom).getId())
			return 1;
		return 0;
	}
}
