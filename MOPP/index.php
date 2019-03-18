#!/usr/bin/php-cgi
<?php
include('login.php');
?>
 <?php include('errors.php'); ?>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Login</title>
    <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
      <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</head>
<body>

  <div class="formFields">
    <img src="images/logo.PNG" alt="logo">
<form method="post" action="index.php">

        <input type="text" name="username" placeholder="username" value="<?php echo $username; ?>"/><br/>
        <input type="password" name="password" placeholder="password"><br/>
        <button type="button" class="btn btn-danger">Enter</button>
</form>
</div>
</body>
</html>