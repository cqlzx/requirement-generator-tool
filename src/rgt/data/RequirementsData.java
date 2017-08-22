package rgt.data;

import java.util.ArrayList;
import java.util.List;

public class RequirementsData extends VerbNounPhraseData {
	private List<VerbNounPhraseData> children=new ArrayList<VerbNounPhraseData>();
	private static RequirementsData data = new RequirementsData();
	
	private RequirementsData() {}
	
	public static RequirementsData getInstance() {
		return data;
	}
	
	@Override
	public StringBuilder display(String title, String user) {
		StringBuilder output = new StringBuilder();
		for(VerbNounPhraseData vnData: children) {
			output.append(vnData.display(title, user));
		}
		return output;
	}

	public void add(VerbNounPhraseData verbNounPhrase) {
		children.add(verbNounPhrase);
	}

}
