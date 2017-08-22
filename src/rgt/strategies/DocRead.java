/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rgt.strategies;
import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import rgt.interfaces.ReadInterface;
/**
 *
 * @author HarshaTriyambaka
 */
public class DocRead implements ReadInterface{
    public String readfile(File f){
        String str = "";
        try{
            FileInputStream fis = new FileInputStream(f.getAbsolutePath());
            //WordExtractor extractor = null ;
            HWPFDocument doc=new HWPFDocument(fis);
            WordExtractor extractor = new WordExtractor(doc);
            String[] fileData = extractor.getParagraphText();
            //extractor = new WordExtractor(doc);
            //String [] fileData = extractor.getParagraphText();
            for(String paragraph: fileData){
                str += paragraph; 
                str +='\n';
            }
            extractor.close();
        }
        catch(Exception e){}
	return str;
    }
    
}
