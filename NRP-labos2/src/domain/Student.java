package domain;

import java.util.Objects;

public class Student {
	private int id;
	
	public Student(int id){
		this.id = id;
	}
	
	public int getId(){
		return this.id;
	}
	
	@Override
	public boolean equals(Object o) {
	    // self check
	    if (this == o)
	        return true;
	    // null check
	    if (o == null)
	        return false;
	    // type check and cast
	    if (getClass() != o.getClass())
	        return false;
	    Student student = (Student) o;
	    // field comparison
	    return Objects.equals(id, student.id);
	}
}
