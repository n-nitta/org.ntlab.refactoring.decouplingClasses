package test.callTree;

public class E {
	public J j = new J();
	
	public void mE() {
		j.mJ(new Target("Created By E"));
	}
}
