package rgt.data;

public abstract class VerbNounPhraseData {
	private String verbPhrase;
	private String nounPhrase;
	private String verbNounPhrase;
	
	public String getVerbPhrase() {
		return verbPhrase;
	}
	public void setVerbPhrase(String verbPhrase) {
		this.verbPhrase = verbPhrase;
	}
	public String getNounPhrase() {
		return nounPhrase;
	}
	public void setNounPhrase(String nounPhrase) {
		this.nounPhrase = nounPhrase;
	}
	public String getVerbNounPhrase() {
		return verbNounPhrase;
	}
	public void setVerbNounPhrase(String verbNounPhrase) {
		this.verbNounPhrase = verbNounPhrase;
	}
	public abstract StringBuilder display(String title, String user);
}
