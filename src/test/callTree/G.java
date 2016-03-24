package test.callTree;

public class G {
	public H h = new H();

	public void mG(Target target) {
		h.mH(target);
	}
	
}
