<?php
    require_once("connection.php");
    $curr_date = date('Y-m-d');
    $curr_time = date("H:i:s");
    $sql = "UPDATE appointment
            set hasDone = 1 
            WHERE scheduledate < '$curr_date' 
            OR (scheduledate = '$curr_date' AND endtime < '$curr_time')";
    $conn->query($sql);
    $conn->close();
    
    
?>