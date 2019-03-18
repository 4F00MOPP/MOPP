<!DOCTYPE html>
<head>
    <title> MOPP Login </title>
    <link href="" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/styles.css"/>
</head>

<body>
<?php
session_start();
require_once('db.php');
$idletime=500;
$errorArray=array();
$pwd="";
$username="";
$_SESSION['active']='';
if(isset($_POST['login'])){
$username=($_POST['username']);
$pwd=($_POST['password']);
    if(empty($username)){
        array_push($errorArray,"you must enter a username");
    }
    if(empty($pwd)){
        array_push($errorArray,"you must enter a password");
    }
     //if no errors in login form
        if (count($errorArray) == 0) {
            $password = md5($pwd);
            $query = "SELECT * from userList WHERE name='$username'AND password='$password'";
            $result = mysqli_query($db, $query);
            $row = mysqli_fetch_row($result);

            if (mysqli_num_rows($result) == 1) {
                $_SESSION['username'] = $username;
                $_SESSION['active'] = TRUE;
                $_SESSION['timestamp'] = time();

                if($_SESSION['active']==TRUE){
                    if($row[3]=='prof'){
                        header('location: prof.html');
                    }
                    if($row[3]=='student'){
                        header('location: student.html');
                    }
                }

            } else {
                session_destroy();
                array_push($errorArray, "The username/password is incorrect! Try Again");
            }
        }

}

?>
</body>
</html>