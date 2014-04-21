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

	private static final String CHECK_IT = "Check it";

	private static final long serialVersionUID = -778615248202221558L;
	
	private boolean initialized = false;
	
	private JMenu counterMenu = new JMenu();
	private int counter = 0;
	
	private Action checkboxAction = new AbstractAction(CHECK_IT) {
		@Override public void actionPerformed(ActionEvent e) {
			counter++;
			updateCounterMenuText();
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
		
		JInternalFrame doubleChecker = new DoubleCheckerInternalFrame(checkboxModel, checkboxAction, CHECK_IT);
		desktop.add(doubleChecker);
		doubleChecker.setLocation(5, 5);
		
		JInternalFrame singleChecker = new SingleCheckerInternalFrame(checkboxModel, CHECK_IT);
		desktop.add(singleChecker);
		singleChecker.setLocation(50, 50);
		
		JInternalFrame differentModelChecker = new DifferentModelInternalFrame(checkboxAction);
		desktop.add(differentModelChecker);
		differentModelChecker.setLocation(100, 100);
		
		initialized = true;
	}
	
	public Action getCheckBoxAction() {
		return checkboxAction;
	}
	
	public ButtonModel getCheckBoxModel() {
		return checkboxModel;
	}
	
	private JMenuBar getAndInitMenuBar() {
		updateCounterMenuText();
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");
		JCheckBoxMenuItem checkIt = new JCheckBoxMenuItem(checkboxAction);
		checkboxModel = checkIt.getModel();
		file.add(checkIt);
		menuBar.add(file);
		menuBar.add(counterMenu);
		return menuBar;
	}
	
	private void updateCounterMenuText() {
		counterMenu.setText("Checking counter: " + counter);
	}
	
	private static class DoubleCheckerInternalFrame extends JInternalFrame {
		
		private static final long serialVersionUID = 7764296274182376110L;

		public DoubleCheckerInternalFrame(ButtonModel model, Action action, String checkItText) {
			super("Double checker", true, true, true, true);
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
	
	private static class SingleCheckerInternalFrame extends JInternalFrame {
		
		public SingleCheckerInternalFrame(ButtonModel model, String checkItText) {
			super("Single checker", true, true, true, true);
			setSize(320, 320);
			setVisible(true);
			
			JCheckBoxMenuItem checkIt = new JCheckBoxMenuItem(checkItText);
			checkIt.setModel(model);
			
			JMenuBar menuBar = new JMenuBar();
			JMenu menu = new JMenu("File");
			menu.add(checkIt);
			menuBar.add(menu);
			
			setJMenuBar(menuBar);
		}
		
	}
	
	private static class DifferentModelInternalFrame extends JInternalFrame {
		
		public DifferentModelInternalFrame(Action action) {
			super("Same action, different model", true, true, true, true);
			setSize(320, 320);
			setVisible(true);
			
			JCheckBoxMenuItem checkIt = new JCheckBoxMenuItem(action);
			
			JMenuBar menuBar = new JMenuBar();
			JMenu menu = new JMenu("File");
			menu.add(checkIt);
			menuBar.add(menu);
			
			setJMenuBar(menuBar);
		}
		
	}
	
}
