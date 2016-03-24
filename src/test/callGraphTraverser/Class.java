package test.callGraphTraverser;

import java.util.ArrayList;
import java.util.List;

public class Class {
	private String name;
	private List<MethodDeclaration> methods = new ArrayList<>();
	private String packageName = "";

	public Class(String name) {
		this.name = name;
	}
	
	public void rename(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setPackage(String name) {
		packageName = name;
	}

	public String getPackage() {
		return packageName;
	}
	
	public void addMethod(String methodName, Class returnType, Class... paramType) {
		MethodDeclaration method = new MethodDeclaration(methodName, returnType, paramType);
		method.setDeclaringClass(this);
		methods.add(method);
	}
	
	public MethodDeclaration getMethod(String name, Class... params) {
		for (MethodDeclaration method: methods) {
			if (method.equals(name, params)) {
				return method;
			}
		}
		
		return null;
	}
	
	public boolean equals(Class obj) {
		if (!obj.getName().equals(name))
			return false;
		
		if (!obj.getPackage().equals(packageName))
			return false;
		
		return true;
	}
	
	public void print() {
		System.out.println(packageName + "." + name);
		for (MethodDeclaration method: methods) {
			method.print();
		}
		System.out.println();
	}

	public List<MethodDeclaration> getMethods() {
		return methods;
	}

}
