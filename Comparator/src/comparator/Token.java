package comparator;
/*This class wraps a string with a boolean to determine if it has been marked*/
public class Token {

	private String token;//the string
	private boolean marked;//the boolean
	
	public Token (String t, boolean m) {
		token = t;
		marked = m;
	}
	
	public boolean getMarked() {
		return marked;
	}//getMarked
	
	public String getToken() {
		return token;
	}//getToken
	
	public void mark() {
		marked = true;
	}//mark
	public void unmark() {
		marked = false;
	}//unmark
}//Token
