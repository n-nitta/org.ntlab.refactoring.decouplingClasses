package jp.ac.konan.nittaLab.eclipse.plugin.refactoring.decoupleClasses.core;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.NodeFinder;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.NullChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;
import org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor;
import org.eclipse.ltk.core.refactoring.participants.SharableParticipants;

import jp.ac.konan.nittaLab.eclipse.plugin.refactoring.decoupleClasses.descriptors.DecoupleClassesDescriptor;

public class DecoupleClassesRefactoringProcessor extends RefactoringProcessor {
	private String newClassName = "";
	private String packageName = "";
	private String targetClassName;

	public DecoupleClassesRefactoringProcessor(DecoupleClassesDescriptor descriptor, RefactoringStatus status) {
	}

	@Override
	public Object[] getElements() {
		System.out.println(this.getClass().getSimpleName() + "#getElements()");
		return null;
	}

	@Override
	public String getIdentifier() {
		System.out.println(this.getClass().getSimpleName() + "#getIdentifier()");
		return null;
	}

	@Override
	public String getProcessorName() {
		System.out.println(this.getClass().getSimpleName() + "#getProcessorName()");
		return null;
	}

	@Override
	public boolean isApplicable() throws CoreException {
		System.out.println(this.getClass().getSimpleName() + "#isApplicable()");
		return false;
	}

	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm) throws CoreException, OperationCanceledException {
		System.out.println(this.getClass().getSimpleName() + "#checkInitialConditions()");
		return new RefactoringStatus();
	}

	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm, CheckConditionsContext context) throws CoreException, OperationCanceledException {
		System.out.println(this.getClass().getSimpleName() + "#checkFinalConditions()");
		return new RefactoringStatus();
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException, OperationCanceledException {
		System.out.println(this.getClass().getSimpleName() + "#createChange()");
		return new NullChange();
	}

	@Override
	public RefactoringParticipant[] loadParticipants(RefactoringStatus status, SharableParticipants sharedParticipants) throws CoreException {
		System.out.println(this.getClass().getSimpleName() + "#loadParticipants()");
		return new RefactoringParticipant[0];
	}
	
	public ASTNode findNode(CompilationUnit cu, SearchMatch searchResult) {
		return findNode(cu, searchResult.getOffset(), searchResult.getLength());
	}
	
	public ASTNode findNode(CompilationUnit cu, int start, int length) {
		return NodeFinder.perform(cu, start, length);
	}

	public void setNewClassName(String text) {
		this.newClassName = text;
	}
	
	public String getNewClassName() {
		return newClassName;
	}

	public void setPackageName(String packageName) {
		this.packageName  = packageName;
	}
	
	public String getPackageName() {
		return packageName;
	}

	public String getTargetClassName() {
		return targetClassName;
	}

	public void setTargetClassName(String className) {
		this.targetClassName = className;
	}

}
