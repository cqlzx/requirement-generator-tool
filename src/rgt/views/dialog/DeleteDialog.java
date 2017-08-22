package rgt.views.dialog;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import rgt.constants.RGTConstants;
import rgt.constants.RGTEntities;
import rgt.views.panels.DeletePanel;

public class DeleteDialog extends JDialog{
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;
	
	public DeleteDialog() {
		super();
		initializeDeleteDialog();
	}

	public DeleteDialog(JFrame rToolView) {
		super(rToolView, true);
		initializeDeleteDialog();
	}

	private void initializeDeleteDialog() {
		setLayout(new BorderLayout()); 

		tabbedPane = new JTabbedPane();
		
		tabbedPane.addTab("<html><body width='120'><center>"+RGTEntities.BUSINESS_PROCESS.getEntity()+"</center></body></html>", 
				null, new DeletePanel(RGTEntities.BUSINESS_PROCESS.getEntity()), RGTConstants.TOOLTIP_ADD_TAB1);
		tabbedPane.addTab("<html><body width='120'><center>"+RGTEntities.STEP.getEntity()+"</table></body></html>", 
				null, new DeletePanel(RGTEntities.STEP.getEntity()), RGTConstants.TOOLTIP_ADD_TAB2);
		tabbedPane.addTab("<html><body width='120'><center>"+RGTEntities.ACTION.getEntity()+"</table></body></html>", 
				null, new DeletePanel(RGTEntities.ACTION.getEntity()), RGTConstants.TOOLTIP_ADD_TAB3);

		Container container = getContentPane();
		container.add(tabbedPane, BorderLayout.NORTH);
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

}
