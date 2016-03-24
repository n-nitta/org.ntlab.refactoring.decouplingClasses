package jp.ac.konan.nittaLab.eclipse.plugin.refactoring.decoupleClasses.descriptors;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.refactoring.descriptors.JavaRefactoringContribution;
import org.eclipse.jdt.core.refactoring.descriptors.JavaRefactoringDescriptor;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringDescriptor;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

import jp.ac.konan.nittaLab.eclipse.plugin.refactoring.decoupleClasses.core.DecoupleClassesRefactoring;
import jp.ac.konan.nittaLab.eclipse.plugin.refactoring.decoupleClasses.core.DecoupleClassesRefactoringProcessor;

public class DecoupleClassesRefactoringContribution extends JavaRefactoringContribution {

	@Override
	public Refactoring createRefactoring(JavaRefactoringDescriptor descriptor, RefactoringStatus status) throws CoreException {
		if (!(descriptor instanceof DecoupleClassesDescriptor)) {
			status.addFatalError("Unknown Descriptor");
			return null;
		}
		
		DecoupleClassesRefactoringProcessor processor = new DecoupleClassesRefactoringProcessor((DecoupleClassesDescriptor) descriptor, status);
		return new DecoupleClassesRefactoring(processor);
	}
	
	@Override
	public RefactoringDescriptor createDescriptor() throws IllegalArgumentException {
		return new DecoupleClassesDescriptor();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public RefactoringDescriptor createDescriptor(String id, String project, String description, String comment, Map arguments, int flags) throws IllegalArgumentException {
		return new DecoupleClassesDescriptor(project, description, comment, arguments, flags);
	}
	
}
