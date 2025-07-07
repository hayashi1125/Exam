package bean;

import java.io.Serializable;

public class Student implements Serializable {

  private String no;          // 学生番号
  private String name;        // 学生氏名
  private String class_num;   // クラス番号

  public Student() {
  }

  // 学生番号のGetter/Setter
  public String getNo() {
    return no;
  }

  public void setNo(String no) {
    this.no = no;
  }

  // 氏名のGetter/Setter
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  // クラス番号のGetter/Setter
  public String getClass_num() {
    return class_num;
  }

  public void setClass_num(String class_num) {
    this.class_num = class_num;
  }
}
