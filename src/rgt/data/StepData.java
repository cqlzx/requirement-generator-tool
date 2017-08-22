package rgt.data;

import java.util.Comparator;

import rgt.application.ApplicationManager;

public class StepData extends VerbNounPhraseData implements Comparator<StepData>{
	private String stepId;
	private String businessProcessId;
	private int stepSeqNumber;
	
	public String getStepId() {
		return stepId;
	}
	public void setStepId(String stepId) {
		this.stepId = stepId;
	}
	public String getBusinessProcessId() {
		return businessProcessId;
	}
	public void setBusinessProcessId(String businessProcessId) {
		this.businessProcessId = businessProcessId;
	}
	public int getStepSeqNumber() {
		return stepSeqNumber;
	}
	public void setStepSeqNumber(int stepSeqNumber) {
		this.stepSeqNumber = stepSeqNumber;
	}
	@Override
	public StringBuilder display(String title, String user) {
		String sentence = " The " + title + " should allow " + user + " to ";
		return new StringBuilder("\t" + "R" + new ApplicationManager().getBusinessProcess(businessProcessId).getPriority() + "." + stepSeqNumber + sentence
				+ this.getVerbNounPhrase() + "\n");
	}
	
	@Override
	public String toString() {
		return this.getVerbNounPhrase();
	}
	
	@Override
	public int compare(StepData o1, StepData o2) {
		if(o1.getStepSeqNumber()==o2.getStepSeqNumber())  
			return 0;  
		else if(o1.getStepSeqNumber()>o2.getStepSeqNumber())  
			return 1;  
		else  
			return -1;
	}
}
