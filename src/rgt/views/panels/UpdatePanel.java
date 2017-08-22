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
import rgt.views.dialog.UpdateDialog;

public class UpdatePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel updateBusinessProcessLabel;
	private JComboBox<BusinessProcessData> updateBusinessProcessCombo;
	private JLabel verbNounLabel;
	private JTextField verbNounText;
	private JLabel priorityLabel;
	private JComboBox<String> priorityCombo;
	private JButton updateButton;
	private JButton cancelButton;
	
	public UpdatePanel() {
		super();
		initializeUpdatePanel();
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
	
	private void initializeUpdatePanel() {	
		setBorder(BorderFactory.createTitledBorder(RGTConstants.UPDATE_DIALOG));
		setLayout(new GridBagLayout());
		
		if(CollectionUtils.isEmpty(RGTApplication.getApplicationInstance().getBusinessProcesses())){
			initializeMessageTab(RGTEntities.BUSINESS_PROCESS.getEntity());
			return;
		}
		
		initializeCombo();
        
        updateBusinessProcessCombo.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					BusinessProcessData selectedBusinessProcess = (BusinessProcessData)event.getItem();
					
					if(selectedBusinessProcess.getVerbNounPhrase().equals(RGTConstants.DEFAULT_BP_COMBOBOX)) {
						return;
					}
					
					EditController editCtrl = new EditController();
					BusinessProcessData businessProcess = editCtrl.getBusinessProcess(selectedBusinessProcess.getBusinessProcessId());
					removeOldComponents();
					displaySelectedBusinessProcess(businessProcess);
					revalidate();
				}
			}

			private void removeOldComponents() {
				if(null != verbNounLabel) {
					remove(verbNounLabel);
				}
				if(null != verbNounText) {
					remove(verbNounText);
				}
				if(null != priorityLabel) {
					remove(priorityLabel);
				}
				if(null != priorityCombo) {
					remove(priorityCombo);
				}
				if(null != updateButton) {
					remove(updateButton);
				}
				if(null != cancelButton) {
					remove(cancelButton);
				}
			}
		}); 
	}

	private void initializeCombo() {
		EditController editCtrl = new EditController();
		List<BusinessProcessData> businessProcesses = new ArrayList<BusinessProcessData>(editCtrl.getAllBusinessProcesses());

		BusinessProcessData defaultBPData = new BusinessProcessData();
		defaultBPData.setVerbNounPhrase(RGTConstants.DEFAULT_BP_COMBOBOX);
		businessProcesses.add(0, defaultBPData);
		
		updateBusinessProcessLabel = new JLabel(RGTConstants.LABEL_COMBO_SELECT_BP);
        updateBusinessProcessCombo = new JComboBox<BusinessProcessData>();
        
        for(BusinessProcessData item: businessProcesses) {
        	// To display the verb-noun phrase in the combobox override the toString method in BusinessProcessData  
        	updateBusinessProcessCombo.addItem(item);
        }
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.insets = new Insets(2,2,2,2);
		
		// First Column
		constraints.anchor = GridBagConstraints.LINE_END;
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(updateBusinessProcessLabel, constraints);

        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.gridx = 1;
        constraints.gridy = 0;
        add(updateBusinessProcessCombo, constraints);
	}

	protected void displaySelectedBusinessProcess(BusinessProcessData businessProcess) {
		GridBagConstraints constraints = new GridBagConstraints();

		verbNounLabel = new JLabel(RGTConstants.LABEL_VERB_NOUN);
		verbNounText = new JTextField(businessProcess.getVerbNounPhrase(), RGTConstants.VERB_NOUN_INPUT_SIZE);
		priorityLabel = new JLabel(RGTConstants.LABEL_BUSINESS_PROCESS_PRIORITY);
		priorityCombo = new JComboBox<String>(RGTConstants.PRIORITY_VALUES); 
		priorityCombo.setSelectedItem(Integer.toString(businessProcess.getPriority()));
		updateButton = new JButton(RGTButtons.UPDATE.getButton());
		cancelButton = new JButton(RGTButtons.CANCEL.getButton());

		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.insets = new Insets(2,2,2,2);
		
		// First Column
		constraints.anchor = GridBagConstraints.LINE_END;
		
		constraints.gridx = 0;
		constraints.gridy = 3;
		add(verbNounLabel,constraints);

		constraints.gridx = 0;
		constraints.gridy = 4;
		add(priorityLabel,constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 5;
		add(updateButton,constraints);
		
		// Second Column
		constraints.anchor = GridBagConstraints.LINE_START;
		
		constraints.gridx = 1;
		constraints.gridy = 3;
		add(verbNounText,constraints);

		constraints.gridx = 1;
		constraints.gridy = 4;
		add(priorityCombo,constraints);

		constraints.gridx = 1;
		constraints.gridy = 5;
		add(cancelButton,constraints);

		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateBusinessProcess(businessProcess);
				outPanelNew(e);
				showSuccessDialog(e);
			}

			private void showSuccessDialog(ActionEvent e) {
				Container container = (Container)e.getSource();
				JDialog updateDialogBox = (JDialog) SwingUtilities.getWindowAncestor(container);
				
				int dialogResult = JOptionPane.showConfirmDialog(updateDialogBox, RGTConstants.MESSAGE_BUSINESS_PROCESS_UPDATE, RGTConstants.MESSAGE_UPDATE_TITLE, JOptionPane.YES_NO_OPTION);
				updateDialogBox.dispose();
				if(dialogResult == JOptionPane.YES_OPTION){
					UpdateDialog updateDialog = new UpdateDialog((RToolView)updateDialogBox.getParent());
					updateDialog.setTitle(RGTConstants.UPDATE_DIALOG_BOX_TITLE);
					updateDialog.setSize(RGTConstants.DIALOG_WIDTH, RGTConstants.DIALOG_HEIGHT);
					Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
					updateDialog.setLocation(dimension.width/2-updateDialog.getSize().width/2, dimension.height/2-updateDialog.getSize().height/2);
					updateDialog.setVisible(true);
					updateDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				}
			}

			private void updateBusinessProcess(BusinessProcessData businessProcess) {
				String updatedVNPhrase = verbNounText.getText();
				int priority = Integer.parseInt(priorityCombo.getSelectedItem().toString());
				
				BusinessProcessData updatedBusinessProcess = new BusinessProcessData();
				updatedBusinessProcess.setBusinessProcessId(businessProcess.getBusinessProcessId());
				updatedBusinessProcess.setVerbNounPhrase(updatedVNPhrase);
				updatedBusinessProcess.setPriority(priority);
				
				EditController editCtrl = new EditController();
				editCtrl.updateBusinessProcess(updatedBusinessProcess);
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Container container = (Container)e.getSource();
				JDialog parentDialog = (JDialog) SwingUtilities.getWindowAncestor(container);
				parentDialog.setVisible(false);
			}
		});
	}
	
	public void initializeMessageTab(String entities){
		JLabel errorMessage = null;
		if(entities.equals(RGTEntities.BUSINESS_PROCESS.getEntity())){
			errorMessage = new JLabel(RGTConstants.ERROR_NO_BUSINESSPROCESS);
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
