#!/usr/bin/php-cgi

<?php


//Parameters
$target_dir = "uploads/";		//Upload Directory
$fileSize_Cap = 1000000000;  	//Limit for an uploaded fize's size
$fileType_Accepted = array("zip");		//Accepted file types

//Checks
$uploadOk = 1;
$check_FileExists = true;
$check_FileSize = true;
$check_FileType = true;

$username = $_POST["username"];
$assNum = $_POST["assNum"];
$courseCode = $_POST["courseCode"];

echo($username);
echo($assNum);
echo($courseCode);

$target_dir = $target_dir . $courseCode . "_" . $assNum . "_" . $username . "/";

$target_file = $target_dir . basename($_FILES["fileToUpload"]["name"]);
$fileType = strtolower(pathinfo($target_file,PATHINFO_EXTENSION));

echo('<br>');
echo($target_file);
echo('<br>');

//If the file already exists
if (file_exists($target_dir)) {
    $check_FileExists = false;
}//End if

// Check file size
if ($_FILES["fileToUpload"]["size"] > $fileSize_Cap) {
    $check_FileSize = false;
}//End if

// Only allowing Zip files
$check_FileType = false;
if ($fileType ==  $fileType_Accepted[0]) {
	$check_FileType = true;
}//End if

// Check if $uploadOk is set to 0 by an error
if (!$check_FileExists || !$check_FileSize || !$check_FileType) {
    echo "File was not uploaded due to the following: \n";
	
	if (!$check_FileExists) echo "A submission has already been made for this assignment and account.";
	if (!$check_FileSize) echo "File size too big.";
	if (!$check_FileType) echo "File must be in ZIP format.";

	
// if everything is ok, try to upload file
} else {
	mkdir($target_dir);
    if (move_uploaded_file($_FILES["fileToUpload"]["tmp_name"], $target_file)) {
		$zip = new ZipArchive;
		$res = $zip->open($target_file);
		if ($res === TRUE) {
			$zip->extractTo($target_dir);
			$zip->close();
			unlink($target_file);
			echo "The file ". basename( $_FILES["fileToUpload"]["name"]). " has been uploaded.";
		} else {
			echo 'Sorry, there was an error unzipping your file.';
		}//End if
    } else {
        echo "Sorry, there was an error uploading your file.";
    }//End if
}//End if

?>