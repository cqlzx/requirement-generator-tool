package rgt.data;

import java.util.Comparator;

public class BusinessProcessData extends VerbNounPhraseData implements Comparator<BusinessProcessData>{
	private String businessProcessId;
	private int priority;
	
	public String getBusinessProcessId() {
		return businessProcessId;
	}
	public void setBusinessProcessId(String businessProcessId) {
		this.businessProcessId = businessProcessId;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	@Override
	public String toString() {
		return this.getVerbNounPhrase();
	}
	@Override
	public int compare(BusinessProcessData o1, BusinessProcessData o2) {
		if(o1.getPriority() == o2.getPriority()) {
			return 0;	
		}
		else if(o1.getPriority() > o2.getPriority()) {
			return 1;
		} 
		else {
			return -1;
		}
		
	}
	@Override
	public StringBuilder display(String title, String user) {
		String sentence = " The " + title + " should allow " + user + " to ";
		return new StringBuilder("R" + this.priority + sentence
				+ this.getVerbNounPhrase() + "\n");
	}
	
}
