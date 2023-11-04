package hai704i.tp2.demo.model;

public class Employee {
	/* ATTRIBUTES */
	private int id;
	private String name;
	
	/* CONSTRUCTORS */
	public Employee(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	/* METHODS */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
