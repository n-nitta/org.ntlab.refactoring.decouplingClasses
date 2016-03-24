package test.searchClassByUsingSearchEngine;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchRequestor;

public class CallersSearchRequestor extends SearchRequestor {
	private List<IMember> callers = new ArrayList<IMember>();

	@Override
	public void acceptSearchMatch(SearchMatch match) throws CoreException {
		if (match.getElement() == null) {
			return;
		}

		if (match.getElement() instanceof IMember) {
			IMember member = (IMember) match.getElement();
			switch (member.getElementType()) {
			case IJavaElement.METHOD:
			case IJavaElement.TYPE:
			case IJavaElement.FIELD:
			case IJavaElement.INITIALIZER:
				callers.add(member);
				break;
			}
			System.out.println();
		}
	}
	
	@Override
	public void beginReporting() {
		System.out.println(this.getClass().getSimpleName() + "#beginReporting()");
	}

	@Override
	public void endReporting() {
		System.out.println(this.getClass().getSimpleName() + "#endReporting()");
	}
	
	@Override
	public void enterParticipant(SearchParticipant participant) {
		System.out.println(this.getClass().getSimpleName() + "#enterParticipant()");
	}

	@Override
	public void exitParticipant(SearchParticipant participant) {
		System.out.println(this.getClass().getSimpleName() + "#exitParticipant()");
	}

	public List<IMember> getCallers() {
		return callers;
	}


}
