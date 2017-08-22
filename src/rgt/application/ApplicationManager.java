package rgt.application;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import rgt.data.ActionData;
import rgt.data.BusinessProcessData;
import rgt.data.RequirementsData;
import rgt.data.StepData;
import rgt.interfaces.ApplicationManagerInterface;

public class ApplicationManager implements ApplicationManagerInterface{

	/* Updates the business process by using the given businessProcessData */
	@Override
	public void updateBusinessProcess(BusinessProcessData businessProcessData) {
		RGTApplication app = RGTApplication.getApplicationInstance();
		List<BusinessProcessData> businessProcesses = app.getBusinessProcesses();

		ListIterator<BusinessProcessData> businessProcessIterator = businessProcesses.listIterator();
		while (businessProcessIterator.hasNext()) {
			BusinessProcessData businessProcess = businessProcessIterator.next();
			if (businessProcessData.getBusinessProcessId().equals(businessProcess.getBusinessProcessId())) {
				businessProcessIterator.set(businessProcessData);
				break;
			}
		}

		app.setBusinessProcesses(businessProcesses);
	}

	/* Updates the steps by using the given stepData */
	@Override
	public void updateStep(StepData stepData) {
		RGTApplication app = RGTApplication.getApplicationInstance();
		List<StepData> steps = app.getSteps();

		ListIterator<StepData> stepIterator = steps.listIterator();

		while (stepIterator.hasNext()) {
			StepData step = stepIterator.next();
			if (stepData.getStepId().equals(step.getStepId())) {
				stepIterator.set(stepData);
				break;
			}
		}

		app.setSteps(steps);
	}

	/* Updates the actions by using the given actionData */
	@Override
	public void updateAction(ActionData actionData) {
		RGTApplication app = RGTApplication.getApplicationInstance();
		List<ActionData> actions = app.getActions();

		ListIterator<ActionData> actionIterator = actions.listIterator();

		while (actionIterator.hasNext()) {
			ActionData action = actionIterator.next();
			if (actionData.getActionId().equals(action.getActionId())) {
				actionIterator.set(actionData);
				break;
			}
		}

		app.setActions(actions);
	}

	/* Deletes the business process by using businessProcessId */
	@Override
	public void deleteBusinessProcess(String businessProcessId) {
		RGTApplication app = RGTApplication.getApplicationInstance();
		List<BusinessProcessData> businessProcesses = app.getBusinessProcesses();

		Iterator<BusinessProcessData> iterator = businessProcesses.iterator();
		while (iterator.hasNext()) {
			BusinessProcessData businessProcess = iterator.next();
			if (businessProcessId.equals(businessProcess.getBusinessProcessId())) {
				iterator.remove();
				for (StepData step : getAllSteps(businessProcessId)) {
					deleteStep(step.getStepId());
				}
				break;
			}
		}

		app.setBusinessProcesses(businessProcesses);
	}

	/* Deletes the step by using stepId */
	@Override
	public void deleteStep(String stepId) {
		RGTApplication app = RGTApplication.getApplicationInstance();
		List<StepData> steps = app.getSteps();

		Iterator<StepData> iterator = steps.iterator();
		while (iterator.hasNext()) {
			StepData step = iterator.next();
			if (stepId.equals(step.getStepId())) {
				iterator.remove();
				for (ActionData action : getAllActions(stepId)) {
					deleteAction(action.getActionId());
				}
				break;
			}
		}

		app.setSteps(steps);
	}

	/* Deletes the action by using actionId */
	@Override
	public void deleteAction(String actionId) {
		RGTApplication app = RGTApplication.getApplicationInstance();
		List<ActionData> actions = app.getActions();

		Iterator<ActionData> iterator = actions.iterator();
		while (iterator.hasNext()) {
			ActionData action = iterator.next();
			if (actionId.equals(action.getActionId())) {
				iterator.remove();
				break;
			}
		}

		app.setActions(actions);
	}

	/*
	 * Gets the business process data by using businessProcessId if it exists.
	 * Otherwise returns null
	 */
	@Override
	public BusinessProcessData getBusinessProcess(String businessProcessId) {
		RGTApplication app = RGTApplication.getApplicationInstance();
		BusinessProcessData businessProcessData = new BusinessProcessData();
		for (BusinessProcessData businessProcess : app.getBusinessProcesses()) {
			if (businessProcessId.equals(businessProcess.getBusinessProcessId())) {
				businessProcessData = businessProcess;
				break;
			}
		}
		return businessProcessData;
	}

	/*
	 * Gets the step data by using stepId if it exists. Otherwise returns null
	 */
	@Override
	public StepData getStep(String stepId) {
		RGTApplication app = RGTApplication.getApplicationInstance();
		StepData stepData = new StepData();
		for (StepData step : app.getSteps()) {
			if (stepId.equals(step.getStepId())) {
				stepData = step;
				break;
			}
		}
		return stepData;
	}

	/*
	 * Gets the action data by using actionId if it exists. Otherwise returns
	 * null
	 */
	@Override
	public ActionData getAction(String actionId) {
		RGTApplication app = RGTApplication.getApplicationInstance();
		ActionData actionData = new ActionData();
		for (ActionData action : app.getActions()) {
			if (actionId.equals(action.getActionId())) {
				actionData = action;
				break;
			}
		}
		return actionData;
	}

	/*
	 * Adds the new business process provided by the user to the existing
	 * business process list
	 */
	@Override
	public void addBusinessProcess(final BusinessProcessData newBusinessProcess) {
		RGTApplication app = RGTApplication.getApplicationInstance();
		List<BusinessProcessData> existingBusinessProcesses = app.getBusinessProcesses();
		existingBusinessProcesses.add(newBusinessProcess);
		app.setBusinessProcesses(existingBusinessProcesses);
		
		RequirementsData data = RequirementsData.getInstance();
		data.add(newBusinessProcess);
		
	}

	/* Adds the new step provided by the user to the existing step list */
	@Override
	public void addStep(final StepData newStep) {
		RGTApplication app = RGTApplication.getApplicationInstance();
		List<StepData> existingSteps = app.getSteps();
		existingSteps.add(newStep);
		app.setSteps(existingSteps);
		
		RequirementsData data = RequirementsData.getInstance();
		data.add(newStep);
	}

	/* Adds the new action provided by the user to the existing action list */
	@Override
	public void addAction(final ActionData newAction) {
		RGTApplication app = RGTApplication.getApplicationInstance();
		List<ActionData> existingActions = app.getActions();
		existingActions.add(newAction);
		app.setActions(existingActions);
		
		RequirementsData data = RequirementsData.getInstance();
		data.add(newAction);
	}

	/* Returns all the business processes in the application */
	@Override
	public List<BusinessProcessData> getAllBusinessProcesses() {
		return RGTApplication.getApplicationInstance().getBusinessProcesses();
	}

	/* Returns all the steps for a given business process */
	@Override
	public List<StepData> getAllSteps(String businessProcessId) {
		List<StepData> steps = RGTApplication.getApplicationInstance().getSteps();
		List<StepData> stepsForBusinessProcess = new ArrayList<StepData>();

		for (StepData step : steps) {
			if (step.getBusinessProcessId().equals(businessProcessId)) {
				stepsForBusinessProcess.add(step);
			}
		}
		return stepsForBusinessProcess;
	}

	/* Returns all the actions for a given step */
	@Override
	public List<ActionData> getAllActions(String stepId) {
		List<ActionData> actions = RGTApplication.getApplicationInstance().getActions();
		List<ActionData> actionsForStep = new ArrayList<ActionData>();

		for (ActionData action : actions) {
			if (action.getStepId().equals(stepId)) {
				actionsForStep.add(action);
			}
		}
		return actionsForStep;
	}

	@Override
	public List<StepData> getAllSteps() {
		return RGTApplication.getApplicationInstance().getSteps();
	}

	@Override
	public void clearAll() {
		RGTApplication.getApplicationInstance().setActions(new ArrayList<ActionData>());
		RGTApplication.getApplicationInstance().setBusinessProcesses(new ArrayList<BusinessProcessData>());
		RGTApplication.getApplicationInstance().setSteps(new ArrayList<StepData>());
	}

	@Override
	public List<ActionData> getAllActions() {
		return RGTApplication.getApplicationInstance().getActions();
	}
}
