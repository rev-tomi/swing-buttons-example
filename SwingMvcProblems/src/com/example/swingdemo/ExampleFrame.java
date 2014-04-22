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
import javax.swing.JMenuItem;

import com.example.swingdemo.InternalFrameFactory.DifferentModelInternalFrame;
import com.example.swingdemo.InternalFrameFactory.DoubleCheckerInternalFrame;
import com.example.swingdemo.InternalFrameFactory.SameModelInternalFrame;
import com.example.swingdemo.InternalFrameFactory.SingleCheckerInternalFrame;

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
	
	private JDesktopPane desktop;
	
	private JInternalFrame doubleChecker;
	private JInternalFrame singleChecker;
	private JInternalFrame differentModelChecker;
	private JInternalFrame sameModelChecker;
	
	public ExampleFrame() {
		// NoP. Call init() to initialize the GUI
	}
	
	public void init() {
		if (initialized) {
			throw new IllegalStateException("The frame is already initialized");
		}
		title = "Container frame";
		setSize(800, 600);
		desktop = new JDesktopPane();
		setContentPane(desktop);
		setJMenuBar(getAndInitMenuBar());
		
		updateTitle();
		
		initialized = true;
	}
	
	private void showSameModelChecker() {
		if (sameModelChecker == null) {
			sameModelChecker = new SameModelInternalFrame(checkboxModel);
			desktop.add(sameModelChecker);
			sameModelChecker.setLocation(0, 0);
		}
		sameModelChecker.setVisible(true);
	}
	
	private void showDifferentModelChecker() {
		if (differentModelChecker == null) {
			differentModelChecker = new DifferentModelInternalFrame(checkboxAction);
			desktop.add(differentModelChecker);
			differentModelChecker.setLocation(320, 0);
		}
		differentModelChecker.setVisible(true);
	}
	
	private void showDoubleChecker() {
		if (doubleChecker == null) {
			doubleChecker = new DoubleCheckerInternalFrame(checkboxModel, checkboxAction, CHECK_IT);
			desktop.add(doubleChecker);
			differentModelChecker.setLocation(0, 240);
		}
		doubleChecker.setVisible(true);
	}
	
	private void showSingleChecker() {
		if (singleChecker == null) {
			singleChecker = new SingleCheckerInternalFrame(checkboxModel, CHECK_IT);
			desktop.add(singleChecker);
			sameModelChecker.setLocation(320, 240);
		}
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
		
		JMenu checkerOpeners = new JMenu("Internal Frames");
		checkerOpeners.add(new JMenuItem(new AbstractAction("Same button model") {
			@Override public void actionPerformed(ActionEvent e) { showSameModelChecker(); }
		}));
		checkerOpeners.add(new JMenuItem(new AbstractAction("Different button model") {
			@Override public void actionPerformed(ActionEvent e) { showDifferentModelChecker(); }
		}));
		checkerOpeners.add(new JMenuItem(new AbstractAction("Same action and model") {
			@Override public void actionPerformed(ActionEvent e) { showDoubleChecker(); }
		}));
		checkerOpeners.add(new JMenuItem(new AbstractAction("Single checker") {
			@Override public void actionPerformed(ActionEvent e) { showSingleChecker(); }
		}));
		
		menuBar.add(checkerOpeners);
		
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
	
}
