package com.example.swingdemo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonModel;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JToggleButton.ToggleButtonModel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class ExampleFrame extends JFrame {

	private static final String CHECK_IT = "Check it";
	private static final long serialVersionUID = -778615248202221558L;
	
	private boolean initialized = false;
	private JMenu actionCounterMenu = new JMenu();
	private int actionCounter = 0;
	private JMenu listenerCounterMenu = new JMenu();
	private int listenerCounter = 0;
	private String title;
	
	private Action checkboxAction = new AbstractAction(CHECK_IT) {
		
		private static final long serialVersionUID = 737633186243567717L;

		@Override public void actionPerformed(ActionEvent e) {
			actionCounter++;
			updateActionCounterMenuText();
		}
	};
	private ButtonModel checkboxModel;
	private JCheckBoxMenuItem checkBoxMenuItem;
	
	public ExampleFrame() {
		// NoP. Call init() to initialize the GUI
	}
	
	public void init() {
		if (initialized) {
			throw new IllegalStateException("The frame is already initialized");
		}
		title = "Container frame";
		setSize(800, 600);
		JDesktopPane desktop = new JDesktopPane();
		setContentPane(desktop);
		setJMenuBar(getAndInitMenuBar());
		
		JInternalFrame doubleChecker = new DoubleCheckerInternalFrame(checkboxModel, checkboxAction, CHECK_IT);
		desktop.add(doubleChecker);
		doubleChecker.setLocation(0, 0);
		
		JInternalFrame singleChecker = new SingleCheckerInternalFrame(checkboxModel, CHECK_IT);
		desktop.add(singleChecker);
		singleChecker.setLocation(320, 0);
		
		JInternalFrame differentModelChecker = new DifferentModelInternalFrame(checkboxAction);
		desktop.add(differentModelChecker);
		differentModelChecker.setLocation(0, 240);
		
		JInternalFrame sameModelChecker = new SameModelInternalFrame(checkboxModel);
		desktop.add(sameModelChecker);
		sameModelChecker.setLocation(320, 240);
		
		updateTitle();
		
		initialized = true;
	}
	
	private void updateTitle() {
		ButtonModel model = checkBoxMenuItem.getModel();
		String actualTitle = title + " - " + (model.isSelected() ? "Checked" : "Unchecked");
		setTitle(actualTitle);
	}
	
	private JMenuBar getAndInitMenuBar() {
		updateActionCounterMenuText();
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		checkBoxMenuItem = new JCheckBoxMenuItem(checkboxAction);
		
		checkBoxMenuItem.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				listenerCounter++;
				updateListenterCounterMenuText();
				updateTitle();
			}
		});
		
		checkboxModel = checkBoxMenuItem.getModel();
		
		fileMenu.add(checkBoxMenuItem);
		menuBar.add(fileMenu);
		
		JMenu buttonActions = new JMenu("Button actions");
		final JCheckBoxMenuItem actionEnabled = new JCheckBoxMenuItem("Action enabled");
		actionEnabled.setSelected(true);
		actionEnabled.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				checkboxAction.setEnabled(actionEnabled.isSelected());
			}
		});
		buttonActions.add(actionEnabled);
		
		final JCheckBoxMenuItem modelEnabled = new JCheckBoxMenuItem("Model enabled");
		modelEnabled.setSelected(true);
		modelEnabled.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				checkboxModel.setEnabled(modelEnabled.isSelected());
			}
		});
		buttonActions.add(modelEnabled);
		
		menuBar.add(buttonActions);
		
		menuBar.add(actionCounterMenu);
		menuBar.add(listenerCounterMenu);
		updateListenterCounterMenuText();
		
		return menuBar;
	}
	
	private void updateActionCounterMenuText() {
		actionCounterMenu.setText("Action Checking counter: " + actionCounter);
	}
	
	private void updateListenterCounterMenuText() {
		listenerCounterMenu.setText("Listener Checking counter: " + listenerCounter);
	}
	
	private static abstract class CheckerInternalFrame extends JInternalFrame {
		
		private static final long serialVersionUID = 4030440002012563184L;
		
		private JCheckBoxMenuItem checker;
		private String title;
		
		private boolean initialized;
		
		public CheckerInternalFrame(String title) {
			super(title, true, true, true, true);
			this.title = title;
			setSize(320, 240);
			setVisible(true);
		}
		
		protected final void init(JCheckBoxMenuItem checker) {
			if (initialized) {
				throw new IllegalStateException("Internal frame already initialized");
			}
			this.checker = checker;
			checker.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					updateTitle();
				}
			});
			initMenuBar();
			updateTitle();
			
			addInternalFrameListener(new InternalFrameAdapter() {
				public void internalFrameClosed(InternalFrameEvent e) {
					JCheckBoxMenuItem checker = CheckerInternalFrame.this.checker;
					if (checker != null) {
						checker.setAction(null);
						checker.setModel(null);
					}
				}
			});
			
			initialized = true;
		}
		
		private void initMenuBar() {
			JMenuBar menuBar = new JMenuBar();
			JMenu menu = new JMenu("File");
			menu.add(checker);
			menuBar.add(menu);
			setJMenuBar(menuBar);
		}
		
		private void updateTitle() {
			ToggleButtonModel model = (ToggleButtonModel) checker.getModel();
			String actualTitle = title + " - " + (model.isSelected() ? "Checked" : "Unchecked"); 
			setTitle(actualTitle);
		}
	}
	
	private static class DoubleCheckerInternalFrame extends CheckerInternalFrame {
		
		private static final long serialVersionUID = 7764296274182376110L;

		public DoubleCheckerInternalFrame(ButtonModel model, Action action, String checkItText) {
			super("Double checker");
			
			JCheckBoxMenuItem checkIt = new JCheckBoxMenuItem(checkItText);
			checkIt.setAction(action);
			checkIt.setModel(model);
			
			init(checkIt);
		}
	}
	
	private static class SingleCheckerInternalFrame extends CheckerInternalFrame {
		
		private static final long serialVersionUID = 8380545232445039770L;

		public SingleCheckerInternalFrame(ButtonModel model, String checkItText) {
			super("Single checker");
			
			JCheckBoxMenuItem checkIt = new JCheckBoxMenuItem(checkItText);
			checkIt.setModel(model);
			
			init(checkIt);
		}
		
	}
	
	private static class DifferentModelInternalFrame extends CheckerInternalFrame {
		
		private static final long serialVersionUID = -5768769546908502695L;

		public DifferentModelInternalFrame(Action action) {
			super("Same action");
			
			JCheckBoxMenuItem checkIt = new JCheckBoxMenuItem(action);
			init(checkIt);
		}
		
	}
	
	private static class SameModelInternalFrame extends CheckerInternalFrame {
		
		private static final long serialVersionUID = -7958841262971958339L;

		public SameModelInternalFrame(ButtonModel model) {
			super("Same model");
			
			JCheckBoxMenuItem checkIt = new JCheckBoxMenuItem();
			checkIt.setModel(model);
			init(checkIt);
		}
	}
	
}
