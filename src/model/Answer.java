package model;

public class Answer {
	private int question_no; 
	private String contents;
	private String created_by;
	private String created_at;
	
	public Answer(int question_no, String contents, String created_by, String created_at) {
		super();
		this.question_no = question_no;
		this.contents = contents;
		this.created_by = created_by;
		this.created_at = created_at;
	}
	
	public int getQuestion_no() {
		return question_no;
	}
	public void setQuestion_no(int question_no) {
		this.question_no = question_no;
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

	@Override
	public String toString() {
		return "Answer [question_no=" + question_no + ", contents=" + contents + ", created_by=" + created_by
				+ ", created_at=" + created_at + "]";
	}

}
