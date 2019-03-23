package comparator;

import java.util.ArrayList;

public class Wrapper {
	private ArrayList<Token> list;
	private int index;
	private Integer index2;
	private String f1;
	private String f2;
	private String l1;
	private String l2;
	
	public Wrapper(int t, Integer p, ArrayList<Token> l, String file1, String file2, String line1, String line2 ) {
		index= t;
		index2 = p;
		list = l;
		f1 = file1;
		f2 = file2;
		l1 = line1;
		l2 = line2;
	}
	
	public ArrayList<Token> getList(){
		return list;
	}
	
	public int getIndex() {
		return index;
	}
	
	public Integer getIndex2() {
		return index2;
	}
	
	public void setIndex2(int a) {
		index2 = a;
	}
	
	public void setIndex(int a) {
		index = a;
	}
	
	public String getFile1Name() {
		return f1;
	}
	
	public String getFile2Name() {
		return f2;
	}
	
	public String getLine1() {
		return l1;
	}
	
	public String getLine2() {
		return l2;
	}
	
	public void setLine2(String s) {
		l2 = s;
	}

}
