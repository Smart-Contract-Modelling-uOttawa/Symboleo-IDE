package ca.uottawa.csmlab.symboleo.generator;

public class KeyValuePair {
	private String Key;
	private String Value;
	
	public KeyValuePair(String k) {
		this.Key = k;
	}
	
	public KeyValuePair(String k, String v) {
		this.Key = k;
		this.Value = v;
	}
	
	public void setValue(String v) {
		this.Value = v;
	}
	
	public String getKey() {
		return this.Key;
	}
	
	public String getValue() {
		return this.Value;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}