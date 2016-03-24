package test.callTree;

public class B {
	public G g = new G();
	
	public void mB() {
		g.mG(new Target("Created By B"));
	}
	
}
