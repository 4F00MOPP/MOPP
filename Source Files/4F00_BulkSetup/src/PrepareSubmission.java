import java.io.File;
import javax.swing.JFileChooser;

public class PrepareSubmission {
	
	public static void main (String args[]) {
        
        System.out.println("Please select the Zipped file with the submission...");
        JFileChooser f = new JFileChooser();
        //f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
        //f.showSaveDialog(null);
        f.showOpenDialog(null);
        
        File currDir = f.getCurrentDirectory();
        File selectedFile = f.getSelectedFile();
        
        //FolderZipper.zipFolder();
        
        File unzippedFile = unzipFiles(currDir, selectedFile);
        
        renameFiles(unzippedFile);
        try {
        	 FolderZipper.zipFolder(unzippedFile.getAbsolutePath(), currDir.getAbsolutePath() + "/prep_" + unzippedFile.getName() + ".zip");
        } catch (Exception ex) { ex.printStackTrace(); }
       
        System.out.println("Prepared file generated: " + currDir.getAbsolutePath() + "/prep_" + unzippedFile.getName() + ".zip");
        

	}//End main
	
	private static File unzipFiles(File currDir, File selectedFile) {
		
		String fileName = selectedFile.getName();
		String zipFilePath = selectedFile.getAbsolutePath();
        String destDirectory = currDir.getPath() + "/" + fileName.substring(0, fileName.length()-4);
        
        new File(destDirectory).mkdirs();
        
        System.out.println(destDirectory);
        
        UnzipUtility unzipper = new UnzipUtility();
        try {
            unzipper.unzip(zipFilePath, destDirectory);
        } catch (Exception ex) {
            // some errors occurred
            ex.printStackTrace();
        }
		
        return new File(destDirectory);
        
	}//End unzipFiles
	
	private static void renameFiles(File selectedFile) {
		
		int counter = 0;
        File renamedFile;
        
        for (File subDir: selectedFile.listFiles()) {
        	counter++;
        	renamedFile = new File(selectedFile.getAbsolutePath() + "/Bulk_" + counter + "_" + counter + "_" + counter);
        	subDir.renameTo(renamedFile);
        }//End for
		
	}//End renameFiles
	
	
}//End prepareSubmission
