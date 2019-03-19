import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Semaphore;

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
		
		writer.println("Files Detected: ");

		for (int i = 0; i < files.length; i++) {
			writer.println(i + ": " + files[i]);
		}//End for
		writer.flush();
		
		//For each file in the directory
		for (int i = 0; i < files.length; i++) {
			doThings(files[i]);
		}//End for
		
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
	
	private void doThings(String fileName) {
		
	}//End doThings
	
	
	
	
	

}//End AlgorithmRunner
