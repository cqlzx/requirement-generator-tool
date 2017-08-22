package rgt.strategies;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import rgt.interfaces.ExportInterface;

public class TxtExport implements ExportInterface {

	@Override
	public void exportRequirements(File file, String content) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(content);
		} catch (IOException ex) {
			Logger.getLogger(rgt.views.OutputView.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException ex) {
					Logger.getLogger(rgt.views.OutputView.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}

}
