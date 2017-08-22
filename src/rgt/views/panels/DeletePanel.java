package rgt.views.panels;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
import rgt.views.dialog.DeleteDialog;

public class DeletePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton cancelButton;
	private JLabel stepComboLabel;
	private JComboBox<StepData> stepComboBox;
	private JLabel actionComboLabel;
	private JComboBox<ActionData> actionComboBox;
	private JComboBox<BusinessProcessData> businessProcessComboBox;

	public DeletePanel(String entity) {
		super();
		setLayout(new GridBagLayout());
		repaint();
		cancelButton = new JButton(RGTButtons.CANCEL.getButton());
		if(RGTEntities.BUSINESS_PROCESS.getEntity().equals(entity)) {
			if(CollectionUtils.isEmpty(RGTApplication.getApplicationInstance().getBusinessProcesses())){
				initializeMessageTab(RGTEntities.BUSINESS_PROCESS.getEntity());
			}else{
				initializeBusinessProcessTab();	
			}
		} else if(RGTEntities.STEP.getEntity().equals(entity)) {
			if(CollectionUtils.isEmpty(RGTApplication.getApplicationInstance().getSteps())){
				initializeMessageTab(RGTEntities.STEP.getEntity());
			}else{
				initializeStepTab();	
			}
		} else if(RGTEntities.ACTION.getEntity().equals(entity)) {
			if(CollectionUtils.isEmpty(RGTApplication.getApplicationInstance().getActions())){
				initializeMessageTab(RGTEntities.ACTION.getEntity());
			}else{
				initializeActionTab();	
			}
		}
	}
	
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

	private void initializeActionTab() {
		setBorder(BorderFactory.createTitledBorder(RGTConstants.DELETE_ACTION_DIALOG));
		
		GridBagConstraints constraints = new GridBagConstraints();

		JLabel businessProcessComboLabel = new JLabel(RGTConstants.LABEL_COMBO_SELECT_BP);
		JComboBox<BusinessProcessData> businessProcessComboBox = new JComboBox<BusinessProcessData>();
		
		EditController editCtrl = new EditController();
		List<BusinessProcessData> businessProcesses = new ArrayList<BusinessProcessData>(editCtrl.getAllBusinessProcesses()); 
        
		BusinessProcessData defaultBPData = new BusinessProcessData();
		defaultBPData.setVerbNounPhrase(RGTConstants.DEFAULT_BP_COMBOBOX);
		businessProcesses.add(0, defaultBPData);
		
        for(BusinessProcessData item: businessProcesses) {
        	// To display the verb-noun phrase in the combobox override the toString method in BusinessProcessData  
        	businessProcessComboBox.addItem(item);
        }
		
        constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.insets = new Insets(2,2,2,2);
		
		// First Column
		constraints.anchor = GridBagConstraints.LINE_END;
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(businessProcessComboLabel,constraints);
		
		// Second Column
		constraints.anchor = GridBagConstraints.LINE_START;
		
		constraints.gridx = 1;
		constraints.gridy = 0;
		add(businessProcessComboBox,constraints);

		businessProcessComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					BusinessProcessData selectedBusinessProcess = (BusinessProcessData)event.getItem();
					
					if(selectedBusinessProcess.getVerbNounPhrase().equals(RGTConstants.DEFAULT_BP_COMBOBOX)) {
						return;
					}
					
					EditController editCtrl = new EditController();
					List<StepData> steps = new ArrayList<StepData>(editCtrl.getAllSteps(selectedBusinessProcess.getBusinessProcessId()));
					
					StepData defaultStepData = new StepData();
					defaultStepData.setVerbNounPhrase(RGTConstants.DEFAULT_STEP_COMBOBOX);
					steps.add(0, defaultStepData);
					
					if(null != stepComboBox) {
						remove(stepComboBox);
					}
						
					if(null != stepComboLabel) {
						remove(stepComboLabel);
					}
					
					GridBagConstraints constraints = new GridBagConstraints();
					stepComboLabel = new JLabel(RGTConstants.LABEL_COMBO_SELECT_STEPS);
					stepComboBox = new JComboBox<StepData>();
					
					for(StepData item: steps) {
			        	stepComboBox.addItem(item);
			        }
					
			        constraints.weightx = 0.5;
					constraints.weighty = 0.5;
					constraints.insets = new Insets(2,2,2,2);
					
					// First Column
					constraints.anchor = GridBagConstraints.LINE_END;
					
					constraints.gridx = 0;
					constraints.gridy = 1;
					add(stepComboLabel,constraints);
					
					// Second Column
					constraints.anchor = GridBagConstraints.LINE_START;
					
					constraints.gridx = 1;
					constraints.gridy = 1;
					add(stepComboBox,constraints);

					stepComboBox.addItemListener(new ItemListener() {
						@Override
						public void itemStateChanged(ItemEvent stepEvent) {
							if (stepEvent.getStateChange() == ItemEvent.SELECTED) {
								
								if(selectedBusinessProcess.getVerbNounPhrase().equals(RGTConstants.DEFAULT_BP_COMBOBOX)) {
									return;
								}
								
								StepData selectedStep = (StepData)stepEvent.getItem();
								EditController editCtrl = new EditController();
								List<ActionData> actions = new ArrayList<ActionData>(editCtrl.getAllActions(selectedStep.getStepId()));
								
								ActionData defaultActionData = new ActionData();
								defaultActionData.setVerbNounPhrase(RGTConstants.DEFAULT_ACTION_COMBOBOX);
								actions.add(0, defaultActionData);
								
								if(null != actionComboBox) {
									remove(actionComboBox);
								}
									
								if(null != actionComboLabel) {
									remove(actionComboLabel);
								}
								
								GridBagConstraints constraints = new GridBagConstraints();
								actionComboLabel = new JLabel(RGTConstants.LABEL_COMBO_SELECT_ACTIONS);
								actionComboBox = new JComboBox<ActionData>();
								JButton deleteButton = new JButton(RGTButtons.DELETE.getButton());
								
								for(ActionData item: actions) {
						        	actionComboBox.addItem(item);
						        }
								
						        constraints.weightx = 0.5;
								constraints.weighty = 0.5;
								constraints.insets = new Insets(2,2,2,2);
								
								// First Column
								constraints.anchor = GridBagConstraints.LINE_END;
								
								constraints.gridx = 0;
								constraints.gridy = 2;
								add(actionComboLabel,constraints);
								
								constraints.gridx = 0;
								constraints.gridy = 3;
								add(deleteButton,constraints);
	
								// Second Column
								constraints.anchor = GridBagConstraints.LINE_START;
								
								constraints.gridx = 1;
								constraints.gridy = 2;
								add(actionComboBox,constraints);
	
								constraints.gridx = 1;
								constraints.gridy = 3;
								add(cancelButton,constraints);
								
								deleteButton.addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {
										showConfDialogAction(e);
									}
								});
								
								cancelButton.addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {
										Container container = (Container)e.getSource();
										JDialog parentDialog = (JDialog) SwingUtilities.getWindowAncestor(container);
										parentDialog.dispose();
									}
								});
							}
							revalidate();
						}
					});
					revalidate();
				}
			}
		});
	}

	private void showConfDialogAction(ActionEvent e) {
		Container container = (Container) e.getSource();
		JDialog deleteDialogBox = (JDialog) SwingUtilities.getWindowAncestor(container);
		
		EditController editCtrl = new EditController();
		ActionData selectedAction = (ActionData)actionComboBox.getSelectedItem();
		
		if(selectedAction.getVerbNounPhrase().equals(RGTConstants.DEFAULT_ACTION_COMBOBOX)) {
			JOptionPane.showMessageDialog(deleteDialogBox, RGTConstants.VALIDATION_MESSAGE_EMPTY_COMBOBOX,
					RGTConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE, null);
		}
		else {
			int dialogResult = JOptionPane.showConfirmDialog(deleteDialogBox, RGTConstants.MESSAGE_CONFIRM_DELETE,
					RGTConstants.MESSAGE_CONFIRM_DIALOG_TITLE, JOptionPane.YES_NO_OPTION);
			if (dialogResult == JOptionPane.YES_OPTION) {
				deleteDialogBox.dispose();
				editCtrl.deleteAction(selectedAction.getActionId());
				outPanelNew(e);
				JOptionPane.showMessageDialog(deleteDialogBox, RGTConstants.MESSAGE_DELETE_ITEM, RGTConstants.MESSAGE_DELETE_TITLE, JOptionPane.INFORMATION_MESSAGE);
				refreshDeleteDialog(deleteDialogBox, RGTConstants.ACTION_TAB_INDEX);
			}
		}
		
	}
	
	private void initializeStepTab() {
		setBorder(BorderFactory.createTitledBorder(RGTConstants.DELETE_ACTION_DIALOG));
		
		GridBagConstraints constraints = new GridBagConstraints();

		JLabel businessProcessComboLabel = new JLabel(RGTConstants.LABEL_COMBO_SELECT_BP);
		JComboBox<BusinessProcessData> businessProcessComboBox = new JComboBox<BusinessProcessData>();
		
		EditController editCtrl = new EditController();
		List<BusinessProcessData> businessProcesses = new ArrayList<BusinessProcessData>(editCtrl.getAllBusinessProcesses()); 
        
		BusinessProcessData defaultBPData = new BusinessProcessData();
		defaultBPData.setVerbNounPhrase(RGTConstants.DEFAULT_BP_COMBOBOX);
		businessProcesses.add(0, defaultBPData);
		
        for(BusinessProcessData item: businessProcesses) {
        	// To display the verb-noun phrase in the combobox override the toString method in BusinessProcessData  
        	businessProcessComboBox.addItem(item);
        }
		
        constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.insets = new Insets(2,2,2,2);
		
		// First Column
		constraints.anchor = GridBagConstraints.LINE_END;
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(businessProcessComboLabel,constraints);
		
		// Second Column
		constraints.anchor = GridBagConstraints.LINE_START;
		
		constraints.gridx = 1;
		constraints.gridy = 0;
		add(businessProcessComboBox,constraints);

		businessProcessComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					BusinessProcessData selectedBusinessProcess = (BusinessProcessData)event.getItem();
					
					if(selectedBusinessProcess.getVerbNounPhrase().equals(RGTConstants.DEFAULT_BP_COMBOBOX)) {
						return;
					}
					
					EditController editCtrl = new EditController();
					JButton deleteButton = new JButton(RGTButtons.DELETE.getButton());
					
					List<StepData> steps = new ArrayList<StepData>(editCtrl.getAllSteps(selectedBusinessProcess.getBusinessProcessId()));
					
					StepData defaultStepData = new StepData();
					defaultStepData.setVerbNounPhrase(RGTConstants.DEFAULT_STEP_COMBOBOX);
					steps.add(0, defaultStepData);
					
					if(null != stepComboBox) {
						remove(stepComboBox);
					}
						
					if(null != stepComboLabel) {
						remove(stepComboLabel);
					}
					
					GridBagConstraints constraints = new GridBagConstraints();
					stepComboLabel = new JLabel(RGTConstants.LABEL_COMBO_SELECT_STEPS);
					stepComboBox = new JComboBox<StepData>();
					
					for(StepData item: steps) {
			        	stepComboBox.addItem(item);
			        }
					
			        constraints.weightx = 0.5;
					constraints.weighty = 0.5;
					constraints.insets = new Insets(2,2,2,2);
					
					// First Column
					constraints.anchor = GridBagConstraints.LINE_END;
					
					constraints.gridx = 0;
					constraints.gridy = 1;
					add(stepComboLabel,constraints);
					
					constraints.gridx = 0;
					constraints.gridy = 2;
					add(deleteButton,constraints);
					
					// Second Column
					constraints.anchor = GridBagConstraints.LINE_START;
					
					constraints.gridx = 1;
					constraints.gridy = 1;
					add(stepComboBox,constraints);
					
					constraints.gridx = 1;
					constraints.gridy = 2;
					add(cancelButton,constraints);
					
					deleteButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							showConfDialogStep(e);
						}
					});
					
					cancelButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							Container container = (Container)e.getSource();
							JDialog parentDialog = (JDialog) SwingUtilities.getWindowAncestor(container);
							parentDialog.dispose();
						}
					});
				}
				revalidate();
			}
		});

	}

	private void showConfDialogStep(ActionEvent e) {
		Container container = (Container) e.getSource();
		JDialog deleteDialogBox = (JDialog) SwingUtilities.getWindowAncestor(container);
		
		EditController editCtrl = new EditController();
		StepData selectedStep = (StepData)stepComboBox.getSelectedItem();
		
		if(selectedStep.getVerbNounPhrase().equals(RGTConstants.DEFAULT_STEP_COMBOBOX)) {
			JOptionPane.showMessageDialog(deleteDialogBox, RGTConstants.VALIDATION_MESSAGE_EMPTY_COMBOBOX,
					RGTConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE, null);
		}
		else {
			int dialogResult = JOptionPane.showConfirmDialog(deleteDialogBox, RGTConstants.MESSAGE_CONFIRM_DELETE,
					RGTConstants.MESSAGE_CONFIRM_DIALOG_TITLE, JOptionPane.YES_NO_OPTION);
			if (dialogResult == JOptionPane.YES_OPTION) {
				deleteDialogBox.dispose();
				editCtrl.deleteStep(selectedStep.getStepId());
				outPanelNew(e);
				JOptionPane.showMessageDialog(deleteDialogBox, RGTConstants.MESSAGE_DELETE_ITEM, RGTConstants.MESSAGE_DELETE_TITLE, JOptionPane.INFORMATION_MESSAGE);
				refreshDeleteDialog(deleteDialogBox, RGTConstants.STEP_TAB_INDEX);
			}
		}
	}
	
	private void initializeBusinessProcessTab() {
		setBorder(BorderFactory.createTitledBorder(RGTConstants.DELETE_ACTION_DIALOG));
		
		GridBagConstraints constraints = new GridBagConstraints();

		JLabel businessProcessComboLabel = new JLabel(RGTConstants.LABEL_COMBO_SELECT_BP);
		businessProcessComboBox = new JComboBox<BusinessProcessData>();
		JButton deleteButton = new JButton(RGTButtons.DELETE.getButton());
		
		EditController editCtrl = new EditController();
		List<BusinessProcessData> businessProcesses = new ArrayList<BusinessProcessData>(editCtrl.getAllBusinessProcesses()); 
        
		BusinessProcessData defaultBPData = new BusinessProcessData();
		defaultBPData.setVerbNounPhrase(RGTConstants.DEFAULT_BP_COMBOBOX);
		businessProcesses.add(0, defaultBPData);
		
        for(BusinessProcessData item: businessProcesses) {
        	// To display the verb-noun phrase in the combobox override the toString method in BusinessProcessData  
        	businessProcessComboBox.addItem(item);
        }
		
        constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.insets = new Insets(2,2,2,2);
		
		// First Column
		constraints.anchor = GridBagConstraints.LINE_END;
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(businessProcessComboLabel,constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		add(deleteButton,constraints);
		
		// Second Column
		constraints.anchor = GridBagConstraints.LINE_START;
		
		constraints.gridx = 1;
		constraints.gridy = 0;
		add(businessProcessComboBox,constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 1;
		add(cancelButton,constraints);
		
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showConfDialogBP(e);
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Container container = (Container)e.getSource();
				JDialog parentDialog = (JDialog) SwingUtilities.getWindowAncestor(container);
				parentDialog.dispose();
			}
		});
	}

	private void showConfDialogBP(ActionEvent e) {
		Container container = (Container) e.getSource();
		JDialog deleteDialogBox = (JDialog) SwingUtilities.getWindowAncestor(container);
		
		EditController editCtrl = new EditController();
		BusinessProcessData selectedBusinessProcess = (BusinessProcessData)businessProcessComboBox.getSelectedItem();
		
		if(selectedBusinessProcess.getVerbNounPhrase().equals(RGTConstants.DEFAULT_BP_COMBOBOX)) {
			JOptionPane.showMessageDialog(deleteDialogBox, RGTConstants.VALIDATION_MESSAGE_EMPTY_COMBOBOX,
					RGTConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE, null);
		}
		else {
			int dialogResult = JOptionPane.showConfirmDialog(deleteDialogBox, RGTConstants.MESSAGE_CONFIRM_DELETE,
					RGTConstants.MESSAGE_CONFIRM_DIALOG_TITLE, JOptionPane.YES_NO_OPTION);
			if (dialogResult == JOptionPane.YES_OPTION) {
				deleteDialogBox.dispose();
				editCtrl.deleteBusinessProcess(selectedBusinessProcess.getBusinessProcessId());
				outPanelNew(e);
				JOptionPane.showMessageDialog(deleteDialogBox, RGTConstants.MESSAGE_DELETE_ITEM, RGTConstants.MESSAGE_DELETE_TITLE, JOptionPane.INFORMATION_MESSAGE);
				refreshDeleteDialog(deleteDialogBox, RGTConstants.BUSINESS_PROCESS_TAB_INDEX);
			}
		}
	}
	
	public void refreshDeleteDialog(JDialog deleteDialogBox, int selectedTabIndex) {
		DeleteDialog deleteDialog = new DeleteDialog((RToolView)deleteDialogBox.getParent());
		deleteDialog.getTabbedPane().setSelectedIndex(selectedTabIndex);
		deleteDialog.setTitle(RGTConstants.DELETE_DIALOG_BOX_TITLE);
		deleteDialog.setSize(RGTConstants.DIALOG_WIDTH, RGTConstants.DIALOG_HEIGHT);
		deleteDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		deleteDialog.setLocation(dimension.width/2-deleteDialog.getSize().width/2, dimension.height/2-deleteDialog.getSize().height/2);
		deleteDialog.setVisible(true);
	}
	
	public void initializeMessageTab(String entities){
		JLabel errorMessage = null;
		if(entities.equals(RGTEntities.BUSINESS_PROCESS.getEntity())){
			setBorder(BorderFactory.createTitledBorder(RGTConstants.DELETE_BUSINESS_PROCESS_DIALOG));
			errorMessage = new JLabel(RGTConstants.ERROR_NO_BUSINESSPROCESS);
		}else if(entities.equals(RGTEntities.STEP.getEntity())){
			setBorder(BorderFactory.createTitledBorder(RGTConstants.DELETE_STEP_DIALOG));
			errorMessage = new JLabel(RGTConstants.ERROR_NO_STEP);
		}else if(entities.equals(RGTEntities.ACTION.getEntity())){
			setBorder(BorderFactory.createTitledBorder(RGTConstants.DELETE_ACTION_DIALOG));
			errorMessage = new JLabel(RGTConstants.ERROR_NO_ACTION);
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
