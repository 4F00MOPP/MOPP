#!/usr/bin/php-cgi
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
<?php
include('login.php');
?>
  <div class="formFields">
<form method="post" action="index.php">
    <?php include('errors.php'); ?>
        <input type="text" name="username" placeholder="username" value="<?php echo $username; ?>"/><br/>
        <input type="password" name="password" placeholder="password"><br/>
        <input type="submit" name="login" value="Login">
</form>
</div>
</body>
</html>