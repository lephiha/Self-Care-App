<?php
    require_once("connection.php");
    $pid = $_GET['pid'];
    $docid = $_GET['docid'];
    $date = $_GET['date'];
    $sql="INSERT INTO reschedule (id,pid,docid, scheduledate)
        VALUES (NULL, $pid, $docid, '$date') ";
    $conn->query($sql);
    $array = array("result" => "Success");
    echo json_encode($array);
    $conn->close();
?>