<?php
   $con=mysqli_connect("mydbinstance.cvafadxwm3ta.us-west-2.rds.amazonaws.com","cav7157","codered21","CompanionAppDB");

   if (mysqli_connect_errno($con)) {
      echo "Failed to connect to MySQL: " . mysqli_connect_error();
   }
	
   $username = $_POST['username'];
   $password = $_POST['password'];
   $result = mysqli_query($con,"SELECT * FROM tbl_login where 
   email ='$username' and password='$password'");
   $row = mysqli_fetch_array($result);
   $data = $row[0];

   if($data){
      echo $data;
   }
	
   mysqli_close($con);
?>
