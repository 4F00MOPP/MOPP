#!/usr/bin/php-cgi

<!DOCTYPE html>
<html lang="en-us">
 
<head>
<meta charset="utf-8">
<title>Professor Submission</title>
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
	<form action="selectFile.php" method="post" enctype="multipart/form-data">
		Select Files to Process:
		<input type="submit" value="Select Files" name="submit">
	</form>
<?php
}//End if
?>

</body>
</html>
