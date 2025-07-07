package bean;

import java.io.Serializable;

public class Subject implements Serializable {
    private String cd;     // 科目コード
    private String name;   // 科目名

    public Subject() {}

    public String getCd() {
        return cd;
    }

    public void setCd(String cd) {
        this.cd = cd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
