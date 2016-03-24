package test.callTree;

public class F {
	public H h = new H();
	public I i = new I();

	public void mF(Target target) {
		i.mI(target);
		h.mH(target);
	}
}
