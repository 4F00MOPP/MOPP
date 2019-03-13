package comparator;

import java.util.StringTokenizer;
import java.util.Hashtable;
import java.util.ArrayList;
public class Running_Karp_Rabin {
	private Hashtable<String, Integer> values;
	private int length;
	private int numOfCollisions=0;//the number of collisions when the algorithm is run. This is tracked for performance reasons
	
	//this constructor makes a unique hash entry for each token in a given alphabet
	public Running_Karp_Rabin(String...alphabet) {
		values = new Hashtable<>();
		length = alphabet.length;
		int i = 0;
		for(String s : alphabet) {
			values.put(s, i);
			i++;
		}
		
		
		
	}//constructor
	
	
	
	

	
	public void parse(String file1, String file2) {
		ArrayList<Token> tokens1 = new ArrayList<>();
		ArrayList<Token> tokens2 = new ArrayList<>();
		StringTokenizer letters1 = new StringTokenizer(file1, ":");
		StringTokenizer letters2 = new StringTokenizer(file2, ":");
		while(letters1.hasMoreTokens()) {
			tokens1.add(new Token(letters1.nextToken(),false));
		}
		while(letters2.hasMoreTokens()) {
			tokens2.add(new Token(letters2.nextToken(), false));
		}
		
		
		
	}
	
	//this method generates the initial hash for the pattern and the first substring of the text
	public int generateHash(String pattern){
		int patternLength = pattern.length();//the length of the pattern being examined
		char[] letters = pattern.toCharArray();
		int hash = 0;//the starting hash value
		int power = letters.length-1;//the weighted position of each token in the string
		for(int i=0; i<letters.length; i++) {
			hash+=(values.get(""+letters[i])*Math.pow(values.size(), power));
			power--;
		}
		return hash;
	}//generateHash
	
	//this method performs a Karp-Rabin String comparison to determine if a pattern exists in a text
	public void karprabin(String text, String pattern) {
		if(text==null||pattern==null||text.length()<pattern.length())
			return;
		int hash = generateHash(pattern);//the patterns hash value
		int startOfSubstring = 0;//the beginning of the substring to be examined
		int endOfSubstring = pattern.length();//the end of the substring to be examined
		String substring = text.substring(startOfSubstring, endOfSubstring);//the substring
		endOfSubstring--;//this decrement is necessary because Java Strings are dumb
		int comparison = generateHash(substring);//the hash value of the substring
		boolean found = false;//whether the pattern was found in the text
		for(;;){
			if(comparison == hash) {//first the hash values are compared, if they are equal, each element of the String is compared
				boolean isMatch = true;//whether the match is for realsies or simply a collission of the hash function
				for(int i=0; i<pattern.length(); i++) {
					if(text.charAt(startOfSubstring+i)!=pattern.charAt(i)) {
						isMatch = false;
						numOfCollisions++;
						break;
					}
				}
				if(isMatch) {
					found = true;
					System.out.println("Pattern exists from "+startOfSubstring+" to "+endOfSubstring);//if there is a match, printout where it exists
				}
			}
			/*This chunk of code updates the hash value and moves the examined portion of the String over by one character
			 * It does this by first subtracting the value the first character of the String plays in determining the hash value,
			 * then multiplying the result of this subtraction by the base (the number of tokens in the alphabet). Next, the value
			 * substring is incremented by one and the new Tokens value is added to the hash value. If the end of the String is reached,
			 * the Algorithm concludes*/
			comparison-=(values.get(""+text.charAt(startOfSubstring))*Math.pow(values.size(), pattern.length()-1));
			comparison*=values.size();
			startOfSubstring++;
			endOfSubstring++;
			try {
				comparison+=values.get(""+text.charAt(endOfSubstring));
			}catch(IndexOutOfBoundsException e) {
				break;
			}
		}
		if(!found) {
			System.out.println("Pattern not Found");
		}
		System.out.println("Number of Collisions: "+numOfCollisions);
		
	}//karprabin
	
	
	
	public static void main(String[] args) {
		String[] alphabet = new String[] {"a","b","c","d","e","f","g","h","i","j"};
		Running_Karp_Rabin s = new Running_Karp_Rabin(alphabet);
		s.karprabin("jcdjhifbjhdabcaabjhd", "abbaaj");
		s.karprabin("jcdjhifbjhdabcaabjhd", "jhd");
		s.karprabin("jcdjhifbjhdabcaabjhd", "aab");

	}//main

}//Running_Karp_Rabin
