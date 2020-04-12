package pl.luccasso.mailownik.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import pl.luccasso.mailownik.config.ConfigF;

public class ConfigWindowListeners {

	static ConfigWindow window = ConfigWindow.getInstance();

	static String lastPath = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();

	public abstract static class AbstractConfigListener {

		File chooseFileOrNull() {

			JFileChooser jfc = new JFileChooser(lastPath);
			int returnValue = jfc.showOpenDialog(null);

			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = jfc.getSelectedFile();
				lastPath = selectedFile.getParent();
				return selectedFile;
			}
			return null;

		}
	}

	public static class GoogleListener extends AbstractConfigListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			File selectedFile = chooseFileOrNull();
			if (selectedFile != null) {
				ConfigF.setPupPath(selectedFile.getAbsolutePath());
				window.updateLabelsFromConfig();
			}
		}
	}

	public static class BankListener extends AbstractConfigListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			File selectedFile = chooseFileOrNull();
			if (selectedFile != null) {
				ConfigF.setBankPath(selectedFile.getAbsolutePath());
				window.updateLabelsFromConfig();
			}
		}
	}

	public static class PreviousListener extends AbstractConfigListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			File selectedFile = chooseFileOrNull();
			if (selectedFile != null) {
				ConfigF.setSavedPath(selectedFile.getAbsolutePath());
				window.updateLabelsFromConfig();
			}
		}
	}

	public static class PayPerListener extends AbstractConfigListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			File selectedFile = chooseFileOrNull();
			if (selectedFile != null) {
				ConfigF.setPayPerClass(selectedFile.getAbsolutePath());
				window.updateLabelsFromConfig();
			}
		}
	}

	public static class ClassPerListener extends AbstractConfigListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			File selectedFile = chooseFileOrNull();
			if (selectedFile != null) {
				ConfigF.setClassPerSchool(selectedFile.getAbsolutePath());
				window.updateLabelsFromConfig();
			}
		}
	}

	public static class ConfigListener extends AbstractConfigListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			File selectedFile = chooseFileOrNull();
			if (selectedFile != null) {
				ConfigF.setConfigPath(selectedFile.getAbsolutePath());
				window.updateLabelsFromConfig();
			}
		}
	}
}
