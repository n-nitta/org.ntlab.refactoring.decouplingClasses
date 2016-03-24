package jp.ac.konan.nittaLab.eclipse.plugin.refactoring.decoupleClasses.descriptors;

import java.util.Map;

import org.eclipse.jdt.core.refactoring.descriptors.JavaRefactoringDescriptor;

import jp.ac.konan.nittaLab.eclipse.plugin.refactoring.decoupleClasses.core.DecoupleClassesRefactoring;

public class DecoupleClassesDescriptor extends JavaRefactoringDescriptor {

	protected DecoupleClassesDescriptor() {
		super(DecoupleClassesRefactoring.ID);
	}

	public DecoupleClassesDescriptor(String project, String description, String comment, Map<String, String> arguments, int flags) throws IllegalArgumentException {
		super(DecoupleClassesRefactoring.ID, project, description, comment, arguments, flags);
	}
}
