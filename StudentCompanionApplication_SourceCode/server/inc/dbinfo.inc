<?php

$servername = "mydbinstance.cvafadxwm3ta.us-west-2.rds.amazonaws.com";
$username = "cav7157";
$password = "codered21";
$dbname = "companionapp";

try {
    	$conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    	$conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    }
catch(PDOException $e)
    {
    	die($e);
    }

?>
