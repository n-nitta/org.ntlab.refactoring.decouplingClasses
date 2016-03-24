package jp.ac.konan.nittaLab.eclipse.plugin.refactoring.decoupleClasses.handler;

import java.util.Collection;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICodeAssist;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.ITypeRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.NodeFinder;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jdt.ui.refactoring.RefactoringSaveHelper;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import jp.ac.konan.nittaLab.eclipse.plugin.refactoring.decoupleClasses.core.DecoupleClassesRefactoring;
import jp.ac.konan.nittaLab.eclipse.plugin.refactoring.decoupleClasses.core.DecoupleClassesRefactoringProcessor;
import jp.ac.konan.nittaLab.eclipse.plugin.refactoring.decoupleClasses.ui.DecoupleClassesWizard;
import test.searchClassByUsingSearchEngine.SearchClassByUsingSearchEngineTest;

public class DecoupleClassesRefactoringHandler implements IHandler {

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		System.out.println("InformationHidingHandler#addHandlerListener()");
	}

	@Override
	public void dispose() {
		System.out.println("InformationHidingHandler#dispose()");
	}

	/**
	 * 呼び出し元を辿る機能を付け足すテスト
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		checkingHandlerUtil(event);

		IEditorInput activeEditorInput = HandlerUtil.getActiveEditorInput(event);
		ITypeRoot typeRoot = JavaUI.getEditorInputTypeRoot(activeEditorInput);
		ITextSelection selection = getCurrentITextSelection(event);

		// 実行時に選択しているプログラム片を囲っているメソッドを取得する。
		IJavaElement enclosingMethod = getEnclosingMethod(typeRoot, selection);

//		new SearchClassByUsingSearchEngineTest().search(enclosingMethod, new NullProgressMonitor());

		//		InformationHidingRefactoringDescriptor descriptor = new InformationHidingRefactoringDescriptor();
		//		descriptor.setStartMethod(SourceMethod);
		//		descriptor.setTarget();
		//		
		DecoupleClassesRefactoringProcessor processor = new DecoupleClassesRefactoringProcessor(null, null);
		//		RefactoringStatus status= processor.checkInitialConditions(new NullProgressMonitor());

		DecoupleClassesRefactoring refactoring = new DecoupleClassesRefactoring(processor);

		Shell shell = HandlerUtil.getActiveShell(event);
		openWizard(shell, refactoring);

//		InformationHidingWizard wizard = new InformationHidingWizard(/*processor, */refactoring);
//		activate(wizard, shell, "Refactoring");

		return null;
	}

	/**
	 * HandlerUtil#getCurrentSelection()では正しくSelectionが取れない例があった(length, offset共に0になる)ため、両方を使うことにした。
	 */
	private ITextSelection getCurrentITextSelection(ExecutionEvent event) {
		ITextSelection selection = (ITextSelection) HandlerUtil.getCurrentSelection(event);

		if (selection.getOffset() == 0 && selection.getLength() == 0) {
			selection = (ITextSelection) HandlerUtil.getActiveMenuSelection(event);
		}

		return selection;
	}

	private IJavaElement getEnclosingMethod(ITypeRoot typeRoot, ITextSelection selection) {
		try {
			IJavaElement enclosingElement = typeRoot.getElementAt(selection.getOffset());
			if (enclosingElement instanceof IMethod) {
				return enclosingElement;
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}

		return null;
	}

	private void checkingHandlerUtil(ExecutionEvent event) {
		Collection<?>		activeContexts			= HandlerUtil.getActiveContexts(event);
		IEditorPart			activeEditor			= HandlerUtil.getActiveEditor(event);
		String				activeEditorId			= HandlerUtil.getActiveEditorId(event);
		IEditorInput		activeEditorInput		= HandlerUtil.getActiveEditorInput(event);
		ISelection			activeMenuEditorInput	= HandlerUtil.getActiveMenuEditorInput(event);
		Collection<?>		activeMenus				= HandlerUtil.getActiveMenus(event);
		ISelection			activeMenuSelection		= HandlerUtil.getActiveMenuSelection(event);
		System.out.println("InformationHidingHandler#checkingHandlerUtil() activeMenuSelection: " + ((ITextSelection) activeMenuSelection).getText());
		IWorkbenchPart		activePart				= HandlerUtil.getActivePart(event);
		String				activePartId			= HandlerUtil.getActivePartId(event);
		Shell				activeShell				= HandlerUtil.getActiveShell(event);
		IWorkbenchSite		activeSite				= HandlerUtil.getActiveSite(event);
		IWorkbenchWindow	activeWorkbenchWindow	= HandlerUtil.getActiveWorkbenchWindow(event);
		ISelection			currentSelection		= HandlerUtil.getCurrentSelection(event);
		System.out.println("InformationHidingHandler#checkingHandlerUtil() currentSelection: " + ((ITextSelection) currentSelection).getText());
		Object				showInInput				= HandlerUtil.getShowInInput(event);
		ISelection			showInSelection			= HandlerUtil.getShowInSelection(event);

		IJavaElement javaElement = JavaUI.getEditorInputJavaElement(activeEditorInput);
		ITypeRoot typeRoot = JavaUI.getEditorInputTypeRoot(activeEditorInput);
	}

	private IJavaElement[] codeResolve(ExecutionEvent event) throws JavaModelException {
		IEditorInput activeEditorInput = HandlerUtil.getActiveEditorInput(event);
		ITypeRoot input = JavaUI.getEditorInputTypeRoot(activeEditorInput);

		if (input instanceof ICodeAssist) {
			if (input instanceof ICompilationUnit) {
				reconcile(input);
			}
			ITextSelection selection = getCurrentITextSelection(event);
			IJavaElement[] elements = ((ICodeAssist)input).codeSelect(selection.getOffset() + selection.getLength(), 0);
			if (elements.length > 0) {
				return elements;
			}
		}

		return new IJavaElement[0];
	}

	private void reconcile(ITypeRoot input) throws JavaModelException {
		((ICompilationUnit) input).reconcile(
				0, // no AST
				false /* don't force problem detection */,
				null /* use primary owner */,
				null /* no progress monitor */);
	}

	public boolean activate(RefactoringWizard wizard, Shell parent, String dialogTitle) {
		RefactoringSaveHelper saveHelper= new RefactoringSaveHelper(RefactoringSaveHelper.SAVE_REFACTORING);
		if (!canActivate(saveHelper, parent))
			return false;

		try {
			RefactoringWizardOpenOperation op = new RefactoringWizardOpenOperation(wizard);
			int result= op.run(parent, dialogTitle);
			//			RefactoringStatus fStatus = op.getInitialConditionCheckingStatus();
			if (result == IDialogConstants.CANCEL_ID || result == RefactoringWizardOpenOperation.INITIAL_CONDITION_CHECKING_FAILED) {
				saveHelper.triggerIncrementalBuild();
				return false;
			} else {
				return true;
			}
		} catch (InterruptedException e) {
			return false; // User action got cancelled
		}
	}

	private boolean canActivate(RefactoringSaveHelper saveHelper, Shell shell) {
		return saveHelper.saveEditors(shell);
	}

	private void openWizard(Shell shell, DecoupleClassesRefactoring refactoring) {
		WizardDialog dialog = new WizardDialog(null, new DecoupleClassesWizard(refactoring));
		dialog.setPageSize(500, 300);
		dialog.open();
	}

	private ASTNode findASTNodeByNodeFinderPerform(ITypeRoot typeRoot, CompilationUnit node, ITextSelection selection) {
		ASTNode target = null;

		try {
			target = NodeFinder.perform(node, selection.getOffset(), selection.getLength(), typeRoot);
		} catch (JavaModelException e) {
		}

		if (target == null) {
			target = NodeFinder.perform(node, selection.getOffset(), selection.getLength());
		}

		return target;
	}

	@Override
	public boolean isEnabled() {
		System.out.println(this.getClass().getSimpleName() + "#" + "isEnabled()");
		return true;
	}

	@Override
	public boolean isHandled() {
		System.out.println(this.getClass().getSimpleName() + "#" + "isHandled()");
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
		System.out.println(this.getClass().getSimpleName() + "#" + "removeHandlerListener()");
	}

}
