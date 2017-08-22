package rgt.views;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import rgt.constants.RGTButtons;
import rgt.controllers.ExportController;
import rgt.views.panels.OutputPanel;

/* This view will be generated asynchronously on the click of generate button */
public class OutputView extends JFrame{
	private static final long serialVersionUID = 1L;
	private JFileChooser fileChooser;
	private OutputPanel outputPanel;
	public OutputView(String title, String user) {
		super();
		initializeOutputView(title, user);
	}

	private void initializeOutputView(String title, String user) {
		// Set Layout
		setLayout(new BorderLayout()); 

		// Create Components (in this case a panel) 
		outputPanel = new OutputPanel(title, user);
		JButton exportButton = new JButton(RGTButtons.EXPORT.getButton());

		// Add Components to the container
		Container container = getContentPane();
		container.add(outputPanel, BorderLayout.CENTER);
		container.add(exportButton, BorderLayout.SOUTH);

		fileChooser = new JFileChooser();
		exportButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVal = fileChooser.showSaveDialog(OutputView.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					ExportController ctrl = new ExportController();
					String content = null != outputPanel.getOutputArea() ? outputPanel.getOutputArea().getText() : "";
					ctrl.exportRequirements(fileChooser.getSelectedFile(), content);
				}
			}
		});
	}
}
