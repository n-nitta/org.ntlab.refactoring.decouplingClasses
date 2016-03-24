package test.callGraphTraverser;

public class MethodInvocation {
	private MethodDeclaration source;
	private MethodDeclaration destination;
	private boolean refactored = false;
	
	/**
	 * @param source �Ăяo�����̃��\�b�h
	 * @param destination �Ăяo����̃��\�b�h
	 */
	public MethodInvocation(MethodDeclaration source, MethodDeclaration destination) {
		this.source = source;
		this.destination = destination;
	}
	
	public MethodDeclaration getSource() {
		return source;
	}
	
	public MethodDeclaration getDestination() {
		return destination;
	}

	public void refactor() {
		refactored = true;
	}
	
	public boolean isRefactored() {
		return refactored;
	}
	
}
