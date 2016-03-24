package test.callTree;

public class C {
	public H h = new H();
	
	public void mC() {
		h.mH(new Target("Created By C"));
	}
}
