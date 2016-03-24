package test.searchClassByUsingSearchEngine;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;

public class SearchClassByUsingSearchEngineTest {

	public void search(IJavaElement calee, IProgressMonitor monitor) {
		SearchPattern pattern = SearchPattern.createPattern(
				calee,
				IJavaSearchConstants.REFERENCES,
				SearchPattern.R_EXACT_MATCH | SearchPattern.R_CASE_SENSITIVE | SearchPattern.R_ERASURE_MATCH);
		SearchParticipant[] participant = new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() };
		IJavaSearchScope scope = SearchEngine.createWorkspaceScope();
		CallersSearchRequestor requestor = new CallersSearchRequestor();
		
		try {
			new SearchEngine().search(pattern, participant, scope, requestor, monitor);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		System.out.println(this.getClass().getSimpleName() + "#execute()");
		System.out.println("[Calee]");
		System.out.println("Name: " + calee.getElementName());
		System.out.println("Type: " + typeConstantToString(calee.getElementType()));
		System.out.println("Parent Name: " + calee.getParent().getElementName());
		System.out.println("Parent Type: " + typeConstantToString(calee.getParent().getElementType()));
		System.out.println();
		
		System.out.println("[Caller]");
		for (IMember caller: requestor.getCallers()) {
			System.out.println("Name: " + caller.getElementName());
			System.out.println("Type: " + typeConstantToString(caller.getElementType()));
			System.out.println("Parent Name: " + caller.getParent().getElementName());
			System.out.println("Parent Type: " + typeConstantToString(caller.getParent().getElementType()));
		}
	}
	
	private String typeConstantToString(int typeConstant) {
		switch (typeConstant) {
		case IJavaElement.JAVA_MODEL:
			return "JavaModel";
		case IJavaElement.JAVA_PROJECT:
			return "JavaProject";
		case IJavaElement.PACKAGE_FRAGMENT_ROOT:
			return "PackageFragmentRoot";
		case IJavaElement.PACKAGE_FRAGMENT:
			return "PackageFragment";
		case IJavaElement.COMPILATION_UNIT:
			return "CompilationUnit";
		case IJavaElement.CLASS_FILE:
			return "ClassFile";
		case IJavaElement.TYPE:
			return "Type";
		case IJavaElement.FIELD:
			return "Field";
		case IJavaElement.METHOD:
			return "Method";
		case IJavaElement.INITIALIZER:
			return "Initializer";
		case IJavaElement.PACKAGE_DECLARATION:
			return "PackageDeclaration";
		case IJavaElement.IMPORT_CONTAINER:
			return "ImportContainer";
		case IJavaElement.IMPORT_DECLARATION:
			return "ImportDeclaration";
		case IJavaElement.LOCAL_VARIABLE:
			return "LocalVariable";
		case IJavaElement.TYPE_PARAMETER:
			return "TypeParameter";
		case IJavaElement.ANNOTATION:
			return "Annotation";
		}
		return "Unknown";
	}

}
