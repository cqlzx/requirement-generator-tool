/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rgt.strategies;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import rgt.interfaces.ReadInterface;
/**
 *
 * @author HarshaTriyambaka
 */
public class DocxRead implements ReadInterface{
    public String readfile(File f){
        String str = "";
        try{
            FileInputStream fis = new FileInputStream(f.getAbsolutePath());
            //WordExtractor extractor = null ;
            XWPFDocument docx=new XWPFDocument(fis);
            List<XWPFParagraph> paragraphList = docx.getParagraphs();
            //extractor = new WordExtractor(doc);
            //String [] fileData = extractor.getParagraphText();
            for(XWPFParagraph paragraph: paragraphList){
                str += paragraph.getText(); 
                str +='\n';
            }
            docx.close();
        }
        catch(Exception e){}
	return str;
    }
    
}
