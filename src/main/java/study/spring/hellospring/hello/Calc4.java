package study.spring.hellospring.hello;

import java.util.Random;

//Value 클래스의 객체를 생성자로 전달
public class Calc4{
	private int x;
	private int y;
	
	public void init() {
		//랜덤객체 생성
		Random r = new Random(System.currentTimeMillis());
		//랜덤한 init값을 생성
		x = r.nextInt();
		y = r.nextInt();
	}
	
	public int sum() {
		return this.x + this.y;
	}
}