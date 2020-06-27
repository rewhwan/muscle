package model;

public class PTBarChart {

    private String member_id;
    private int cnt;

    public PTBarChart(String member_id, int cnt) {
        this.member_id = member_id;
        this.cnt = cnt;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    @Override
    public String toString() {
        return "PTBarChart{" +
                "member_id='" + member_id + '\'' +
                ", cnt=" + cnt +
                '}';
    }
}
