package rgt.views.panels;

import java.awt.BorderLayout;
import java.awt.TextArea;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import rgt.constants.RGTConstants;
import rgt.controllers.GenerateController;

public class OutputPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private TextArea outputArea;
	public OutputPanel(String title, String user) {
		super();
		initializeOutputPanel(title, user);
	}

	private void initializeOutputPanel(String title, String user) {
		setBorder(BorderFactory.createTitledBorder(RGTConstants.OUTPUT_PANEL));
		
		// Set Layout
		setLayout(new BorderLayout());
		
		// Create components
		outputArea = new TextArea();
		outputArea.setEditable(false);
		outputArea.setText(new GenerateController().getOutputContent(title, user).toString());
		// Add components to the container
		add(outputArea, BorderLayout.CENTER);
	}

	public TextArea getOutputArea() {
		return outputArea;
	}
	
}
