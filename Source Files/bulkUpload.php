#!/usr/bin/php-cgi

<?php

echo "<link rel='stylesheet' type='text/css' href='./main.css'>";
echo "<link href='./bootstrap/css/bootstrap.min.css' rel='stylesheet'>";
echo "<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'>";

if (isset($_FILES["fileToUpload"]["name"])) {
    $name       = $_FILES['fileToUpload']['name'];  
    $temp_name  = $_FILES['fileToUpload']['tmp_name'];  
    if(isset($name)){
        if(!empty($name)){      
            $location = 'bulkUpload/';     
			$submissionName = basename($_FILES["fileToUpload"]["name"], ".zip");
            if(move_uploaded_file($temp_name, $location.$name)){
                //echo 'File uploaded successfully';
				
				$zip = new ZipArchive;
				$res = $zip->open($location.$name);
				if ($res === TRUE) {
					$zip->extractTo($location);
					$zip->close();
					unlink($location.$name);
					echo "<p class='lead'>The file ". basename( $_FILES["fileToUpload"]["name"]). " has been uploaded.</p>";

				} else {
					echo '<p class="lead">Sorry, there was an error unzipping your file.</p>';
				}//End if
				
				
            }
        }       
    }  else {
        echo '<p class="lead">You should select a file to upload !!</p>';
    }
} else {
	echo "hi";
}



//$target_dir = "bulkUpload/";		//Upload Directory
//$target_file = $target_dir . basename($_FILES["fileToUpload"]["name"]);


//$testSet_Name = basename($_FILES["fileToUpload"]["name"];

//$username = $_POST["username"];
//$assNum = $_POST["assNum"];
//$courseCode = $_POST["courseCode"];
//$stuNum = $_POST["stuNum"];


//$target_dir = $target_dir . basename($_FILES["fileToUpload"]["name"] . "/";

//$fileType = strtolower(pathinfo($target_file,PATHINFO_EXTENSION));

//mkdir($target_dir);

echo "<form class='form-submit'> 
    <a href='#' onclick='javascript:window.history.go(-1);return false;'>
    <button class='btn btn-block btn-secondary mt-2 mx-auto' name='logout' type='button'>Back</button>
    </a>
    </form>";

?>
