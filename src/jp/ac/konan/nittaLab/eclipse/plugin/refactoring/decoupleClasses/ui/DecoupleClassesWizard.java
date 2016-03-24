package jp.ac.konan.nittaLab.eclipse.plugin.refactoring.decoupleClasses.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import jp.ac.konan.nittaLab.eclipse.plugin.refactoring.decoupleClasses.core.DecoupleClassesRefactoring;
import jp.ac.konan.nittaLab.eclipse.plugin.refactoring.decoupleClasses.core.DecoupleClassesRefactoringProcessor;
import jp.ac.konan.nittaLab.eclipse.plugin.refactoring.decoupleClasses.core.DecoupleClassesRefactoringPlugin;

public class DecoupleClassesWizard extends RefactoringWizard {
	private DecoupleClassesRefactoringProcessor processor;
	
	public DecoupleClassesWizard(DecoupleClassesRefactoring refactoring) {
		super(refactoring, DIALOG_BASED_USER_INTERFACE);
		processor = (DecoupleClassesRefactoringProcessor) refactoring.getProcessor();
		setDefaultPageTitle(DecoupleClassesRefactoringPlugin.REFACTORING_NAME);
	}
	
	@Override
	protected void addUserInputPages() {
		addPage(new DecoupleClassesUserInputPage(processor));
	}
	
	public class DecoupleClassesUserInputPage extends UserInputWizardPage {
		private DecoupleClassesRefactoringProcessor processor;
		private static final String DESCRIPTION = "Specify where to hide the method invocation.";
		
		public DecoupleClassesUserInputPage(DecoupleClassesRefactoringProcessor processor) {
			super(DecoupleClassesRefactoringPlugin.REFACTORING_NAME);
			this.processor = processor;
			setTitle(DecoupleClassesRefactoringPlugin.REFACTORING_NAME);
			setImageDescriptor(DecoupleClassesRefactoringPlugin.getInstance().getImageRegistry().getDescriptor(DecoupleClassesRefactoringPlugin.IMG_DESCRIPTION));
			setDescription(DESCRIPTION);
		}
		
		@Override
		public void createControl(Composite parent) {
			initializeDialogUnits(parent);
			
			Composite result = new Composite(parent, SWT.NONE);
			result.setLayout(new GridLayout(2, false));
			
			createNewClassInput(result);
			createTargetClassesInput(result);
			
			setControl(result);
			
			validate();
		}
		
		private Group createGroup(Composite parent, String caption) {
			Group group = new Group(parent, SWT.None);
			group.setLayout(new GridLayout(2, false));
			group.setText(caption);
			
			GridData gridData = new GridData(GridData.FILL_BOTH);
			gridData.horizontalSpan = 2;
			group.setLayoutData(gridData);

			return group;
		}

		private void createNewClassInput(Composite result) {
			Group group = createGroup(result, "New class");
			
			Label nameLabel = new Label(group, SWT.LEAD);
			nameLabel.setText("Class name:");
			
			final Text nameText = new Text(group, SWT.SINGLE | SWT.BORDER);
			nameText.setText("NewClass");
			nameText.setFocus();
			nameText.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent e) {
					processor.setNewClassName(nameText.getText());
					validate();
				}
			});
			nameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			
			
			Label packageLabel = new Label(group, SWT.LEAD);
			packageLabel.setText("Package name:");
			
			final Text packageText = new Text(group, SWT.SINGLE | SWT.BORDER);
			packageText.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent e) {
					processor.setPackageName(packageText.getText());
					validate();
				}
			});
			packageText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}

		private void createTargetClassesInput(Composite parent) {
			Group group = createGroup(parent, "Classes to decouple");
			
			Label targetLabel = new Label(group, SWT.LEAD);
			targetLabel.setText("Target class:");
			
			final Text targetText = new Text(group, SWT.SINGLE | SWT.BORDER);
			targetText.setText("Target Class");
			targetText.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent e) {
					processor.setTargetClassName(targetText.getText());
					validate();
				}
			});
			targetText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			
			
			Label l = new Label(group, SWT.NONE);
			l.setText("Client classes:");
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.horizontalSpan = 2;
			gridData.verticalIndent = 5;
			l.setLayoutData(gridData);
			
			CheckboxTableViewer tableViewer = CheckboxTableViewer.newCheckList(group, SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
			Table table = tableViewer.getTable();
			table.setLinesVisible(true);
			GridData tableGridData = new GridData(GridData.FILL_BOTH);
			tableGridData.horizontalSpan = 2;
			table.setLayoutData(tableGridData);
			
			// Tableのモデルを制御するプロバイダー
			tableViewer.setContentProvider(new IStructuredContentProvider() {
				@Override
				public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
					System.out.println(getClass().getSimpleName() + "#inputChanged()");
				}
				@Override
				public void dispose() {
					System.out.println(getClass().getSimpleName() + "#dispose()");
				}
				@SuppressWarnings("unchecked")
				@Override
				public Object[] getElements(Object inputElement) {
					System.out.println(getClass().getSimpleName() + "#getElements()");
					return ((List<String>) inputElement).toArray();
				}
			});

			// Tableのビューを制御するプロバイダ
			tableViewer.setLabelProvider(new ITableLabelProvider() {
				@Override
				public void removeListener(ILabelProviderListener listener) {
					System.out.println(getClass().getSimpleName() + "#removeListener()");
				}
				@Override
				public boolean isLabelProperty(Object element, String property) {
					System.out.println(getClass().getSimpleName() + "#isLabelProperty()");
					return false;
				}
				@Override
				public void dispose() {
					System.out.println(getClass().getSimpleName() + "#dispose()");
				}
				@Override
				public void addListener(ILabelProviderListener listener) {
					System.out.println(getClass().getSimpleName() + "#addListener()");
				}
				@Override
				public String getColumnText(Object element, int columnIndex) {
					System.out.println(getClass().getSimpleName() + "#getColumnText()");
					return (String) element;
				}
				@Override
				public Image getColumnImage(Object element, int columnIndex) {
					System.out.println(getClass().getSimpleName() + "#getColumnImage()");
					return null;
				}
			});
			
			tableViewer.add("A");
			tableViewer.add("B");
//			List<String> list = new ArrayList<>();
//			list.add("A");
//			list.add("B");
//			tableViewer.setInput(list);
		}
		
		private void validate() {
			if (processor.getNewClassName().isEmpty()) {
				setErrorMessage("Nothing found to decouple!");
				setPageComplete(false);
				return;
			}

			setErrorMessage(null);
			setPageComplete(true);
		}
	}
}
