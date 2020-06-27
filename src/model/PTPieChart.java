package model;

public class PTPieChart {

    private String time;
    private int cnt;

    public PTPieChart(String time, int cnt) {
        this.time = time;
        this.cnt = cnt;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    @Override
    public String toString() {
        return "PTPieChart{" +
                "time='" + time + '\'' +
                ", cnt=" + cnt +
                '}';
    }
}
