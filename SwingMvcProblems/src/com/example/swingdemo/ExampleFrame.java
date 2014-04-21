package com.example.swingdemo;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonModel;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class ExampleFrame extends JFrame {

	private static final long serialVersionUID = -778615248202221558L;
	
	private boolean initialized = false;
	private Action checkboxAction = new AbstractAction("Check it") {
		@Override public void actionPerformed(ActionEvent e) {
			System.out.println("--> checking");
		}
	};
	private ButtonModel checkboxModel;
	
	public ExampleFrame() {
		// NoP. Call init() to initialize the GUI
	}
	
	public void init() {
		if (initialized) {
			throw new IllegalStateException("The frame is already initialized");
		}
		setTitle("A frame to contain the app");
		setSize(640, 480);
		JDesktopPane desktop = new JDesktopPane();
		setContentPane(desktop);
		setJMenuBar(getAndInitMenuBar());
		
		JInternalFrame internal = new CheckerInternalFrame(checkboxModel, checkboxAction, "Check it");
		internal.setLocation(5, 5);
		desktop.add(internal);
		initialized = true;
	}
	
	public Action getCheckBoxAction() {
		return checkboxAction;
	}
	
	public ButtonModel getCheckBoxModel() {
		return checkboxModel;
	}
	
	private JMenuBar getAndInitMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");
		JCheckBoxMenuItem checkIt = new JCheckBoxMenuItem(checkboxAction);
		checkboxModel = checkIt.getModel();
		file.add(checkIt);
		menuBar.add(file);
		return menuBar;
	}
	
	private static class CheckerInternalFrame extends JInternalFrame {
		
		private static final long serialVersionUID = 7764296274182376110L;

		public CheckerInternalFrame(ButtonModel model, Action action, String checkItText) {
			super("Internal", true, true, true, true);
			setSize(320, 320);
			setVisible(true);
			
			JCheckBoxMenuItem checkIt = new JCheckBoxMenuItem(checkItText);
			checkIt.setAction(action);
			checkIt.setModel(model);
			
			JMenuBar menuBar = new JMenuBar();
			JMenu menu = new JMenu("File");
			menu.add(checkIt);
			menuBar.add(menu);
			
			setJMenuBar(menuBar);
		}
	}
	
	
	
}
