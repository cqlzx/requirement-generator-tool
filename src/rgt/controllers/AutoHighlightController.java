/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rgt.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 *
 * @author HarshaTriyambaka
 */
public class AutoHighlightController {
	String str;
	List<String> noun;
	List<String> verb;
	public AutoHighlightController(String s) {
		this.str = s;
	}
	public void tagInput(){
		noun = new ArrayList<String>();
		verb = new ArrayList<String>();
		String tagged = "";
		MaxentTagger tagger = null;
		try {
			tagger = new MaxentTagger("tagger/bidirectional-distsim-wsj-0-18.tagger");
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		String[] line = str.split("\n");
		for(int i=0;i<line.length;i++){
			tagged = tagger.tagString(line[i]);
			String[] words = tagged.split(" ");
			for (int j=0;j<words.length;j++){
				String[] word = words[j].split("/");
				if(word[1].equals("NN") || word[1].equals("NNP") || word[1].equals("NNS") || word[1].equals("NNPS") || word[1].equals("JJ")){
					noun.add(word[0]);
				}
				else if(word[1].equals("VB") || word[1].equals("VBD") || word[1].equals("VBG") || word[1].equals("VBN") || 
						word[1].equals("VBZ") || word[1].equals("VBP") ||word[1].equals("MD")){
					verb.add(word[0]);
				}
			}
		}
	}
	public List<String> getNouns() {
		return noun;
	}
	public List<String> getVerbs() {
		return verb;
	}
}
