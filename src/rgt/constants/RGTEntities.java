package rgt.constants;

public enum RGTEntities {
	BUSINESS_PROCESS("Business Process"), 
	STEP("Step"), 
	ACTION("Action");
	
	private String entity;
	
	RGTEntities(String entity) {
		this.entity = entity;
	}
	
	public String getEntity() {
		return entity;
	}
}
