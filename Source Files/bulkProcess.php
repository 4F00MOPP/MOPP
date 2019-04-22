#!/usr/bin/php-cgi

<?php

echo "<link rel='stylesheet' type='text/css' href='./main.css'>";
echo "<link href='./bootstrap/css/bootstrap.min.css' rel='stylesheet'>";
echo "<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'>";

//Parameters
$uploads_dir = "bulkUpload/";		//File Directory
$target_dir = "process/";		//Processing Directory
//Function to Recursivly copy one Directory to another location
function recurse_copy($src,$dst) { 
    $dir = opendir($src); 
    @mkdir($dst); 
    while(false !== ( $file = readdir($dir)) ) { 
        if (( $file != '.' ) && ( $file != '..' )) { 
            if ( is_dir($src . '/' . $file) ) { 
                recurse_copy($src . '/' . $file,$dst . '/' . $file); 
            } 
            else { 
                copy($src . '/' . $file,$dst . '/' . $file); 
            } 
        } 
    } 
    closedir($dir); 
}
if (empty($_POST["files"])){
	echo("<p class='lead'>No files selected</p>");
} else {
	//Getting the Selected files
	$selectedFiles = $_POST["files"];
	//Determining the Destination
	$existingFolders = scandir($target_dir);
	$numFolders = count($existingFolders);
	
	//Getting a random "processing" ID
	$target_Folder = "BULK";
	
	//Making the Directory
	mkdir($target_dir.$target_Folder , 0701);
	
	//Getting the files in the directory
	$uploadedFiles = scandir($uploads_dir);
	
	//Moving the files to process
	$numSelected = count($selectedFiles);
	
	echo "<div class='lead'>Submission Successful</div>";
	echo "<div class='lead'>Submission ID: " . $target_Folder . "</div>";
	echo "<div class='lead'>The following files have been submitted:</div>";

	for ($i = 0; $i < $numSelected; $i++) {
		$fileName = basename($uploadedFiles[$selectedFiles[$i]]);
		echo("<div class='lead'>" . $fileName. "</div>");
		//copy($uploads_dir.$fileName, $target_dir.$target_Folder."/".$fileName);
		recurse_copy($uploads_dir.$fileName, $target_dir.$target_Folder."/".$fileName);
	}//End for
	
	
	//echo "<form action='professor_classView.php' method='post'>";
echo "<form class='form-submit'> 
    <a href='#' onclick='javascript:window.history.go(-3);return false;'>
    <button class='btn btn-block btn-secondary mt-2 mx-auto' name='logout' type='button'>Back</button>
    </a>
    </form>";
	//echo "</form>";
	
	//In an ideal world, this would work
	//exec("java -Xmx2048M StartAlgorithm abc");
	
	//Instead we need to rely on the Java program already running...
	
}//End if
	
?>
