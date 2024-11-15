<?php
    date_default_timezone_set('Asia/Ho_Chi_Minh');
    $conn = mysqli_connect("localhost","root", "","edoc");
    if($conn->connect_error){
        die("Connection failed:".$conn->connect_error);
    }
?>