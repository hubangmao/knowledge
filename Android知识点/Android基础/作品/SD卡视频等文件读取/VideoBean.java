package cn.ucai.day07_05muis;

/**
 * Created by Administrator on 2016/5/3.
 */
public class VideoBean {
    private String namev;
    private String pathv;
    private int idv;

    public VideoBean(String namev, String pathv, int idv) {
        this.namev = namev;
        this.pathv = pathv;
        this.idv = idv;
    }

    @Override

    public String toString() {
        return "VideoBean{" +
                "namev='" + namev + '\'' +
                ", pathv='" + pathv + '\'' +
                ", idv=" + idv +
                '}';
    }

    public String getNamev() {
        return namev;
    }

    public void setNamev(String namev) {
        this.namev = namev;
    }

    public String getPathv() {
        return pathv;
    }

    public void setPathv(String pathv) {
        this.pathv = pathv;
    }

    public int getIdv() {
        return idv;
    }

    public void setIdv(int idv) {
        this.idv = idv;
    }
}
