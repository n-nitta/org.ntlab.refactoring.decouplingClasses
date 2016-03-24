package testCase1.after;

public class A {
	B b = new B();
	D d = new D();

	public void m() {
		F f = b.getF();
		d.setF(f);
	}
}
