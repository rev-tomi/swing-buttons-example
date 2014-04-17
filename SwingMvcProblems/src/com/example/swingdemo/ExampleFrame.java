package com.example.swingdemo;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonModel;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
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
		JDesktopPane desktop = new JDesktopPane();
		setContentPane(desktop);
		setSize(640, 480);
		setJMenuBar(getAndInitMenuBar());
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
	
}
