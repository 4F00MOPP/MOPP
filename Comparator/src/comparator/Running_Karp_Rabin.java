package comparator;

import java.util.StringTokenizer;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;
public class Running_Karp_Rabin {
	private int lineNumbers = 0;
	private int tiles;
	private PrintWriter scribe;
	private Hashtable<String, Integer> values;
	private static final int HASH_PRIME = 3;
	private static final int MIN_MATCH_LENGTH=2;
	private Hashtable<Long,LinkedList<Wrapper>>karpRabinHash;
	private String file1name ="";
	private String file2name = "";
	private String file1start = "";
	private String file2start = "";
	private int length;
	private int numOfCollisions=0;//the number of collisions when the algorithm is run. This is tracked for performance reasons
	ArrayList<Token> tokens1 = new ArrayList<>();
	ArrayList<Token> tokens2 = new ArrayList<>();
	LinkedList<LinkedList<Wrapper>> queueOfQueues;

	
	//this constructor makes a unique hash entry for each token in a given alphabet
	public Running_Karp_Rabin(String...alphabet) {
		String File3 = "";
		String File1 = "";
		String File2 = "";
		File file1 = new File("/home/andrew/eclipse-workspace/Comparator/src/file1.txt");
		Scanner s;
		try {
			s = new Scanner(file1);
		
		
		while(s.hasNext()) {
			File1 += s.next();
		}
		s.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		File file2 = new File("/home/andrew/eclipse-workspace/Comparator/src/file2.txt");
		try {
			s = new Scanner(file2);
		
		
		while(s.hasNext()) {
			File2 += s.next();
		}
		s.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File file3 = new File("/home/andrew/eclipse-workspace/Comparator/src/file3.txt");
		try {
			s = new Scanner(file3);
		
		
		while(s.hasNext()) {
			File3+= s.next();
		}
		s.close();
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		karpRabinHash = new Hashtable<>();
		values = new Hashtable<>();
		length = alphabet.length;
		int i = 0;
		for(String letter : alphabet) {
			values.put(letter, i);
			i++;
		}
		
		start(new String[] {File1, File2, File3});
		System.out.println("heree");
		
		
		
		
		
	}//constructor
	
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
	}
	
	public void start(String[] files) {
		queueOfQueues = new LinkedList<>();
		ArrayList<Token> tokens1 = new ArrayList<>();
		ArrayList<Token> tokens2 = new ArrayList<>();
		ArrayList<ArrayList<Token>> tokens = new ArrayList<>();
		for(int i=0; i<files.length; i++) {
			tokens.add(parse(files[i]));
		}
		for(int i=0; i<tokens.size(); i++) {
			file1name = "file"+(i+1);
			for(int j=0; j<tokens.size(); j++) {
				queueOfQueues.clear();
				file2name = "file"+(j+1);
				if(i>=j)
					continue;
				else {
					runningKarpRabin(tokens.get(i),tokens.get(j));
				}
			}
			System.out.println("Percent Plagiarised: "+percentMatching(tokens.get(i)));
		}

		System.out.println("end for realsies");
		/*parse(files[1], files[2]);
		runningKarpRabin();
		parse(files[0],files[2]);
		runningKarpRabin();*/
	}
	

	
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
		
	}
	
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
	}
	
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
	}
	
		
	private int scanPattern(int s, ArrayList<Token> tokens1, ArrayList<Token> tokens2) {
		//queueOfQueues = new LinkedList<>();
		boolean enter = false;
		int maxMatch = 0;
		for(int i=0; i< tokens1.size(); i++) {//Starts at beginning of list for text
			if(getDistance(i, tokens1)<s)//if there are not enough consecutive tokens,
				continue;//go to next tile
			else {
				enter = true;
				ArrayList<Token> temp = new ArrayList<>();
				//temp.addAll(tokens1.subList(i, i+s+lineNumbers));
				ArrayList<Token> temp1 = new ArrayList<>();
				boolean start = false;
				int z = i;
				for(int recover = i; recover<i+s; recover++) {
					try {
						Integer line = Integer.parseInt(tokens1.get(z).getToken());
						recover--;
						if(!start) {
							file1start = ""+line;
							start = true;
						}
					}catch(NumberFormatException e) {
						temp.add(tokens1.get(z));
					}
					z++;
				}
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
				//temp.addAll(tokens2.subList(i, i+s+lineNumbers));
				boolean start = false;
				for(int recover = i; recover<i+s+lineNumbers; recover++) {
					try {
						Integer line = Integer.parseInt(tokens2.get(recover).getToken());
						if(!start) {
							file2start = ""+line;
							start = true;
						}
					}catch(NumberFormatException e) {
						temp.add(tokens2.get(recover));
					}
				}
				long hashCode = generateHash(temp);//create the hash for the substring
				if(karpRabinHash.containsKey(hashCode)) {//check if hashtable contains the hash
					System.out.println("possible match");
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
							System.out.println("match");
							int k = s;
							int t = queue.get(a).getIndex();
							while(i+k<tokens2.size()&&t+k<tokens1.size()&&tokens2.get(i+k).equals(tokens1.get(t+k))&!tokens2.get(i+k).getMarked()&!tokens1.get(t+k).getMarked()) {//check how long the maximal match is
								k++;
							}
							if(k>2*s)//if it is twice as long as the starting max length, return that value and rerun method
								return k;
							else {//otherwise, perform a sorted insert into a queue of queues.
								//scribe.append(file1name+", "+file2name+", "+file1start+", "+file2start);
								System.out.println("adding to queue");
								int b = 0;
								int length = queue.size();
								for(;;) {
									if(b>=queueOfQueues.size()) {
										queue.get(a).setIndex2(i);
										queue.get(a).setLine2(file2start);
										queueOfQueues.add(queue);
										break;
									}
									LinkedList<Wrapper> temp1 = queueOfQueues.get(b);
									if(temp1.getFirst().getList().size()>length) {
										b++;
									}
									else {
										System.out.println("setting index 2");
										queue.get(a).setIndex2(i);
										queue.get(a).setLine2(file2start);
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
	}
	
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
		
	}
	
	
	private void markArrays(int s, ArrayList<Token> tokens1, ArrayList<Token> tokens2) {
		for(LinkedList<Wrapper> list : queueOfQueues) {
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
							int index2 = match.getIndex2();
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
					System.out.println(file1name+", "+file2name+", "+match.getLine1()+", "+match.getLine2());
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
	}
	
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
		
			
	}
	
	
	
	
	
	public static void main(String[] args) {
		String[] alphabet = new String[] {"a","b","c","d"};
		Running_Karp_Rabin s = new Running_Karp_Rabin(alphabet);
		//s.karprabin("jcdjhifbjhdabcaabjhd", "abbaaj");
		//s.karprabin("jcdjhifbjhdabcaabjhd", "jhd");
		//s.karprabin("jcdjhifbjhdabcaabjhd", "aab");

	}//main

}//Running_Karp_Rabin
