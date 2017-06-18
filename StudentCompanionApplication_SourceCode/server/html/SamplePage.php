<?php include "../inc/dbinfo.inc"; ?>
<html>
<body>
<h1>Student Companion Application</h1>
<title> PHP Sample Page </title>
<?php

     	 
	 // Check whether username or password is set from android	
     if(isset($_POST['username']) && isset($_POST['password']))
     {
		  // Initialize Variable
		  $result='';
	   	  $username = $_POST['username'];
          $password = $_POST['password'];
		  
		  // Query database for row exist or not
          $sql = 'SELECT * FROM Login WHERE  email = :username AND password = :password';
          $stmt = $conn->prepare($sql);
          $stmt->bindParam(':username', $username, PDO::PARAM_STR);
          $stmt->bindParam(':password', $password, PDO::PARAM_STR);
          $stmt->execute();
          if($stmt->rowCount())
          {
			 $result="true";	
          }  
          elseif(!$stmt->rowCount())
          {
			  	$result="false";
          }
		  
		  // send result back to android
   		  echo $result;
  	}
	
?>
</body>
</html>

