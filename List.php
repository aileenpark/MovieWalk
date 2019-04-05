<?php
      $con = mysqli_connect("localhost","keeka2","qwer321!","keeka2");
      $result = mysqli_query($con,"select * from DB;");
      $response = array();

      while($row = mysqli_fetch_array($result)){
                array_push($response, 
                array('id'=>$row[0],
                'name'=>$row[1],
                'title'=>$row[2],
                'latitude'=>$row[3],
                'longitude'=>$row[4],
                'address'=>$row[5],
                'image1'=>$row[6],
                'image2'=>$row[7],
                'information'=>$row[8]
            ));
      }
       header('Content-Type: application/json; charset=utf8');
      echo json_encode(array("response"=>$response), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
      mysqli_close($con);
?>