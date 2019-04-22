#!/usr/bin/php-cgi

<?php


$testArr = array();

for ($i = 1; $i <= 5; $i++) {
	for ($j = 1; $j <= 5; $j++) {
		$testArr[$i][$j] = $i + $j;
	}//End for
}//End for

for ($i = 1; $i <= 5; $i++) {
	for ($j = 1; $j <= 5; $j++) {
		echo ($testArr[$i][$j] . ", ");
	}//End for
	echo ("<br>");
}//End for


?>