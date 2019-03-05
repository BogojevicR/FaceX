package app.model;

public class PersonModel {

	private long id;
	private String first_name;
	private String last_name;
	private String link;
	public PersonModel() {
		super();
	}
	public PersonModel(long id, String first_name, String last_name, String link) {
		super();
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.link = link;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	@Override
	public String toString() {
		return "PersonModel [id=" + id + ", first_name=" + first_name + ", last_name=" + last_name + ", link=" + link
				+ "]";
	}
	
	
}
