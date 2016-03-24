package test.callTree;

public class Target {
	private String message = "";

	public Target(String newMessage) {
		message = newMessage;
	}
	
	public void printMessage() {
		System.out.println(message);
	}

}
