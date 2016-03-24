package jp.ac.konan.nittaLab.eclipse.plugin.refactoring.decoupleClasses.handler;

import java.util.Collection;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.ILocalVariable;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.NodeFinder;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.internal.corext.dom.LinkedNodeFinder;
import org.eclipse.jdt.internal.corext.refactoring.util.RefactoringASTParser;
import org.eclipse.jdt.internal.ui.actions.SelectionConverter;
import org.eclipse.jdt.internal.ui.javaeditor.EditorUtility;
import org.eclipse.jdt.internal.ui.javaeditor.JavaEditor;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jdt.ui.SharedASTProvider;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import jp.ac.konan.nittaLab.eclipse.plugin.refactoring.decoupleClasses.core.ASTVisitorImpl;
import jp.ac.konan.nittaLab.eclipse.plugin.refactoring.decoupleClasses.core.DecoupleClassesRefactoring;
import jp.ac.konan.nittaLab.eclipse.plugin.refactoring.decoupleClasses.core.DecoupleClassesRefactoringProcessor;
import jp.ac.konan.nittaLab.eclipse.plugin.refactoring.decoupleClasses.ui.DecoupleClassesWizard;

public class InformationHidingHandlerOld implements IHandler {
	private static IJavaElement element;

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		System.out.println("InformationHidingHandler#addHandlerListener()");
	}

	@Override
	public void dispose() {
		System.out.println("InformationHidingHandler#dispose()");
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println("InformationHidingHandler#execute()");
		JavaEditor editor = (JavaEditor) HandlerUtil.getActiveEditor(event);
		ISelection activeMenuSelection = HandlerUtil.getActiveMenuSelection(event); // TextSelection‚ª“¾‚ç‚ê‚½
		
		IJavaElement[] elements = null;
		
		try {
			elements= SelectionConverter.codeResolve(editor);
		} catch (JavaModelException e) { 
			e.printStackTrace();
		}
		
		element = elements[0];
		
		for (int i = 0; i < elements.length; i++) {
			if (elements[i] != null && elements[i].getElementType() == IJavaElement.METHOD) {
				try {
					IMethod method = (IMethod) elements[i];
					if (method.isMainMethod()) {
						System.out.println("MainMethod : " + method);
					} else {
						System.out.println("Method : " + method);
					}
				} catch (JavaModelException e) {
				}
			} else if (elements[i] != null && elements[i].getElementType() == IJavaElement.TYPE) {
				try {
					IType type = (IType) elements[i];
					if (type.isClass()) {
						System.out.println("Class : " + type);
//						startingClassName = type.toString();
					}
				} catch (JavaModelException e) {
				}
			} else if (elements[i] != null && elements[i].getElementType() == IJavaElement.LOCAL_VARIABLE) {
				System.out.println("Var");
				ILocalVariable var = (ILocalVariable) elements[i];
				if (var.isParameter()) {
					System.out.println("Var : " + var);
				} else {
					System.out.println("Var2 : " + var);
				}
			} else if (elements[i] != null && elements[i].getElementType() == IJavaElement.FIELD) {
				System.out.println("Field");
				IField field = (IField) elements[i];
				try {
					if (field.isEnumConstant()) {
						System.out.println("Field : " + field);
					}
				} catch (JavaModelException e) {
					e.printStackTrace();
				}
			}
		}
		
		checkingHandlerUtil(event);
		
		ITypeRoot typeRoot = SelectionConverter.getInput((JavaEditor) HandlerUtil.getActiveEditor(event));
		IJavaProject javaProject = typeRoot.getJavaProject();
		IJavaElement[] codeSelect = null;
		try {
			codeSelect = typeRoot.codeSelect(((ITextSelection) activeMenuSelection).getOffset(), ((ITextSelection) activeMenuSelection).getLength());
		} catch (JavaModelException e) {
			e.printStackTrace();
		}

		CompilationUnit node = RefactoringASTParser.parseWithASTProvider(typeRoot, true, null);
		parseASTNodeByUsingVisitor((ICompilationUnit) typeRoot);

		// ‘I‘ð‚µ‚½—v‘f‚Ì‚·‚×‚Ä‚ÌoŒ»êŠ‚ðŒŸõ
		ASTNode[] sameNodes = findSameNodes(editor);
		for (int i = 0; i < sameNodes.length; i++) {
			System.out.println(sameNodes[i].getRoot().toString());
		}
		
		ASTNode target = findASTNodeByNodeFinderPerform(typeRoot, node, (ITextSelection) activeMenuSelection);
		ASTNode root = target.getRoot();
		IJavaElement[] javaElement = findJavaElement(editor);

		tryInformationHidindRefactoring(typeRoot, node, (ITextSelection) activeMenuSelection, HandlerUtil.getActiveShell(event));

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
		
		System.err.println();
	}

	private void parseASTNodeByUsingVisitor(ICompilationUnit element) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(element);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		CompilationUnit node = (CompilationUnit) parser.createAST(new NullProgressMonitor());
		node.accept(new ASTVisitorImpl());
	}

	private IJavaElement[] findJavaElement(JavaEditor editor) {
		IJavaElement[] javaElement = null;
		try {
			javaElement = SelectionConverter.codeResolve(editor);
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return javaElement;
	}

	private ASTNode[] findSameNodes(JavaEditor editor) {
		ISourceViewer viewer= editor.getViewer();
		Point fOriginalSelection= viewer.getSelectedRange();
		CompilationUnit root= SharedASTProvider.getAST((ICompilationUnit) EditorUtility.getEditorInputJavaElement(editor, false), SharedASTProvider.WAIT_YES, null);
		ASTNode selectedNode= NodeFinder.perform(root, fOriginalSelection.x, fOriginalSelection.y);
		return LinkedNodeFinder.findByNode(root, (SimpleName) selectedNode);
	}

	private void tryInformationHidindRefactoring(ITypeRoot typeRoot, CompilationUnit node, ITextSelection selection, Shell shell) {
		ASTNode target = findASTNodeByNodeFinderPerform(typeRoot, node, selection);

		DecoupleClassesRefactoring refactoring = null;
		refactoring = new DecoupleClassesRefactoring(new DecoupleClassesRefactoringProcessor(null, null));			

		if (target.getNodeType() == ASTNode.TYPE_METHOD_REFERENCE) {
			System.out.println("TYPE_METHOD_REFERENCE");
		}

		if (refactoring != null) {
			openWizard(shell, refactoring);
		}
	}

	private void openWizard(Shell shell, DecoupleClassesRefactoring refactoring) {
		WizardDialog dialog = new WizardDialog(null, new DecoupleClassesWizard(refactoring));
		dialog.setPageSize(500, 300);
		dialog.open();
		//		//			new RefactoringStarter().activate(new InformationHidingWizard(refactoring), shell, refactoring.getName(), RefactoringSaveHelper.SAVE_REFACTORING);
		//		RefactoringWizardOpenOperation op = new RefactoringWizardOpenOperation(new InformationHidingWizard(refactoring));
		//		try {
		//			op.run(shell, refactoring.getName());
		//		} catch (InterruptedException e) {
		//			e.printStackTrace();
		//		}
		//		return;
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
		System.out.println("InformationHidingHandler#isEnabled()");
		return true;
	}

	@Override
	public boolean isHandled() {
		System.out.println("InformationHidingHandler#isHandled()");
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
		System.out.println("InformationHidingHandler#removeHandlerListener()");
	}

	public static IJavaElement getElement() {
		return element;
	}

	public void setElement(IJavaElement element) {
		this.element = element;
	}

}
