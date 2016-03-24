package test.callGraphTraverser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class GraphBuilder {
	private Class target = null;
	private Map<String, Class> classes = new HashMap<>();

	public Class findClassByName(String name) {
		return classes.get(name);
	}

	public void addClass(String name) {
		if (findClassByName(name) != null) {
			System.err.println(this.getClass().toString() + " addClass(): Class " + name + " is already exists!");
			System.err.println("This operation has been ignored!");
			return;
		}

		Class target = new Class(name);
		classes.put(name, target);
	}

	public void addMethod(String className, String methodName, String returnType, String... params) {
		Class destinationClass = findClassByName(className);

		if (destinationClass == null) {
			System.err.println(this.getClass().toString() + " addMethod(): Class " + className + " is not found!");
			System.err.println("This operation has been ignored!");
			return;
		}

		Class[] paramClasses = toClasses(params);
		if (destinationClass.getMethod(methodName, paramClasses) != null) {
			System.err.println(this.getClass().toString() + " addClass(): Method " + methodName + " is already exists!");
			System.err.println("This operation has been ignored!");
			return;
		}

		destinationClass.addMethod(methodName, findClassByName(returnType), paramClasses);
	}

	private Class[] toClasses(String... params) {
		Class[] paramClasses = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			paramClasses[i] = findClassByName(params[i]);
		}
		return paramClasses;
	}

	public void invoke(String sourceName, String sourceMethodName, String[] sourceMethodParams, String destName, String destMethodName, String[] destMethodParams) {
		Class sourceClass = findClassByName(sourceName);
		if (sourceClass == null) {
			System.err.println(this.getClass().toString() + " invoke(" + sourceName + "#" + sourceMethodName + "() -> " + destName + "#" + destMethodName + "()" + ")");
			System.err.println("Class " + sourceName + " is not found!");
			System.err.println("This operation has been ignored!");
			return;
		}
		MethodDeclaration sourceMethod = sourceClass.getMethod(sourceMethodName, toClasses(sourceMethodParams));
		if (sourceMethod == null) {
			System.err.println(this.getClass().toString() + " invoke(" + sourceName + "#" + sourceMethodName + "() -> " + destName + "#" + destMethodName + "()" + ")");
			System.err.println("Method " + sourceMethodName + " is not found!");
			System.err.println("This operation has been ignored!");
			return;
		}

		Class destClass = findClassByName(destName);
		if (destClass == null) {
			System.err.println(this.getClass().toString() + " invoke(" + sourceName + "#" + sourceMethodName + "() -> " + destName + "#" + destMethodName + "()" + ")");
			System.err.println("Class " + destName + " is not found!");
			System.err.println("This operation has been ignored!");
			return;
		}
		MethodDeclaration destMethod = destClass.getMethod(destMethodName, toClasses(destMethodParams));
		if (destMethod == null) {
			System.err.println(this.getClass().toString() + " invoke(" + sourceName + "#" + sourceMethodName + "() -> " + destName + "#" + destMethodName + "()" + ")");
			System.err.println("Method " + destMethodName + " is not found!");
			System.err.println("This operation has been ignored!");
			return;
		}

		sourceMethod.invoke(destMethod);
	}

	public void setTarget(String targetClassName) {
		Class targetClass = findClassByName(targetClassName);
		if (targetClass == null) {
			System.err.println(this.getClass().toString() + " setTarget(): Class " + targetClassName + " is not found!");
			System.err.println("This operation has been ignored!");
			return;
		}
		target = targetClass;
	}

	public Class getTarget() {
		return target;
	}

	public void print() {
		System.out.println("Target: " + target.getName());
		System.out.println();

		for (Iterator<Entry<String, Class>> i = classes.entrySet().iterator(); i.hasNext();) {
			i.next().getValue().print();
		}
	}

}
