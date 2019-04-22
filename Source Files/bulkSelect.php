#!/usr/bin/php-cgi

<?php

echo "<link rel='stylesheet' type='text/css' href='./main.css'>";
echo "<link href='./bootstrap/css/bootstrap.min.css' rel='stylesheet'>";
echo "<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'>";

//Parameters
$target_dir = "bulkUpload/";		//Upload Directory
//Getting all the files in the directory
$files = scandir($target_dir);
//Creating the Form
echo('<form class="form-submit files" action="bulkProcess.php" method="post">');
for ($i = 0; $i < count($files); $i++){
	
	if (!in_array($files[$i], array(".", ".."))) {
		echo('<input type="radio" name="files[]" value="'.$i.'"required> '.basename($files[$i]).'<br>');
	}
	
	
}//End for
echo('
<button class="btn btn-block btn-secondary mt-2 mx-auto" type="submit" value="Submit"> Submit </button">
');
?>