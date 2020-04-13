package pl.luccasso.mailownik.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
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
	
	public static class SaveListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser(lastPath);
			int returnValue = jfc.showSaveDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				try {
                File file = jfc.getSelectedFile();
                ConfigF.saveToJsonFile(file.getPath());
                lastPath = file.getPath();
				}
				catch (IOException exception) {
					JOptionPane.showMessageDialog(window,
						    "Nie udalo sie zapisać konfiguracji :( \nPopraw się!",
						    "Inane error",
						    JOptionPane.ERROR_MESSAGE);
				}				
			}			
		}
	}
	
	public static class loadConfigFromFileListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				ConfigF.readFromJsonFile(ConfigF.getConfigPath());
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(window,
					    "Nie udalo sie wczytać konfiguracji :( \nPopraw się!",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
			} finally {
			window.updateLabelsFromConfig();
			}
		}
		
	}
	
}
