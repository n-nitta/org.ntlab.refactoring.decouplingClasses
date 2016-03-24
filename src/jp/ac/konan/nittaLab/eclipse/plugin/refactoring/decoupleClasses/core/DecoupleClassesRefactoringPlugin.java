package jp.ac.konan.nittaLab.eclipse.plugin.refactoring.decoupleClasses.core;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class DecoupleClassesRefactoringPlugin extends AbstractUIPlugin {
	public static final String IMG_DESCRIPTION = "Refactoring.Description.Image";
	public static final String REFACTORING_NAME = "Decouple Classes";
	private static DecoupleClassesRefactoringPlugin plugin;

	public DecoupleClassesRefactoringPlugin() {
		plugin = this;
	}
	
	@Override
	protected void initializeImageRegistry(ImageRegistry reg) {
		registerImage(reg, IMG_DESCRIPTION, "infoHide.png");
	}
	
	private void registerImage(ImageRegistry reg, String key, String fileName) {
		URL url = FileLocator.find(getBundle(), new Path("icons/" + fileName), null);
		if (url == null) return;
		reg.put(key, ImageDescriptor.createFromURL(url));
	}

	public static DecoupleClassesRefactoringPlugin getInstance() {
		return plugin;
	}
	
}
