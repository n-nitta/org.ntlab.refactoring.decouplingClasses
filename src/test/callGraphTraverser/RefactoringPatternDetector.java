package test.callGraphTraverser;

public class RefactoringPatternDetector {
	private static void explore(MethodDeclaration targetMethod) {
		System.out.print("Now looking for ");
		System.out.print(targetMethod.getDeclaringClass().getName());
		System.out.println("#" + targetMethod.getName() + "()");

		for (MethodInvocation invoker: targetMethod.getSource()) {
			if (!invoker.getSource().isRefactored()) {
				explore(invoker.getSource());
			}
		}
		targetMethod.refactor();
		System.err.println("Refactored " + targetMethod.getDeclaringClass().getName() + "#" + targetMethod.getName() + "()");
		
		for (MethodInvocation invoker: targetMethod.getSource()) {
			invoker.refactor();
			System.err.println("    Refactored " + targetMethod.getDeclaringClass().getName().toLowerCase() + "." + targetMethod.getName() + "() in " + invoker.getSource().getDeclaringClass().getName() + "#" + invoker.getSource().getName() + "()");
		}
	}

	public static void startExplore(GraphBuilder builder) {
		Class target = builder.getTarget();
		explore(target.getMethod("constructor"));
	}
}
