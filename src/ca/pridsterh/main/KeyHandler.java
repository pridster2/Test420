package ca.pridsterh.main;

public class KeyHandler {
	private UsesKeys workingObject = null;
	
	public void redirectKey(String input, String action) {
		if (this.getObject() == null) {
			return;
		} else {
			workingObject.processKey(input, action);
		}
	}
	
	public void setObject(UsesKeys obj) {
		this.workingObject = obj;
	}
	
	public UsesKeys getObject() {
		return workingObject;
	}
}