package rgt.interfaces;

import java.util.List;

import rgt.data.ActionData;
import rgt.data.BusinessProcessData;
import rgt.data.StepData;

public interface ApplicationManagerInterface {
	void updateBusinessProcess(BusinessProcessData businessProcessData);
	void updateStep(StepData stepData);
	void updateAction(ActionData actionData);
	void deleteBusinessProcess(String businessProcessId);
	void deleteStep(String stepId);
	void deleteAction(String actionId);
	BusinessProcessData getBusinessProcess(String businessProcessId);
	StepData getStep(String stepId);
	ActionData getAction(String actionId);
	void addBusinessProcess(BusinessProcessData newBusinessProcess);
	void addStep(StepData newStep);
	void addAction(ActionData newAction);
	List<BusinessProcessData> getAllBusinessProcesses();
	List<StepData> getAllSteps(String businessProcessId);
	List<ActionData> getAllActions(String stepId);
	List<StepData> getAllSteps();
	void clearAll();
	List<ActionData> getAllActions();

}
