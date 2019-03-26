import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class AlgorithmRunner extends Thread {

	String filePath, fileID, fileName;
	File fileDirectory;
	PrintWriter writer;
	
	public AlgorithmRunner (String path, String processID) {
		
		filePath = path;
		fileID = processID;

		fileDirectory = new File(filePath + "/" + fileID + "/");
		
		try {
			writer = new PrintWriter("log_" + fileID + ".txt", "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}//End catch
		
		//Printing stuff to the Log-Writer
		writer.println("Beginning Log");
		writer.println("File Path: " + filePath);
		writer.println("File ID: " + fileID);
		writer.println("");
		writer.flush();
	}//End Constructor
	
	//Execution Method
	@Override
	public void run() {
		
		String[] files = fileDirectory.list();
		
		writer.println("Projects Detected: ");
		for (int i = 0; i < files.length; i++) {
			writer.println(i + ": " + files[i]);
		}//End for
		writer.flush();

		//For each file in the directory
		for (int i = 0; i < files.length; i++) {
			try {
				prepareFiles(files[i], fileDirectory.getAbsolutePath());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}//End try
		}//End for
		
		
		
		writer.println();
		writer.println("Source Files Detected:");
		for (File file: fileDirectory.listFiles()) {
			writer.println(file.getName());
			for (File f: file.listFiles()) {
				writer.println(f.getName().substring(4));
			}//End for
			writer.println();
		}//End for
		
		//Removing Projects with no Source-files
		for (File file: fileDirectory.listFiles()) {
			if (file.list().length <= 0) {
				writer.println("No source files located in " + file.getName());
				file.delete();
			}//End if
		}//End for
		
		/* COMPARATOR GO HERE
		 * What we have to work with:
		 * 		PrintWriter writer 	-  Used to log information
		 * 		File fileDirectory 	- The directory of the specific Process-request (eg. /1234/* )
		 * 							- Sub-directories will be each assignment and inside them will be the source code and the tokenized code in separate files
		 * 		
		//*/
		writer.println();
		Running_Karp_Rabin s = new Running_Karp_Rabin(fileDirectory.getAbsolutePath(), writer);
		
		writer.println("Algorithm Complete");
		
		//Removes the files
		purgeDirectory(fileDirectory);
		fileDirectory.delete();
		
		
		//Closes the Log-Writer
		writer.close();
	}//End run
	
	//Purges the given directory
	private void purgeDirectory(File f) {
		for (File file: f.listFiles()) {
			if (file.isDirectory()) {
				purgeDirectory(file);
			}//End if
			file.delete();
		}//End for
	}//End purgeDirectory
	
	private void prepareFiles(String fileName, String filePath) throws FileNotFoundException {
		
		//Need to remove unnessecary files...
		String dirPath = filePath + "/" + fileName;
		File myDirectory = new File(dirPath);
		
		//Removing unimportant files
		//System.out.println(dirPath);
		removeExcess(myDirectory);
		compressDirectory(myDirectory, myDirectory);
		
		//Begins tokenizing...
		String files[] = myDirectory.list();
		Tokenizer runner[] = new Tokenizer[files.length];
		for (int i = 0; i < files.length; i ++) {
			runner[i] = new Tokenizer(dirPath + "/" + files[i], dirPath + "/tok_" + files[i]);
		}//End for
		
		/* RESULTS IN A WIERD BUG....
		 * It seems that the Tokenizers are running parallel to THIS thread
		 * Because of that, the file.delete() is running while the tokenizer is still running... Just learving them in the meantime
		//Removes original files
		 * 
		 * 
		 * BUG APPEARS FIXED
		 * LEAVING THE COMMENT ABOVE INCASE IT RE-Appears in this location		 * 
		 * 
		 * */
		String name;
		for (File file: myDirectory.listFiles()) {
			name = file.getName();
			
			if (!name.startsWith("tok_")) {
				//System.out.println(name);
				file.delete();
			}//End if
		}//End for*/

	}//End doThings
	
	private void removeExcess(File currFile) {
		if (currFile.isDirectory()) {
			for (File f: currFile.listFiles()) {
				removeExcess(f);
			}//End for
		} else {
			String filePath = currFile.getAbsolutePath();
			String fileType;
			int i = filePath.lastIndexOf('.');
			fileType = filePath.substring(i+1);
			
			//For unimportant file-types
			if (!(fileType.equals("java") || fileType.equals("cpp") || fileType.equals("h") || fileType.equals("c"))) {
				currFile.delete();
			}//End if
		}//End if
	}//End removeExcess
	
	private void compressDirectory(File currFile, File destDir) {
		for (File f: currFile.listFiles()) {
			if (f.isDirectory()) {
				compressDirectory(f, destDir);
				f.delete();
			} else {
				
				//Ensuring the file doesnt already exist, if it does, add a number to the end til its good to go
				boolean exists = true;
				File temp = null;
				String fileName = f.getName();
				String newName;
				int duplicate = 0;
				while (exists) {
					
					if (duplicate == 0) {
						newName = destDir.getAbsolutePath() + "/" + fileName;
					} else {
						newName = destDir.getAbsolutePath() + "/" + fileName + "(" + duplicate + ")";
					}//End if
					temp = new File(newName);
					exists = temp.exists();
					if (exists) {
						duplicate++;
					}//End if
				}//End while
				
				//Moving
				f.renameTo(temp);
				
			}//End if
		}//End for
		
		
	}//End compressDirectory

}//End AlgorithmRunner
