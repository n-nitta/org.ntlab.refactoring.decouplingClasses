package test.callGraphTraverser;

public class MethodInvocation {
	private MethodDeclaration source;
	private MethodDeclaration destination;
	private boolean refactored = false;
	
	/**
	 * @param source 呼び出し元のメソッド
	 * @param destination 呼び出し先のメソッド
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
