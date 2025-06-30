package bean;

public class Teacher implements java.io.Serializable {

	private String id;
	private String name;
	private String login;
	private String password;
	private String school_cd; // ← 追加

	public String getId(){
		return id;
	}

	public String getName(){
		return name;
	}

	public String getLogin(){
		return login;
	}

	public String getPassword(){
		return password;
	}

	public String getSchool_cd() { // ← 追加
		return school_cd;
	}

	public void setId(String id){
		this.id=id;
	}

	public void setName(String name){
		this.name=name;
	}

	public void setLogin(String login){
		this.login=login;
	}

	public void setPassword(String password){
		this.password=password;
	}

	public void setSchool_cd(String school_cd) { // ← 追加
		this.school_cd = school_cd;
	}
}
