package rgt.controllers;

import java.util.Collections;
import java.util.List;

import rgt.application.ApplicationManager;
import rgt.constants.RGTConstants;
import rgt.data.ActionData;
import rgt.data.BusinessProcessData;
import rgt.data.RequirementsData;
import rgt.data.StepData;
import rgt.data.VerbNounPhraseData;
import rgt.interfaces.ApplicationManagerInterface;

public class GenerateController {
	public StringBuilder getOutputContent(String title, String user) {
		ApplicationManagerInterface manager = new ApplicationManager();
		int businessProcessSeqNumber = 1;
		int stepSeqNumber = 1;
		int actionSeqNumber = 1;
		String sentence = " The " + title + " should allow " + user + " to ";
		List<BusinessProcessData> businessProcesses = manager.getAllBusinessProcesses();

		StringBuilder output = new StringBuilder(RGTConstants.EMPTY);
		Collections.sort(businessProcesses, new BusinessProcessData());
		for (BusinessProcessData businessProcess : businessProcesses) {
			output.append("R" + businessProcessSeqNumber + sentence
					+ businessProcess.getVerbNounPhrase() + "\n");
			List<StepData> steps = manager.getAllSteps(businessProcess.getBusinessProcessId());
			Collections.sort(steps, new StepData());
			for (StepData step : steps) {
				output.append("\t" + "R" + businessProcessSeqNumber + "." + stepSeqNumber + sentence
						+ step.getVerbNounPhrase() + "\n");
				List<ActionData> actions = manager.getAllActions(step.getStepId());
				Collections.sort(actions, new ActionData());
				for (ActionData action : actions) {
					output.append("\t\t" + "R" + businessProcessSeqNumber + "." + stepSeqNumber + "." + actionSeqNumber
							+ sentence + action.getVerbNounPhrase() + "\n");
					actionSeqNumber++;
				}
				actionSeqNumber = 1;
				stepSeqNumber++;
			}
			stepSeqNumber = 1;
			actionSeqNumber = 1;
			businessProcessSeqNumber++;
		}
		return output;
	}
	
	public StringBuilder getOutputContent1(String title, String user) {
		VerbNounPhraseData data = RequirementsData.getInstance();
		
		return data.display(title, user);
	}

}
