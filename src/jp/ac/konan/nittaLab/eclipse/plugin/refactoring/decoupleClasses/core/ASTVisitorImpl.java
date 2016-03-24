package jp.ac.konan.nittaLab.eclipse.plugin.refactoring.decoupleClasses.core;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class ASTVisitorImpl extends ASTVisitor {
	@Override
	public boolean visit(CompilationUnit node) {
		System.err.println(node);
		return super.visit(node);
	}
}
