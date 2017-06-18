
<?php
try
{
	/*
	$servername = "mydbinstance.cvafadxwm3ta.us-west-2.rds.amazonaws.com";
	$username = "cav7157";
	$password = "codered21";*/
	$dbname = "CompanionAppDB";
	
	require "../inc/dbinfo.inc";

	$sql = "Select * from TrackID Where TrackDesc = 'Database_Admin';";

	//$conn = mysqli_connect($servername,$username,$password,$dbname);
	//$result = mysqli_query($con,$sql);


        $result = $conn->query($sql);
        

	$response = array();
	//while($row = mysqli_fetch_array($result))
        while ($row = $result->fetch(PDO::FETCH_BOTH))
	{
                $arr = array("TrackDesc"=>$row[1]);
		array_push($response, $arr);
	}

	echo json_encode(array("tracks_response"=>$response));
	mysql_close($conn);
} 
catch (Exception $e) 
{
  	echo "Error: \n";
  	echo $e->getMessage();
}

?>
