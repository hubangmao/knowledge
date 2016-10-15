package cn.ucai.day07_05muis;

/**
 * Created by Administrator on 2016/5/3.
 */
public class ImageBean {
    private String iname;
    private String ipath;
    private int iid;

    public String getIpath() {
        return ipath;
    }

    public void setIpath(String ipath) {
        this.ipath = ipath;
    }

    public int getIid() {
        return iid;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }

    public String getIname() {
        return iname;
    }

    public void setIname(String iname) {
        this.iname = iname;
    }

    public ImageBean(String iname, String ipath, int iid) {
        this.iname = iname;
        this.ipath = ipath;

        this.iid = iid;
    }

    @Override
    public String toString() {
        return "ImageBean{" +
                "iname='" + iname + '\'' +
                ", ipath='" + ipath + '\'' +
                ", iid=" + iid +
                '}';
    }


}
