
<?php
try
{
	/*
	$servername = "mydbinstance.cvafadxwm3ta.us-west-2.rds.amazonaws.com";
	$username = "cav7157";
	$password = "codered21";*/
	$dbname = "CompanionAppDB";
	
	require "../inc/dbinfo.inc";

	$sql = "Select * from Transcript;";
	//$conn = mysqli_connect($servername,$username,$password,$dbname);
	//$result = mysqli_query($con,$sql);
        $result = $conn->query($sql);
        

	$response = array();
	//while($row = mysqli_fetch_array($result))
        while ($row = $result->fetch(PDO::FETCH_BOTH))
	{
               // $arr = array("Student_ID"=>$row[1],"Course_Code"=>$row[2], "Grade"=>$row[3]);
		$arr = array("Course_Code"=>$row[2], "Grade"=>$row[3]);
		array_push($response, $arr);
	}

	echo json_encode(array("transcript"=>$response));
	mysql_close($conn);
} 
catch (Exception $e) 
{
  	echo "Error: \n";
  	echo $e->getMessage();
}

?>
