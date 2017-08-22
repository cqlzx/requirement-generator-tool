/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rgt.strategies;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import rgt.interfaces.ReadInterface;

/**
 *
 * @author HarshaTriyambaka
 */
public class TxtRead implements ReadInterface{
    public String readfile(File f){
        String str = "";
        try{
            FileReader fr = new FileReader(f);
            BufferedReader in =  new BufferedReader(fr);
            String line;
            while((line= in.readLine())!= null){
                str = str + line + "\n";
            }
            
            in.close();
        }
        catch(IOException e){}
	return str;
    }
}
