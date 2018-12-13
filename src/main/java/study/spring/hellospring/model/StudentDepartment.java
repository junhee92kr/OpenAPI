package study.spring.hellospring.model;

public class StudentDepartment extends Student{	
	private String dname;
	private String pname;
	
	private int limitStart;
	private int listCount;
	
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public int getLimitStart() {
		return limitStart;
	}
	public void setLimitStart(int limitStart) {
		this.limitStart = limitStart;
	}
	public int getListCount() {
		return listCount;
	}
	public void setListCount(int listCount) {
		this.listCount = listCount;
	}
	@Override
	public String toString() {
		return "StudentDepartment [dname=" + dname + ", pname=" + pname + ", limitStart=" + limitStart + ", listCount="
				+ listCount + ", getStudno()=" + getStudno() + ", getName()=" + getName() + ", getUserid()="
				+ getUserid() + ", getGrade()=" + getGrade() + ", getIdnum()=" + getIdnum() + ", getBirthdate()="
				+ getBirthdate() + ", getTel()=" + getTel() + ", getHeight()=" + getHeight() + ", getWeight()="
				+ getWeight() + ", getDeptno()=" + getDeptno() + ", getProfno()=" + getProfno() + "]";
	}
	
	
	
	
}

