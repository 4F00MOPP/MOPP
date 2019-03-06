#!/usr/bin/php-cgi

<?php


//Parameters
$target_dir = "uploads/";		//Upload Directory
$fileSize_Cap = 1000000000;  	//Limit for an uploaded fize's size
$fileType_Accepted = array("zip","7z","rar","tar");		//Accepted file types

//Checks
$uploadOk = 1;
$check_FileExists = true;
$check_FileSize = true;
$check_FileType = true;


$target_file = $target_dir . basename($_FILES["fileToUpload"]["name"]);
$fileType = strtolower(pathinfo($target_file,PATHINFO_EXTENSION));

//If the file already exists
if (file_exists($target_file)) {
    $check_FileExists = false;
}//End if

// Check file size
if ($_FILES["fileToUpload"]["size"] > $fileSize_Cap) {
    $check_FileSize = false;
}//End if

// Allow certain file formats
$check_FileType = false;
for ($i = 0; $i < count($fileType_Accepted); $i++){
	if ($fileType ==  $fileType_Accepted[$i]) {
		$check_FileType = true;
		break;
	}//End if
}//End for

// Check if $uploadOk is set to 0 by an error
if (!$check_FileExists || !$check_FileSize || !$check_FileType) {
    echo "File was not uploaded due to the following: \n";
	
	if (!$check_FileExists) echo "File already exists in the destination.";
	if (!$check_FileSize) echo "File size too big.";
	if (!$check_FileType) echo "File type invalid.";

	
// if everything is ok, try to upload file
} else {
    if (move_uploaded_file($_FILES["fileToUpload"]["tmp_name"], $target_file)) {
        echo "The file ". basename( $_FILES["fileToUpload"]["name"]). " has been uploaded.";
    } else {
        echo "Sorry, there was an error uploading your file.";
    }
}

?>