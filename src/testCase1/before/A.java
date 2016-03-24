package testCase1.before;

public class A {
	B b = new B();
	D d = new D();

	public void m() {
		E e = b.getE();
		d.setE(e);
	}
}
