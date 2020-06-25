package model;

public class Member {
    //트레이너 및 회원의 정보를 관리하는 모델
    //멤버변수
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

    //매개변수 생성자 생성
    public Member(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public Member(String id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    
    
    public Member(String id, String pw, String name, String gender, String phone) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.address = null;
        this.mail = null;
        this.position = null;
        this.ment = null;
        this.trainer_flag = "f";
    }

  

	public Member(String id, String pw, String name, String phone, String birth, String address, String mail) {
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.phone = phone;
		this.birth = birth;
		this.address = address;
		this.mail = mail;
	}

	public Member(String id, String pw, String name, String gender, String phone, String birth, String address, String mail, String position, String ment, String trainer_flag) {
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

    //getters,setters 생성
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

    public String getPosition() { return position; }

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

    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                ", pw='" + pw + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", birth='" + birth + '\'' +
                ", address='" + address + '\'' +
                ", mail='" + mail + '\'' +
                ", position='" + position + '\'' +
                ", ment='" + ment + '\'' +
                ", trainer_flag='" + trainer_flag + '\'' +
                '}';
    }
}
