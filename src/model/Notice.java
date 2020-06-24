package model;

public class Notice {
    private int no;
    private String title;
    private String contents;
    private String created_by;
    private String created_at;

    public Notice(String title, String contents) {
        super();
        this.title = title;
        this.contents = contents;
    }

    public Notice(int no, String title, String contents) {
        super();
        this.no = no;
        this.title = title;
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

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
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


}
