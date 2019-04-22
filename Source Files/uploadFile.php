#!/usr/bin/php-cgi

<?php

echo "<link rel='stylesheet' type='text/css' href='./main.css'>";
echo "<link href='./bootstrap/css/bootstrap.min.css' rel='stylesheet'>";
echo "<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'>";

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
$stuNum = $_POST["stuNum"];
//echo($username);
//echo($assNum);
//echo($courseCode);
$target_dir = $target_dir . $courseCode . "_" . $assNum . "_" . $username . "_" . $stuNum . "/";
$target_file = $target_dir . basename($_FILES["fileToUpload"]["name"]);
$fileType = strtolower(pathinfo($target_file,PATHINFO_EXTENSION));
///echo('<br>');
//echo($target_file);
//echo('<br>');
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
//echo($fileType);
if ($fileType ==  $fileType_Accepted[0]) {
	$check_FileType = true;
}//End if
// Check if $uploadOk is set to 0 by an error
if (!$check_FileExists || !$check_FileSize || !$check_FileType) {
    echo "<p class='lead'> File was not uploaded due to the following: \n </p>";
	
	if (!$check_FileExists) echo "<p class='lead'> A submission has already been made for this assignment and account. </p>";
	if (!$check_FileSize) echo "<p class='lead'>File size too big. </p>";
	if (!$check_FileType) echo "<p class='lead'>File must be in ZIP format.</p>";
	
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
			echo "<p class='lead'>The file ". basename( $_FILES["fileToUpload"]["name"]). " has been uploaded. </p>";
		} else {
			echo "<p class='lead'>Sorry, there was an error unzipping your file.</p>";
		}//End if
    } else {
        echo "<p class='lead'>Sorry, there was an error uploading your file.</p>";
    }//End if
}//End if

echo "<form class='form-submit'> 
    <a href='#' onclick='javascript:window.history.go(-2);return false;'>
    <button class='btn btn-block btn-secondary mt-2 mx-auto' name='logout' type='button'>Back</button>
    </a>
    </form>";
?>
