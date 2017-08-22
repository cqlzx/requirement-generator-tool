package rgt.controllers;

import java.util.List;

import rgt.application.ApplicationManager;
import rgt.data.ActionData;
import rgt.data.BusinessProcessData;
import rgt.data.StepData;
import rgt.interfaces.ApplicationManagerInterface;

public class EditController {
	public void addBusinessProcess(BusinessProcessData businessProcess) {
		ApplicationManagerInterface manager = new ApplicationManager();
		manager.addBusinessProcess(businessProcess);
	}
	
	public void addStep(StepData step) {
		ApplicationManagerInterface manager = new ApplicationManager();
		manager.addStep(step);
	}
	
	public void addAction(ActionData action) {
		ApplicationManagerInterface manager = new ApplicationManager();
		manager.addAction(action);
	}
	
	public void updateBusinessProcess(BusinessProcessData businessProcess) {
		ApplicationManagerInterface manager = new ApplicationManager();
		manager.updateBusinessProcess(businessProcess);	
	}
	
	public void updateStep(StepData step) {
		ApplicationManagerInterface manager = new ApplicationManager();
		manager.updateStep(step);
	}
	
	public void updateAction(ActionData action) {
		ApplicationManagerInterface manager = new ApplicationManager();
		manager.updateAction(action);
	}

	public void deleteBusinessProcess(String businessProcessId) {
		ApplicationManagerInterface manager = new ApplicationManager();
		manager.deleteBusinessProcess(businessProcessId);
	}
	
	public void deleteStep(String stepId) {
		ApplicationManagerInterface manager = new ApplicationManager();
		manager.deleteStep(stepId);
	}
	
	public void deleteAction(String actionId) {
		ApplicationManagerInterface manager = new ApplicationManager();
		manager.deleteAction(actionId);
	}

	public List<BusinessProcessData> getAllBusinessProcesses() {
		ApplicationManagerInterface manager = new ApplicationManager();
		return manager.getAllBusinessProcesses();
	}
	
	public List<StepData> getAllSteps(String businessProcessId) {
		ApplicationManagerInterface manager = new ApplicationManager();
		return manager.getAllSteps(businessProcessId);
	}
	
	public List<ActionData> getAllActions(String stepId) {
		ApplicationManagerInterface manager = new ApplicationManager();
		return manager.getAllActions(stepId);
	}

	public BusinessProcessData getBusinessProcess(String businessProcessId) {
		ApplicationManagerInterface manager = new ApplicationManager();
		return manager.getBusinessProcess(businessProcessId);
	}
	
	public StepData getStep(String stepId) {
		ApplicationManagerInterface manager = new ApplicationManager();
		return manager.getStep(stepId);
	}
	
	public ActionData getAction(String actionId) {
		ApplicationManagerInterface manager = new ApplicationManager();
		return manager.getAction(actionId);
	}

	public List<StepData> getAllSteps() {
		ApplicationManagerInterface manager = new ApplicationManager();
		return manager.getAllSteps();
	}
	
	public List<ActionData> getAllActions() {
		ApplicationManagerInterface manager = new ApplicationManager();
		return manager.getAllActions();
	}
}
