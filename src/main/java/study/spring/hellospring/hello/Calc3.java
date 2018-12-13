package study.spring.hellospring.hello;

//Value 클래스의 객체를 생성자로 전달
public class Calc3{
	private Value v;

	public Calc3(Value v) {
		this.v = v;
	}
	
	public int sum() {
		return v.getX() + v.getY();
	}
	
}