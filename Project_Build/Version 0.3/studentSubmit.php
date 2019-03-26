#!/usr/bin/php-cgi

<!DOCTYPE html>
<html lang="en-us">
 
<head>
<meta charset="utf-8">
<title>Student Submission</title>
</head>

<body>
<?php

$username = $_POST['username'];
$password = $_POST["password"];

echo('<div>');
	echo('Validate the following Login: <br>');
	echo($username . '<br>');
	echo($password . '<br>');
echo('</div>');
echo('<br>');

//Validating the login
$validation = true;
if ($validation == false) {
?>
	Invalid login
<?php
} else {
?>	
	<form action="uploadFile.php" method="post" enctype="multipart/form-data">
		Course Code: 
		<input type="text" name="courseCode" id="courseCode">
		
		Assignment Number:
		<input type="text" name="assNum" id="assNum">
		
		<input type="hidden" name="username" id="username" value="<?php echo($username); ?>">
		
		Select file to upload:
		<input type="file" name="fileToUpload" id="fileToUpload">
		<input type="submit" value="Upload File" name="submit">
	</form>
<?php
}//End if
?>

</body>
</html>