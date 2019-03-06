#!/usr/bin/php-cgi

<?php
//Parameters
$uploads_dir = "uploads/";		//File Directory
$target_dir = "process/";		//Processing Directory

if (empty($_POST["files"])){
	echo("No files selected");
} else {

	//Getting the Selected files
	$selectedFiles = $_POST["files"];

	//Determining the Destination
	$existingFolders = scandir($target_dir);
	$numFolders = count($existingFolders);
	
	//Getting a random "processing" ID
	$target_Folder = rand(1000,9999);
	while(true) {
		$nameTakenCheck = true;
		for ($i = 0; $i < $numFolders; $i++){
			if (basename($existingFolders[$i]) == $target_Folder) {
				$nameTakenCheck = false;
			}//End if
		}//End for
		
		//Name is available
		if ($nameTakenCheck) {
			break;
		}//End if
		
		//Name is not available
		$target_Folder++;
		if ($target_Folder > 9999){
			$target_Folder = 0;
		}//End if
	}//End while
	
	//Making the Directory
	mkdir($target_dir.$target_Folder , 0701);
	
	//Getting the files in the directory
	$uploadedFiles = scandir($uploads_dir);
	
	//Moving the files to process
	$numSelected = count($selectedFiles);
	for ($i = 0; $i < $numSelected; $i++) {
		$fileName = basename($uploadedFiles[$selectedFiles[$i]]);
		echo($fileName."    ");
		copy($uploads_dir.$fileName, $target_dir.$target_Folder."/".$fileName);
	}//End for
	
	//TO DO
	//
	//initialize the Java Program, passing it the Directory of the files
	//Validate it runs
	
	exec("java -Xmx2048M StartAlgorithm abc");
	
}//End if
	

?>