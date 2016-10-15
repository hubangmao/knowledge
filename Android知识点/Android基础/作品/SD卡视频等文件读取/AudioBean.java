package cn.ucai.day07_05muis;

/**
 * Created by Administrator on 2016/5/3.
 */
public class AudioBean {
    private String name;
    private String path;
    private int id;

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;

    }

    public AudioBean(String name, String path, int id) {
        this.name = name;
        this.path = path;
        this.id = id;
    }


    public String getPath() {
        return path;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "AudioBean{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", id=" + id +
                '}';
    }
}
