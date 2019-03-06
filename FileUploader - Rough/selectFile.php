#!/usr/bin/php-cgi

<?php
//Parameters
$target_dir = "uploads/";		//Upload Directory

//Getting all the files in the directory
$files = scandir($target_dir);

//Creating the Form
echo('<form action="processFile.php" method="post">');
for ($i = 0; $i < count($files); $i++){
	echo('<input type="checkbox" name="files[]" value="'.$i.'"> '.basename($files[$i]).'<br>');
}//End for
echo('<input type="submit" value="Submit">');

?>