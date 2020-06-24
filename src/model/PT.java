package model;

public class PT {
	private int no;
	private String member_id;
	private String trainer_id;
	private String date;
	private String created_by;
	private String created_at; 
	
	public PT() {}
	
	public PT(int no, String member_id, String trainer_id, String date) {
		super();
		this.no = no; 
		this.member_id= member_id;
		this.trainer_id = trainer_id;
		this.date=date;
	}
	
	
	public PT(int no, String member_id, String trainer_id, String date, String created_by, String created_at) {
		super();
		this.no = no;
		this.member_id = member_id;
		this.trainer_id = trainer_id;
		this.date = date;
		this.created_by = created_by;
		this.created_at = created_at;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getTrainer_id() {
		return trainer_id;
	}

	public void setTrainer_id(String tainer_id) {
		this.trainer_id = tainer_id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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
