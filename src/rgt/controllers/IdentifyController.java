package rgt.controllers;

import java.io.File;

import rgt.application.ApplicationManager;
import rgt.constants.RGTConstants;
import rgt.interfaces.ApplicationManagerInterface;
import rgt.interfaces.ReadInterface;
import rgt.strategies.DocRead;
import rgt.strategies.DocxRead;
import rgt.strategies.ReadManager;
import rgt.strategies.TxtRead;

public class IdentifyController {

    private ReadInterface impmng;

    public IdentifyController(ReadInterface impmng) {
        this.impmng = impmng;
    }
    public IdentifyController() {
    }

    public String executeStrategy(File f) {
        return impmng.readfile(f);
    }

    public void clear() {
        ApplicationManagerInterface manager = new ApplicationManager();
        manager.clearAll();
    }
    
    public String readFile(String str1, String str2) {
    	ReadManager mgr = new ReadManager();
    	return mgr.readFile(str1, str2);
	}

}
