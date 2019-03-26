
public class Token {

	private String token;
	private boolean marked;
	
	public Token (String t, boolean m) {
		token = t;
		marked = m;
	}
	
	public boolean getMarked() {
		return marked;
	}
	
	public String getToken() {
		return token;
	}
	
	public void mark() {
		marked = true;
	}
	public void unmark() {
		marked = false;
	}
}
