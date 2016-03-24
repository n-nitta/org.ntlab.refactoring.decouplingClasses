package test.callGraphTraverser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MethodDeclaration {
	private Class declaringClass;
	private String name;
	private Class returnType;
	private List<Class> parameters = new ArrayList<>();
	private boolean refactored = false;
	
	/** Invocations in this method body. */
	private List<MethodInvocation> invocations = new ArrayList<>();
	
	/** Invocations which invokes this method. */
	private List<MethodInvocation> invokers = new ArrayList<>();

	public MethodDeclaration(String methodName, Class returnType, Class... parameters) {
		name = methodName;
		this.returnType = returnType;
		this.parameters.addAll(Arrays.asList(parameters));
	}
	
	public void setDeclaringClass(Class declaringClass) {
		this.declaringClass = declaringClass;
	}
	
	public Class getDeclaringClass() {
		return declaringClass;
	}
	
	public void rename(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void changeReturnType(Class returnType) {
		this.returnType = returnType;
	}
	
	public Class getReturnType() {
		return returnType;
	}
	
	/**
	 * このメソッドの呼び出しを追加する。
	 * @param invocation 呼び出し元
	 */
	public void addInvocation(MethodInvocation invocation) {
		invocations.add(invocation);
	}
	
	public List<MethodInvocation> getSource() {
		return invokers;
	}

	public boolean equals(String name, Class... params) {
		if (!this.name.equals(name))
			return false;
		
		if (parameters.size() != params.length)
			return false;
		
		for (int i = 0; i < parameters.size(); i++) {
			parameters.get(i).equals(params[i]);
		}
		
		return true;
	}

	public void invoke(MethodDeclaration destinationMethod) {
		MethodInvocation invocation = new MethodInvocation(this, destinationMethod);
		invocations.add(invocation);
		destinationMethod.invokedBy(invocation);
	}

	private void invokedBy(MethodInvocation invocation) {
		invokers.add(invocation);
	}

	public void print() {
		// Return Type, Method Name, Parameters...
		System.out.print(returnType.getName() + " " + name + "(");
		String paramSum = "";
		for (Class param: parameters) {
			if (paramSum.length() != 0)
				paramSum += ", ";
			paramSum += param.getName();
		}
		System.out.print(paramSum + ")");
		
		if (invokers.size() != 0) {
			// Invoked by ...
			System.out.print(", Invoked By: ");
			String invokerSum = "";
			for (MethodInvocation invoker: invokers) {
				if (invokerSum.length() != 0)
					invokerSum += ", ";
				invokerSum += invoker.getSource().declaringClass.getName() + "#";
				invokerSum += invoker.getSource().getName() + "()";
			}
			System.out.print(invokerSum);
		}
		
		if (invocations.size() != 0) {
			// Invocation in this method body
			System.out.print(", Invokes: ");
			String invocationSum = "";
			for (MethodInvocation invocation: invocations) {
				if (invocationSum.length() != 0)
					invocationSum += ", ";
				invocationSum += invocation.getDestination().declaringClass.getName() + "#";
				invocationSum += invocation.getDestination().getName() + "()";
			}
			System.out.print(invocationSum);
		}
		System.out.println();
	}

	public void refactor() {
		refactored = true;
	}
	
	public boolean isRefactored() {
		return refactored;
	}

}
