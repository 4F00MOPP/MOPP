#!/usr/bin/php-cgi

<?php


echo (ini_get("max_execution_time"));
echo ("<br>");
echo (ini_get("max_input_time"));
echo ("<br>");
echo (ini_get("post_max_size"));
echo ("<br>");
echo (ini_get("upload_max_filesize"));

?>