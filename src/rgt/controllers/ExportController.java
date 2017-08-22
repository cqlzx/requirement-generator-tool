package rgt.controllers;

import java.io.File;

import rgt.interfaces.ExportInterface;
import rgt.strategies.TxtExport;

public class ExportController {
    public void exportRequirements(File file, String content) {
    	ExportInterface export = new TxtExport();
    	export.exportRequirements(file, content);
    }
}
