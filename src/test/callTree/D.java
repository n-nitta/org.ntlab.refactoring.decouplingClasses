package test.callTree;

public class D {
	public I i = new I();
	
	public void mD() {
		i.mI(new Target("Created By D"));
	}
}
