package model;

public class QuestionMember {
	
	private String title;
	private String contents;
	private String created_by;
	private String created_at; 
	
	public QuestionMember() {
		
	}
	public QuestionMember(String title, String contents) {
		super();
		this.title = title;
		this.contents = contents;
		this.created_by = null;
		this.created_at = null;
	}

	public QuestionMember(String title, String contents, String created_by, String created_at) {
		super();
		this.title = title;
		this.contents = contents;
		this.created_by = created_by;
		this.created_at = created_at;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	
	
	
	
	
}
