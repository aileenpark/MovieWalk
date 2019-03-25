<?php
	$con = mysqli_connect("localhost","keeka2","qwer321!","keeka2");
 
            define('UPLOAD_PATH', '/uploads/');

	$target_dir = '/uploads/';

	$name = $_POST["name"];
	$title = $_POST["title"];
	$latitude = $_POST["latitude"];
	$longitude = $_POST["longitude"];
	$address = $_POST["address"];

	$target_file1 = $target_dir . basename($_FILES["image1"]["name"]);
	$target_file2 = $target_dir . basename($_FILES["image2"]["name"]);

            $file1 = $_FILES['image1']['tmp_name'];
            $file2 = $_FILES['image2']['tmp_name'];

	$information = $_POST["information"];

	$imageURL1 = 'http://keeka2.cafe24.com' . $target_file1;
	$imageURL2 = 'http://keeka2.cafe24.com' . $target_file2;

	$file1_path = 'http://keeka2.cafe24.com' . $_FILES["image1"]["name"];
	$file2_path = 'http://keeka2.cafe24.com' . $_FILES["image2"]["name"];


	$uploadOk = 1;

	$imageFileType1 = pathinfo($target_file1,PATHINFO_EXTENSION);
	$imageFileType2 = pathinfo($target_file2,PATHINFO_EXTENSION);

	// Check if image file is a actual image or fake image
	if(isset($_POST["submit"])) {
   	 $check1 = getimagesize($_FILES["image1"]["tmp_name"]);
	 $check2 = getimagesize($_FILES["image2"]["tmp_name"]);

  	  if($check1 !== false && $check2 !==false) {
 	       echo "File is an image - " . $check["mime"] . ".";
 	       $uploadOk = 1;
 	   } else {
        	echo "File is not an image.";
       	 $uploadOk = 0;
   	 }
	}

	// Check if file already exists

	if (file_exists($target_file1) || file_exists($target_file2)) {
    	echo "Sorry, file already exists.";
    	$uploadOk = 0;
	}

	// Check file size

	if ($_FILES["image1"]["size"] > 5000000 || $_FILES["image2"]["size"] > 5000000) {
  	  echo "Sorry, your file is too large.";
  	  $uploadOk = 0;
	}

	// Allow certain file formats

	if($imageFileType1 != "jpg" && $imageFileType1 != "png" && $imageFileType1 != "jpeg"
	&& $imageFileType1 != "gif" ) {
 	   echo "Sorry, only JPG, JPEG, PNG & GIF files are allowed.";
	    $uploadOk = 0;
	}

	if($imageFileType2 != "jpg" && $imageFileType2 != "png" && $imageFileType2 != "jpeg"
	&& $imageFileType2 != "gif" ) {
 	   echo "Sorry, only JPG, JPEG, PNG & GIF files are allowed.";
	    $uploadOk = 0;
	}

	// Check if $uploadOk is set to 0 by an error

             $filedest1 = dirname(__FILE__) . UPLOAD_PATH . $_FILES["image1"]["name"];
             $filedest2 = dirname(__FILE__) . UPLOAD_PATH . $_FILES["image2"]["name"];

	if ($uploadOk == 0) {
 	   echo "Sorry, your file was not uploaded.";

	// if everything is ok, try to upload file

	} else {

        move_uploaded_file($file1, $filedest1);

        move_uploaded_file($file2, $filedest2);


         }


 	$statement = mysqli_prepare($con, "INSERT INTO DB (name, title, latitude, longitude, address, image1, image2, information) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
 	mysqli_stmt_bind_param($statement, "ssssssss", $name, $title, $latitude, $longitude, $address, $imageURL1, $imageURL2, $information);
 	mysqli_stmt_execute($statement);
 
 	$response = array();
	 $response["success"] = true;
  
 	echo json_encode($response);




 ?>