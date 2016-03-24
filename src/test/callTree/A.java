package test.callTree;

public class A {
	public F f = new F();
	
	public void mA() {
		f.mF(new Target("Created By A"));
	}
}
