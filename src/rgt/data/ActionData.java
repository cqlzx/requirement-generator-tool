package rgt.data;

import java.util.Comparator;

import rgt.application.ApplicationManager;

public class ActionData extends VerbNounPhraseData implements Comparator<ActionData>{
	private String actionId;
	private String stepId;
	private int actionSeqNumber;

	public String getActionId() {
		return actionId;
	}
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}	
	public String getStepId() {
		return stepId;
	}
	public void setStepId(String stepId) {
		this.stepId = stepId;
	}
	public int getActionSeqNumber() {
		return actionSeqNumber;
	}
	public void setActionSeqNumber(int actionSeqNumber) {
		this.actionSeqNumber = actionSeqNumber;
	}
	@Override
	public String toString() {
		return this.getVerbNounPhrase();
	}
	@Override
	public int compare(ActionData o1, ActionData o2) {
		if(o1.getActionSeqNumber() == o2.getActionSeqNumber()) {
			return 0;
		}
		else if(o1.getActionSeqNumber() > o2.getActionSeqNumber()) {
			return 1;
		} 
		else {
			return -1;	
		}
	}
	@Override
	public StringBuilder display(String title, String user) {
		String sentence = " The " + title + " should allow " + user + " to ";
		ApplicationManager mgr = new ApplicationManager();
		
		return new StringBuilder("\t\t" + "R" + mgr.getBusinessProcess(mgr.getStep(stepId).getBusinessProcessId()) + "." + mgr.getStep(stepId).getStepSeqNumber() + "." + actionSeqNumber
				+ sentence + this.getVerbNounPhrase() + "\n");
	}
	
}
