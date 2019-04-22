import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class SystemStarter {

	public static void main (String args[]) throws IOException, InterruptedException {
		
		System.out.println("Beginning MOPP 'Server'...");
		
		String programPath = System.getProperty("user.dir");
		
		File parentDirectory = new File(System.getProperty("user.dir")).getParentFile();
		File submissionDirectory = new File(parentDirectory, "process");
		
		String systemPath = programPath + "\\process\\";
		
		System.out.println(parentDirectory.getAbsolutePath());
		System.out.println(submissionDirectory.getAbsolutePath());
		
		//Registering a Listener for the folder
		Path dirFolder = Paths.get(submissionDirectory.getAbsolutePath()+"/");
		WatchService watchService = FileSystems.getDefault().newWatchService();
		dirFolder.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
		
		System.out.println("Submission listener registered on: " + submissionDirectory.getAbsolutePath());
		System.out.println("Awaiting file submission.");
		
		//Listener Loop
		Boolean valid = true;
		while (valid) {
			WatchKey watchKey = watchService.take();
			for (WatchEvent event : watchKey.pollEvents()) {
				WatchEvent.Kind kind = event.kind();
				if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) {
					String fileName = event.context().toString();

					//Validates the Filename is correct
					if (validName(fileName)) {
						Thread.sleep(5000);	//Waits for 5 seconds to ensure ALL files are successfully transferred to the folder before beginning
						System.out.println("New File Detected: " + fileName);
						System.out.println("Beginning new Thread...");

						//Begins the Thread
						new AlgorithmRunner(submissionDirectory.getAbsolutePath(), fileName).start();
					}//End if
					
					//Stops the Server
					if (fileName.equals("stop")) {
						System.out.println("System stop received.");
						valid = false;
					}//End if
					
				}//End if
			}//End for
			
			if (!valid) {
				break;
			}//End if
			
			valid = watchKey.reset();
		}//End while
		System.out.println("Stopping server.");
	
	}//End main
	
	
	//Validates that the fileName given is of the form: XXXX where X is a digit
	private static boolean validName(String fileName) {
		boolean result = true;
		result = result && fileName.length() == 4;
		if (result) {
			result = result && Character.isDigit(fileName.charAt(0));
			result = result && Character.isDigit(fileName.charAt(1));
			result = result && Character.isDigit(fileName.charAt(2));
			result = result && Character.isDigit(fileName.charAt(3));
		}//End if
		
		if (fileName.equals("BULK")) result = true;
		
		return result;
	}//End validName
	
	
}//End SystemStarter
