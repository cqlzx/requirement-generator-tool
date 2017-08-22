package rgt.views.panels;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.commons.collections4.CollectionUtils;

import rgt.application.RGTApplication;
import rgt.constants.RGTButtons;
import rgt.constants.RGTConstants;
import rgt.constants.RGTEntities;
import rgt.controllers.EditController;
import rgt.data.ActionData;
import rgt.data.BusinessProcessData;
import rgt.data.StepData;
import rgt.views.RToolView;
import rgt.views.dialog.AddDialog;

public class AddPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton cancelButton;
	String currentString, output;

	public AddPanel(String entity, String verbNoun) {
		super();
		setLayout(new GridBagLayout());
		repaint();
		if (RGTEntities.BUSINESS_PROCESS.getEntity().equals(entity)) {
			initializeBusinessProcessTab(verbNoun);
		} else if (RGTEntities.STEP.getEntity().equals(entity)) {
			if (CollectionUtils.isEmpty(RGTApplication.getApplicationInstance().getBusinessProcesses())) {
				initializeMessageTab(RGTEntities.STEP.getEntity());
			} else {
				initializeStepTab(verbNoun);
			}
		} else if (RGTEntities.ACTION.getEntity().equals(entity)) {
			if (CollectionUtils.isEmpty(RGTApplication.getApplicationInstance().getSteps())) {
				initializeMessageTab(RGTEntities.ACTION.getEntity());
			} else {
				initializeActionTab(verbNoun);
			}
		}
	}

	public String showoutput(String itmoutput, String currentstring) {
		itmoutput = itmoutput + "\n" + currentstring;
		return itmoutput;

	}

//	private void outPanel(ActionEvent e, String s) {
//		Container container = (Container) e.getSource();
//		JDialog addDialogBox = (JDialog) SwingUtilities.getWindowAncestor(container);
//		RToolView rToolView = (RToolView) addDialogBox.getParent();
//		output = rToolView.getjTextArea2().getText();
//		output = showoutput(output, s);
//		rToolView.getjTextArea2().setText(output);
//	}
	
	//Display output into tree structure
	private void outPanelNew(ActionEvent e){
		Container container = (Container) e.getSource();
		JDialog addDialogBox = (JDialog) SwingUtilities.getWindowAncestor(container);
		RToolView rToolView = (RToolView) addDialogBox.getParent();
		
		JTree tree = rToolView.getjTree();
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		DefaultMutableTreeNode top = buildTree();
		model.setRoot(top);
		
		for (int i = 0; i < tree.getRowCount(); i++) {
		    tree.expandRow(i);
		}
	}

	private void initializeActionTab(String verbNoun) {

		setBorder(BorderFactory.createTitledBorder(RGTConstants.ADD_ACTION_DIALOG));

		GridBagConstraints constraints = new GridBagConstraints();

		JLabel verbNounLabel = new JLabel(RGTConstants.LABEL_VERB_NOUN);
		JTextField verbNounText = new JTextField(RGTConstants.VERB_NOUN_INPUT_SIZE);
		verbNounText.setText(verbNoun);
		JLabel actionSequenceLabel = new JLabel(RGTConstants.LABEL_ACTION_SEQUENCE);
		JTextField actionSeqText = new JTextField(RGTConstants.ACTION_SEQUENCE_NUM_INPUT_SIZE);
		JLabel stepsComboLabel = new JLabel(RGTConstants.LABEL_COMBO_SELECT_STEPS);
		JComboBox<StepData> stepComboBox = new JComboBox<StepData>();

		EditController editCtrl = new EditController();
		List<StepData> steps = editCtrl.getAllSteps();

		for (StepData item : steps) {
			// To display the verb-noun phrase in the combobox override the
			// toString method in StepData
			stepComboBox.addItem(item);
		}

		JButton addButton = new JButton(RGTButtons.ADD.getButton());
		cancelButton = new JButton(RGTButtons.CANCEL.getButton());

		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.insets = new Insets(2, 2, 2, 2);

		// First Column
		constraints.anchor = GridBagConstraints.LINE_END;

		constraints.gridx = 0;
		constraints.gridy = 0;
		add(verbNounLabel, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		add(actionSequenceLabel, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;
		add(stepsComboLabel, constraints);

		constraints.gridx = 0;
		constraints.gridy = 3;
		add(addButton, constraints);

		// Second Column
		constraints.anchor = GridBagConstraints.LINE_START;

		constraints.gridx = 1;
		constraints.gridy = 0;
		add(verbNounText, constraints);

		constraints.gridx = 1;
		constraints.gridy = 1;
		add(actionSeqText, constraints);

		constraints.gridx = 1;
		constraints.gridy = 2;
		add(stepComboBox, constraints);

		constraints.gridx = 1;
		constraints.gridy = 3;
		add(cancelButton, constraints);

		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					if (null == verbNounText.getText() || verbNounText.getText().equals("")) {
						JOptionPane.showMessageDialog(null, RGTConstants.VALIDATION_MESSAGE_EMPTY_VERB_NOUN,
								RGTConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE, null);
					} else {
						addAction();
//						currentString = "Action: " + verbNounText.getText() + "(Sequence No.: "
//								+ actionSeqText.getText() + " ,Step: " + stepComboBox.getSelectedItem() + ")";
//						outPanel(e, currentString);
						outPanelNew(e);
						if (verbNoun.equals(RGTConstants.EMPTY)) {
							showSuccessDialog(e);
						} else {
							Container container = (Container) e.getSource();
							JDialog addDialogBox = (JDialog) SwingUtilities.getWindowAncestor(container);
							addDialogBox.dispose();
						}
					}

				} catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(null, RGTConstants.VALIDATION_MESSAGE_SEQ_NUMBER,
							RGTConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE, null);
				}
			}

			private void showSuccessDialog(ActionEvent e) {
				Container container = (Container) e.getSource();
				JDialog addDialogBox = (JDialog) SwingUtilities.getWindowAncestor(container);
				int dialogResult = JOptionPane.showConfirmDialog(addDialogBox, RGTConstants.MESSAGE_ACTION_ADD,
						RGTConstants.MESSAGE_ADD_TITLE, JOptionPane.YES_NO_OPTION);
				addDialogBox.dispose();
				if (dialogResult == JOptionPane.YES_OPTION) {
					refreshAddDialog(addDialogBox, RGTConstants.ACTION_TAB_INDEX);
				}
			}

			public void addAction() throws NumberFormatException {
				ActionData action = new ActionData();
				action.setActionId(UUID.randomUUID().toString());
				action.setActionSeqNumber(Integer.parseInt(actionSeqText.getText()));
				StepData stepData = (StepData) stepComboBox.getSelectedItem();
				action.setStepId(stepData.getStepId());
				action.setVerbNounPhrase(verbNounText.getText());

				EditController editController = new EditController();
				editController.addAction(action);
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Container container = (Container) e.getSource();
				JDialog parentDialog = (JDialog) SwingUtilities.getWindowAncestor(container);
				parentDialog.dispose();
			}
		});
	}

	private void initializeStepTab(String verbNoun) {
		setBorder(BorderFactory.createTitledBorder(RGTConstants.ADD_STEP_DIALOG));

		GridBagConstraints constraints = new GridBagConstraints();

		JLabel verbNounLabel = new JLabel(RGTConstants.LABEL_VERB_NOUN);
		JTextField verbNounText = new JTextField(RGTConstants.VERB_NOUN_INPUT_SIZE);
		verbNounText.setText(verbNoun);
		JLabel stepSequenceLabel = new JLabel(RGTConstants.LABEL_STEP_SEQUENCE);
		JTextField stepSeqText = new JTextField(RGTConstants.STEP_SEQUENCE_NUM_INPUT_SIZE);
		JLabel businessProcessComboLabel = new JLabel(RGTConstants.LABEL_COMBO_SELECT_BP);
		JComboBox<BusinessProcessData> businessProcessesComboBox = new JComboBox<BusinessProcessData>();

		EditController editCtrl = new EditController();
		List<BusinessProcessData> businessProcesses = editCtrl.getAllBusinessProcesses();

		for (BusinessProcessData item : businessProcesses) {
			// To display the verb-noun phrase in the combobox override the
			// toString method in BusinessProcessData
			businessProcessesComboBox.addItem(item);
		}

		JButton addButton = new JButton(RGTButtons.ADD.getButton());
		cancelButton = new JButton(RGTButtons.CANCEL.getButton());

		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.insets = new Insets(2, 2, 2, 2);

		// First Column
		constraints.anchor = GridBagConstraints.LINE_END;

		constraints.gridx = 0;
		constraints.gridy = 0;
		add(verbNounLabel, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		add(stepSequenceLabel, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;
		add(businessProcessComboLabel, constraints);

		constraints.gridx = 0;
		constraints.gridy = 3;
		add(addButton, constraints);

		// Second Column
		constraints.anchor = GridBagConstraints.LINE_START;

		constraints.gridx = 1;
		constraints.gridy = 0;
		add(verbNounText, constraints);

		constraints.gridx = 1;
		constraints.gridy = 1;
		add(stepSeqText, constraints);

		constraints.gridx = 1;
		constraints.gridy = 2;
		add(businessProcessesComboBox, constraints);

		constraints.gridx = 1;
		constraints.gridy = 3;
		add(cancelButton, constraints);

		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (null == verbNounText.getText() || verbNounText.getText().equals("")) {
						JOptionPane.showMessageDialog(null, RGTConstants.VALIDATION_MESSAGE_EMPTY_VERB_NOUN,
								RGTConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE, null);
					} else {
						addStep();
//						currentString = "Step: " + verbNounText.getText() + "(Sequence No.: " + stepSeqText.getText()
//								+ " ,Business Process: " + businessProcessesComboBox.getSelectedItem() + ")";
//						outPanel(e, currentString);
						outPanelNew(e);
						if (verbNoun.equals(RGTConstants.EMPTY)) {
							showSuccessDialog(e);
						} else {
							Container container = (Container) e.getSource();
							JDialog addDialogBox = (JDialog) SwingUtilities.getWindowAncestor(container);
							addDialogBox.dispose();
						}
					}
				} catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(null, RGTConstants.VALIDATION_MESSAGE_SEQ_NUMBER,
							RGTConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE, null);
				}
			}

			private void showSuccessDialog(ActionEvent e) {
				Container container = (Container) e.getSource();
				JDialog addDialogBox = (JDialog) SwingUtilities.getWindowAncestor(container);
				int dialogResult = JOptionPane.showConfirmDialog(addDialogBox, RGTConstants.MESSAGE_STEP_ADD,
						RGTConstants.MESSAGE_ADD_TITLE, JOptionPane.YES_NO_OPTION);
				addDialogBox.dispose();
				if (dialogResult == JOptionPane.YES_OPTION) {
					refreshAddDialog(addDialogBox, RGTConstants.STEP_TAB_INDEX);
				}
			}

			private void addStep() throws NumberFormatException {
				StepData step = new StepData();
				BusinessProcessData businessProcess = (BusinessProcessData) businessProcessesComboBox.getSelectedItem();
				step.setBusinessProcessId(businessProcess.getBusinessProcessId());
				step.setStepId(UUID.randomUUID().toString());
				step.setStepSeqNumber(Integer.parseInt(stepSeqText.getText()));
				step.setVerbNounPhrase(verbNounText.getText());

				EditController editController = new EditController();
				editController.addStep(step);
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Container container = (Container) e.getSource();
				JDialog parentDialog = (JDialog) SwingUtilities.getWindowAncestor(container);
				parentDialog.dispose();
			}
		});
	}

	private void initializeBusinessProcessTab(String verbNoun) {
		setBorder(BorderFactory.createTitledBorder(RGTConstants.ADD_BUSINESS_PROCESS_DIALOG));

		GridBagConstraints constraints = new GridBagConstraints();

		JLabel verbNounLabel = new JLabel(RGTConstants.LABEL_VERB_NOUN);
		JTextField verbNounText = new JTextField(RGTConstants.VERB_NOUN_INPUT_SIZE);
		verbNounText.setText(verbNoun);
		JLabel priorityLabel = new JLabel(RGTConstants.LABEL_BUSINESS_PROCESS_PRIORITY);
		JComboBox<String> priorityCombo = new JComboBox<String>(RGTConstants.PRIORITY_VALUES);
		JButton addButton = new JButton(RGTButtons.ADD.getButton());
		cancelButton = new JButton(RGTButtons.CANCEL.getButton());

		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.insets = new Insets(2, 2, 2, 2);

		// First Column
		constraints.anchor = GridBagConstraints.LINE_END;

		constraints.gridx = 0;
		constraints.gridy = 0;
		add(verbNounLabel, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		add(priorityLabel, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;
		add(addButton, constraints);

		// Second Column
		constraints.anchor = GridBagConstraints.LINE_START;

		constraints.gridx = 1;
		constraints.gridy = 0;
		add(verbNounText, constraints);

		constraints.gridx = 1;
		constraints.gridy = 1;
		add(priorityCombo, constraints);

		constraints.gridx = 1;
		constraints.gridy = 2;
		add(cancelButton, constraints);

		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (null == verbNounText.getText() || verbNounText.getText().equals("")) {
					JOptionPane.showMessageDialog(null, RGTConstants.VALIDATION_MESSAGE_EMPTY_VERB_NOUN,
							RGTConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE, null);
				} else {
					addBusinessProcess();
//					currentString = "Business Process: " + verbNounText.getText() + "(Priority: "
//							+ priorityCombo.getSelectedItem() + ")";
//					outPanel(e, currentString);
					outPanelNew(e);
					if (verbNoun.equals(RGTConstants.EMPTY)) {
						showSuccessDialog(e);
					} else {
						Container container = (Container) e.getSource();
						JDialog addDialogBox = (JDialog) SwingUtilities.getWindowAncestor(container);
						addDialogBox.dispose();
					}

				}
			}

			private void showSuccessDialog(ActionEvent e) {
				Container container = (Container) e.getSource();
				JDialog addDialogBox = (JDialog) SwingUtilities.getWindowAncestor(container);

				int dialogResult = JOptionPane.showConfirmDialog(addDialogBox,
						RGTConstants.MESSAGE_BUSINESS_PROCESS_ADD, RGTConstants.MESSAGE_ADD_TITLE,
						JOptionPane.YES_NO_OPTION);
				addDialogBox.dispose();
				if (dialogResult == JOptionPane.YES_OPTION) {
					refreshAddDialog(addDialogBox, RGTConstants.BUSINESS_PROCESS_TAB_INDEX);
				}
			}

			private void addBusinessProcess() {
				BusinessProcessData businessProcess = new BusinessProcessData();
				businessProcess.setBusinessProcessId(UUID.randomUUID().toString());
				businessProcess.setVerbNounPhrase(verbNounText.getText());
				businessProcess.setPriority(Integer.parseInt(priorityCombo.getSelectedItem().toString()));

				EditController editController = new EditController();
				editController.addBusinessProcess(businessProcess);
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Container container = (Container) e.getSource();
				JDialog parentDialog = (JDialog) SwingUtilities.getWindowAncestor(container);
				parentDialog.dispose();
			}
		});
	}

	public void refreshAddDialog(JDialog addDialogBox, int selectedTabIndex) {
		AddDialog addDialog = new AddDialog((RToolView) addDialogBox.getParent(), RGTConstants.EMPTY);
		addDialog.getTabbedPane().setSelectedIndex(selectedTabIndex);
		addDialog.setTitle(RGTConstants.ADD_DIALOG_BOX_TITLE);
		addDialog.setSize(RGTConstants.DIALOG_WIDTH, RGTConstants.DIALOG_HEIGHT);
		addDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		addDialog.setLocation(dimension.width / 2 - addDialog.getSize().width / 2,
				dimension.height / 2 - addDialog.getSize().height / 2);
		addDialog.setVisible(true);
	}

	public void initializeMessageTab(String entities) {
		JLabel errorMessage = null;
		if (entities.equals(RGTEntities.STEP.getEntity())) {
			setBorder(BorderFactory.createTitledBorder(RGTConstants.ADD_STEP_DIALOG));
			errorMessage = new JLabel(RGTConstants.ERROR_NO_BUSINESSPROCESS);
		} else if (entities.equals(RGTEntities.ACTION.getEntity())) {
			setBorder(BorderFactory.createTitledBorder(RGTConstants.ADD_ACTION_DIALOG));
			errorMessage = new JLabel(RGTConstants.ERROR_NO_STEP);
		}

		add(errorMessage);
	}
	
	private DefaultMutableTreeNode buildTree(){
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("");
		EditController editController = new EditController();
		
		List<BusinessProcessData> bps = editController.getAllBusinessProcesses();
		Collections.sort(bps, new BusinessProcessData());
		for(BusinessProcessData bp : bps){
			DefaultMutableTreeNode bpNode = new DefaultMutableTreeNode("BusinessProcess : " + bp.getVerbNounPhrase() + " (Priority : " + bp.getPriority() + ")"); 
			top.add(bpNode);
			List<StepData> steps = editController.getAllSteps(bp.getBusinessProcessId());
			Collections.sort(steps, new StepData());
			for(StepData step : steps){
				DefaultMutableTreeNode stepNode = new DefaultMutableTreeNode("Step : " + step.getVerbNounPhrase() + " (Sequence : " + step.getStepSeqNumber() + ")");
				bpNode.add(stepNode);
				List<ActionData> actions = editController.getAllActions(step.getStepId());
				Collections.sort(actions, new ActionData());
				for(ActionData action : actions){
					DefaultMutableTreeNode actionNode = new DefaultMutableTreeNode("Action : " + action.getVerbNounPhrase() + " (Sequence : " + action.getActionSeqNumber() + ")");
					stepNode.add(actionNode);
				}
			}
		}
		
		return top;
	}
}
