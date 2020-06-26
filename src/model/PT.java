package model;

public class PT {
    private int no;
    private String member_id;
    private String trainer_id;
    private String date;
    private String time;
    private String created_by;
    private String created_at;
    private String deleted_by;
    private String deleted_at;

    public PT() {
    }

    public PT(String date, String time, String created_at, String trainer_id) {
    	this.date=date;
    	this.time=time;
    	this.created_at=created_at;
    	this.trainer_id= trainer_id;
    }
    
    public PT(int no, String member_id, String trainer_id, String date) {
        super();
        this.no = no;
        this.member_id = member_id;
        this.trainer_id = trainer_id;
        this.date = date;
    }

    public PT(int no, String member_id, String trainer_id, String date,String time, String created_by, String created_at, String deleted_by, String deleted_at) {
        this.no = no;
        this.member_id = member_id;
        this.trainer_id = trainer_id;
        this.date = date;
        this.time = time;
        this.created_by = created_by;
        this.created_at = created_at;
        this.deleted_by = deleted_by;
        this.deleted_at = deleted_at;
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

    public void setTrainer_id(String trainer_id) {
        this.trainer_id = trainer_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getDeleted_by() {
        return deleted_by;
    }

    public void setDeleted_by(String deleted_by) {
        this.deleted_by = deleted_by;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    @Override
    public String toString() {
        return "PT{" +
                "no=" + no +
                ", member_id='" + member_id + '\'' +
                ", trainer_id='" + trainer_id + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", created_by='" + created_by + '\'' +
                ", created_at='" + created_at + '\'' +
                ", deleted_by='" + deleted_by + '\'' +
                ", deleted_at='" + deleted_at + '\'' +
                '}';
    }
}
