
import java.util.StringTokenizer;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.HashSet;
public class Running_Karp_Rabin {
	private int lineNumbers = 0;
	private int tiles;
	private PrintWriter scribe;
	private Hashtable<String, Integer> values;
	private static final int HASH_PRIME = 3;
	private static final int MIN_MATCH_LENGTH=5;
	private Hashtable<Long,LinkedList<Wrapper>>karpRabinHash;
	private String file1name ="";
	private String file2name = "";
	private String file1start = "";
	private String file2start = "";
	private String[] allProjectNames; 
	private File[][] allFiles;
	private int length;
	private int numOfCollisions=0;//the number of collisions when the algorithm is run. This is tracked for performance reasons
	ArrayList<Token> tokens1 = new ArrayList<>();
	ArrayList<Token> tokens2 = new ArrayList<>();
	LinkedList<LinkedList<Wrapper>> queueOfQueues;

	
	//this constructor makes a unique hash entry for each token in a given alphabet
	public Running_Karp_Rabin(String directory, PrintWriter sc) {
		scribe =sc;
		File f = new File(directory);
		File[] subdirectories = f.listFiles();
		allFiles = new File[subdirectories.length][];
		allProjectNames = new String[subdirectories.length];
		String[][] tokens = new String[allFiles.length][];
		for(int i=0; i<subdirectories.length; i++) {
			allProjectNames[i] = subdirectories[i].getName();
			allFiles[i] = subdirectories[i].listFiles();
			tokens[i] = new String[allFiles[i].length];
		}
		Scanner s;
		for(int i=0; i<allFiles.length; i++) {
			for(int j=0; j<allFiles[i].length; j++) {
				String text = "";
				try {
					s = new Scanner(allFiles[i][j]);
					while(s.hasNext()) {
						text+=s.next();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
				tokens[i][j] = text;
			}
		}
		HashSet<String> set = new HashSet<>();
		String[][] key = Tokens.importTokens();
		karpRabinHash = new Hashtable<>();
		LinkedList<String> alphabet = new LinkedList<>();
		int index = 1;
		for(int i=0; i<key.length; i++) {
			StringTokenizer t = new StringTokenizer(key[i][index],":");
			while(t.hasMoreTokens()) {
				String temp = t.nextToken();
				if(!set.contains(temp)) {
					set.add(temp);
					alphabet.add(temp);
				}
			}
		}
		
		values = new Hashtable<>();
		length = alphabet.size();
		int i = 0;
		for(String letter : alphabet) {
			values.put(letter, i);
			i++;
		}
		start(tokens);
		
		
		
		
		
	}//constructor
	
	//this method determines the percentage of tokens which were found in another document
	private double percentMatching(ArrayList<Token> list) {
		double tokens = 0;
		double actualSize = list.size();
		for(int i=0; i<list.size(); i++) {
			String token = list.get(i).getToken();
			try {
				Integer.parseInt(token);
				actualSize--;
			}catch(NumberFormatException e) {
				if(list.get(i).getMarked())
					tokens++;
			}
		}
		return (tokens/actualSize)*100;
	}//percentMatching
	//this method performs the comparison between all files in a two dimensional array
	public void start(String[][] files) {
		queueOfQueues = new LinkedList<>();
		ArrayList<Token> tokens1 = new ArrayList<>();
		ArrayList<Token> tokens2 = new ArrayList<>();
		ArrayList<ArrayList<ArrayList<Token>>> tokens = new ArrayList<>(files.length);
		for(int i=0; i<files.length; i++) {
			for(int j = 0; j<files[i].length; j++) {
				if(j==0) {
					ArrayList<ArrayList<Token>> temp = new ArrayList<>();
					temp.add(parse(files[i][j]));
					tokens.add(temp);
				}
				else {
					tokens.get(i).add(parse(files[i][j]));
				}
			}
		}
		for(int i=0; i<tokens.size(); i++) {
			//file1name = "file"+(i+1);
			for(int j=0; j<tokens.get(i).size(); j++) {
				ArrayList<Token> firstFile = tokens.get(i).get(j);
				file1name = allFiles[i][j].getName();
				String temp[] = allProjectNames[i].split("_");
				scribe.println("Analysis:");
				scribe.println(file1name + "_" + temp[2] + "_" + temp[3]);
				scribe.flush();
				boolean inProgress = false;
				outer2:for(int k=0; k<tokens.size(); k++ ) {
					for(int l=0; l<tokens.get(k).size(); l++) {
						
						if(k==i)
							continue outer2;
						else {
							queueOfQueues.clear();
							if(!inProgress) {
								for(int m=0; m<firstFile.size(); m++) {
									firstFile.get(m).unmark();
								
								}
								inProgress = true;
							}
							ArrayList<Token> secondFile = tokens.get(k).get(l);
							for(int m=0; m<secondFile.size(); m++) {
								secondFile.get(m).unmark();
							}
							file2name = allFiles[k][l].getName();
							
							runningKarpRabin(firstFile,secondFile);
						}
					}
				}
				
				scribe.println("Percentage of Similarity: "+percentMatching(firstFile));
				scribe.println();
				scribe.flush();
				}
			
			}
		}//start

		
	
	

	//this method parses files
	public ArrayList<Token> parse(String file1) {
		StringTokenizer letters1 = new StringTokenizer(file1, ":");
		//StringTokenizer letters2 = new StringTokenizer(file2, ":");
		ArrayList<Token> tokens1 = new ArrayList<>();
		//ArrayList<Token> tokens2 = new ArrayList<>();
		while(letters1.hasMoreTokens()) {
			tokens1.add(new Token(letters1.nextToken(),false));//the text file
		}
		/*while(letters2.hasMoreTokens()) {
			tokens2.add(new Token(letters2.nextToken(), false));//the pattern file
		}*/
		tokens1.remove(" ");
		//tokens2.remove(" ");
		
		return tokens1;
		
	}//parse
	//this method creates the hash for a value that does not appear in the table
	private long unknownHash(String s) {
		try {
			Integer.parseInt(s);
			return 0;
		}catch(NumberFormatException e) {}
		char[] letters = s.toCharArray();
		long sum = 0;
		for(char c : letters) {
			sum+=c;
		}
		return sum;
	}//unknownHash
	//this method generates the hash value for a string of tokens
	public long generateHash(ArrayList<Token> pattern) {
		int patternLength = pattern.size();
		long hash = 0;
		int power = patternLength-1;
		for(int i=0; i<patternLength; i++) {
			String temp = pattern.get(i).getToken();
			if(!values.containsKey(temp)) {
				hash+=unknownHash(temp);
				continue;
			}
			
			hash+=(values.get(temp)*Math.pow(HASH_PRIME, power));
			power--;
		}
		return hash;
	}//generateHash
	
	//this method finds the maximal matches of two files and records them in a queue	
	private int scanPattern(int s, ArrayList<Token> tokens1, ArrayList<Token> tokens2) {
		karpRabinHash.clear();
		queueOfQueues.clear();
		boolean enter = false;
		int maxMatch = 0;
		for(int i=0; i< tokens1.size(); i++) {//Starts at beginning of list for text
			if(getDistance(i, tokens1)<s)//if there are not enough consecutive tokens,
				continue;//go to next tile
			else {
				enter = true;
				ArrayList<Token> temp = new ArrayList<>();
				temp.addAll(tokens1.subList(i, i+s+lineNumbers));
				long code = generateHash(temp);//generate its hashcode
				LinkedList<Wrapper> queue = karpRabinHash.get(code);//check to see if collision
				if(queue==null) {//if not
					queue = new LinkedList<>();//create a new linkedlist
					Wrapper w = new Wrapper(i,null, temp, file1name, file2name, file1start, file2start);//wrap the Substring with the starting index
					queue.add(w);//add the substring to the list
					karpRabinHash.put(code,queue);//place the list in the hash table
				}
				else {
					Wrapper w = new Wrapper(i,null, temp, file1name, file2name, file1start, file2start);
					queue.add(w);//if there is a collision, add the substring to the list
				}
				
				
			}
		}
		if(!enter)
			return maxMatch;
		for(int i=0; i<tokens2.size(); i++) {//starting at first unmarked token
			if(getDistance(i, tokens2)<s)//if distance to next tile is <= s, go to next tile
				continue;
			else {
				ArrayList<Token> temp = new ArrayList<>();
				temp.addAll(tokens2.subList(i, i+s+lineNumbers));
				boolean start = false;
				long hashCode = generateHash(temp);//create the hash for the substring
				if(karpRabinHash.containsKey(hashCode)) {//check if hashtable contains the hash
					LinkedList<Wrapper> queue = karpRabinHash.get(hashCode);
					for(int a=0; a<queue.size(); a++){//for each entry with hash equal to created value
						ArrayList<Token> letter = queue.get(a).getList();
						boolean check = true;
						for(int j = 0; j<Math.max(letter.size(), temp.size()); j++) {//compare each token to see if same
							if(!letter.get(j).getToken().equals(temp.get(j).getToken())) {
								check = false;
								break;
							}
						}
						if(check) {//if they are the same
							int k = s;
							int t = queue.get(a).getIndex();
							while(i+k<tokens2.size()&&t+k<tokens1.size()&&tokens2.get(i+k).equals(tokens1.get(t+k))&!tokens2.get(i+k).getMarked()&!tokens1.get(t+k).getMarked()) {//check how long the maximal match is
								k++;
							}
							if(k>2*s)//if it is twice as long as the starting max length, return that value and rerun method
								return k;
							else {//otherwise, perform a sorted insert into a queue of queues.
								//scribe.append(file1name+", "+file2name+", "+file1start+", "+file2start);
								//System.out.println("adding to queue");
								int b = 0;
								int length = queue.size();
								for(;;) {
									if(b>=queueOfQueues.size()) {
										queue.get(a).setIndex2(i);
										queueOfQueues.add(queue);
										break;
									}
									LinkedList<Wrapper> temp1 = queueOfQueues.get(b);
									if(temp1.getFirst().getList().size()>length) {
										b++;
									}
									else {
										queue.get(a).setIndex2(i);
										queueOfQueues.add(b, queue);
										break;
									}
									
									
								}
							}
								
								
						}
					}
				}
				
			}
		}
		return maxMatch;
	}//scanPattern
	
	//this is the top level algorithm that finds the matches and then marks them
	private void runningKarpRabin(ArrayList<Token> tokens1, ArrayList<Token> tokens2) {
		int s = 20;
		tiles = 0;
		boolean stop = false;
		do {
			int maxLength = scanPattern(s, tokens1, tokens2);
			if(maxLength > 2*s)
				s = maxLength;
			else {
				markArrays(s, tokens1, tokens2);
				if(s>2*MIN_MATCH_LENGTH) {
					s/=2;
				}
				else if(s>MIN_MATCH_LENGTH) {
					s = MIN_MATCH_LENGTH;
				}
				else {
					stop = true;
				}
			}
				
		}while(!stop);
		scribe.println(file2name+": "+100*(double)tiles/(double)tokens1.size());
		scribe.flush();
		
	}//runningKarpRabin
	
	//this method marks the matching tiles
	private void markArrays(int s, ArrayList<Token> tokens1, ArrayList<Token> tokens2) {
		outer:for(LinkedList<Wrapper> list : queueOfQueues) {
			if(list.isEmpty()) {
				continue;
			}
			else {
				Wrapper match = list.pop();
				ArrayList<Token> string= match.getList();
				boolean occluded = false;
				int numTilesOccluded = 0;
				for(int i=0; i<string.size(); i++) {
					
					if(string.get(i).getMarked()) {
						occluded = true;
						numTilesOccluded++;
					}
						
				}
				if(!occluded) {
					int z = 0;
					int y =0;
					for(int j=0; j<s; j++) {
						//int a = 0;
						for(;;) {
							try {
							int index2 = match.getIndex2();
							}catch(NullPointerException e) {
								continue outer;
							}
							
							try {
								Integer.parseInt(tokens2.get(match.getIndex2()+z+j).getToken());
								z++;
							}catch(NumberFormatException e) {
								tokens2.get(match.getIndex2()+j+z).mark();
								//z++;
								break;
							}
						}
						//a=0;
						for(;;) {
							int index1 = match.getIndex();
							try {
								Integer.parseInt(tokens1.get(match.getIndex()+y+j).getToken());
								y++;
							}catch(NumberFormatException e) {
								tokens1.get(match.getIndex()+j+y).mark();
								//y++;
								break;
							}
						}
						//tokens2.get(match.getIndex2()+j).mark();
						//tokens1.get(match.getIndex()+j).mark();
						tiles++;
						
					}
					//System.out.println(file1name+", "+file2name+", "+match.getLine1()+", "+match.getLine2());
				}
				else if(string.size()-numTilesOccluded>=s) {
					int i=0;
					while(i<string.size()&&string.get(i).getMarked())
						i++;
					int j=i;
					while(j<string.size()&&!string.get(j).getMarked())
						j++;
					if(j+1==string.size())
						j++;
					ArrayList<Token> substring = new ArrayList<>();
					substring.addAll(string.subList(i, j));
					Wrapper temp = new Wrapper(match.getIndex()+i,match.getIndex2()+i, substring, match.getFile1Name(), match.getFile2Name(), match.getLine1(), match.getLine2());
					int b = 0;
					for(;;) {
						if(b>=queueOfQueues.size()) {
							LinkedList<Wrapper> queue = new LinkedList<>();
							queue.add(temp);
							queueOfQueues.add(queue);
							break;
						}
						LinkedList<Wrapper> temp1 = queueOfQueues.get(b);
						if(temp1.getFirst().getList().size()>temp.getList().size()) {
							b++;
						}
						else if(temp1.getFirst().getList().size()==temp.getList().size()) {
							temp1.add(temp);
							break;
						}
						else {
							LinkedList<Wrapper> queue = new LinkedList<>();
							queue.add(temp);
							queueOfQueues.add(b, queue);
							break;
						}
							
						}
					
				}
			}
		}
	}//markArrays
	
	//this method returns the number of consecutive unmarked tiles
	private int getDistance(int start, ArrayList<Token> tokens1) {
		lineNumbers = 0;
		int i=0;
		int sum = 1;
		while(i+start<tokens1.size()) {
			try {
				Integer.parseInt(tokens1.get(i+start).getToken());
				sum--;
				lineNumbers++;
			}catch(NumberFormatException e) {
				
			}
			if(tokens1.get(i+start).getMarked())
				return sum-1;
			i++;
			sum++;
		}
		return sum-1;
		
			
	}//getDistance
	
	
	
	
	
	//public static void main(String[] args) {
	//	String[] alphabet = new String[] {"a","b","c","d"};
	//	Running_Karp_Rabin s = new Running_Karp_Rabin("/home/andrew/eclipse-workspace/Comparator/src/textfiles", null);
	//	//s.karprabin("jcdjhifbjhdabcaabjhd", "abbaaj");
	//	//s.karprabin("jcdjhifbjhdabcaabjhd", "jhd");
	//	//s.karprabin("jcdjhifbjhdabcaabjhd", "aab");
	//
	//}//main

}//Running_Karp_Rabin
