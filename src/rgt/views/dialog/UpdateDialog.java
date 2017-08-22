package rgt.views.dialog;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import rgt.constants.RGTConstants;
import rgt.constants.RGTEntities;
import rgt.views.panels.UpdatePanel;

public class UpdateDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	public UpdateDialog() {
		super();
		initializeUpdateDialog();
	}

	public UpdateDialog(JFrame rToolView) {
		super(rToolView, true);
		initializeUpdateDialog();
	}

	private void initializeUpdateDialog() {
		setLayout(new BorderLayout()); 

		JTabbedPane tabbedPane = new JTabbedPane();
		
		JComponent updatePanel = new UpdatePanel();
		tabbedPane.addTab("<html><body width='120'><center>"+RGTEntities.BUSINESS_PROCESS.getEntity()+"</center></body></html>", null, updatePanel, RGTConstants.TOOLTIP_UPDATE_TAB1);
		tabbedPane.addTab("<html><body width='120'><center>"+RGTEntities.STEP.getEntity()+"</table></body></html>", null, new JPanel(), RGTConstants.TOOLTIP_UPDATE_TAB2);
		tabbedPane.addTab("<html><body width='120'><center>"+RGTEntities.ACTION.getEntity()+"</table></body></html>", null, new JPanel(), RGTConstants.TOOLTIP_UPDATE_TAB3);

		Container container = getContentPane();
		container.add(tabbedPane, BorderLayout.NORTH);
	}
}
