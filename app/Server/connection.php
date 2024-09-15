<?php
    $conn = mysqli_connect("localhost","root", "","edoc");
    if($conn->connect_error){
        die("Connection failed:".$conn->connect_error);
    }
?>