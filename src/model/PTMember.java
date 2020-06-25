package model;

public class PTMember {

    //PT 테이블 멤버 변수 선언
    private int no;
    private String member_id;
    private String trainer_id;
    private String date;
    private String created_by;
    private String created_at;
    private String deleted_by;
    private String deleted_at;

    //Member 테이블 멤버 변수 선언
    private String id;
    private String pw;
    private String name;
    private String gender;
    private String phone;
    private String birth;
    private String address;
    private String mail;
    private String position;
    private String ment;
    private String trainer_flag;

    public PTMember(int no, String date, String name, String phone,String created_by, String created_at) {
        this.no = no;
        this.date = date;
        this.created_by = created_by;
        this.created_at = created_at;
        this.name = name;
        this.phone = phone;
    }

    public PTMember(int no, String member_id, String trainer_id, String date, String created_by, String created_at, String deleted_by, String deleted_at, String id, String pw, String name, String gender, String phone, String birth, String address, String mail, String position, String ment, String trainer_flag) {
        this.no = no;
        this.member_id = member_id;
        this.trainer_id = trainer_id;
        this.date = date;
        this.created_by = created_by;
        this.created_at = created_at;
        this.deleted_by = deleted_by;
        this.deleted_at = deleted_at;
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.birth = birth;
        this.address = address;
        this.mail = mail;
        this.position = position;
        this.ment = ment;
        this.trainer_flag = trainer_flag;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getMent() {
        return ment;
    }

    public void setMent(String ment) {
        this.ment = ment;
    }

    public String getTrainer_flag() {
        return trainer_flag;
    }

    public void setTrainer_flag(String trainer_flag) {
        this.trainer_flag = trainer_flag;
    }
}
