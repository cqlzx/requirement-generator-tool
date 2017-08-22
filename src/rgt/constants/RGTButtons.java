package rgt.constants;

public enum RGTButtons {
	ADD("Add"), 
	UPDATE("Update"), 
	DELETE("Delete"),
	EXPORT("Export Requirements"), 
	CANCEL("Cancel"),
	GENERATE("Generate"),
	AUTO_HIGHLIGHT("Auto Highlight");
	
	private String button;
	
	RGTButtons(String button) {
		this.button = button;
	}
	
	public String getButton() {
		return button;
	}
}
