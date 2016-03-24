package test.callTree;

public class H {
	public I i = new I();

	public void mH(Target target) {
		i.mI(target);
	}

}
