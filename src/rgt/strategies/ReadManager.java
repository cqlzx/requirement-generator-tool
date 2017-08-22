package rgt.strategies;

import java.io.File;

import rgt.constants.RGTConstants;
import rgt.interfaces.ReadInterface;

public class ReadManager {

	public String readFile(String str1, String str2) {
		String str3, str4 = RGTConstants.EMPTY;
		if (null != str1 && null != str2) {
			str3 = str1 + str2;
			File f = new File(str3);
			if (str2.contains(".txt")) {
				ReadInterface impobj = new TxtRead();
				str4 = impobj.readfile(f);
			} else if (str2.contains(".docx")) {
				ReadInterface impobj = new DocxRead();
				str4 = impobj.readfile(f);
			} else if (str2.contains(".doc")) {
				ReadInterface impobj = new DocRead();
				str4 = impobj.readfile(f);
			}
		}
		return str4;
	}

}
