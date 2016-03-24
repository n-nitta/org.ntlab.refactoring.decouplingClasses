package jp.ac.konan.nittaLab.eclipse.plugin.refactoring.decoupleClasses.core;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring;

public class DecoupleClassesRefactoring extends ProcessorBasedRefactoring {
	public static final String ID = "jp.ac.konan.nittaLab.eclipse.plugin.refactoring.decoupleClasses";
	
	public DecoupleClassesRefactoring(DecoupleClassesRefactoringProcessor processor) {
		super(processor);
	}

	@Override
	public String getName() {
		System.out.println(this.getClass().getSimpleName() + "#getName()");
		return this.getClass().getName();
	}

	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm) throws CoreException, OperationCanceledException {
		System.out.println(this.getClass().getSimpleName() + "#checkInitialConditions()");
		return new RefactoringStatus();
	}

	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm) throws CoreException, OperationCanceledException {
		System.out.println(this.getClass().getSimpleName() + "#checkFinalConditions()");
		return new RefactoringStatus();
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException, OperationCanceledException {
		System.out.println(this.getClass().getSimpleName() + "#createChange()");
		return null;
	}
	
}
