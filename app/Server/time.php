<?php
    require_once('connection.php');
    $docid = $_GET['docid'];
    $date = $_GET['date'];
    $stmt = $conn->prepare("SELECT scheduleid, starttime, endtime, booked
                            FROM schedule
                            WHERE docid = $docid and scheduledate = '$date'
                            ORDER BY starttime ASC");
    $stmt->execute();
    $stmt->bind_result($schedule_id, $start_time, $end_time, $booked);
    $time = array();
    while($stmt->fetch()){
        $temp = array();
        $temp['schedule_id'] = $schedule_id;
        $temp['start_time'] = $start_time;
        $temp['end_time'] = $end_time;
        $temp['booked'] = $booked;
        array_push($time,$temp);
    }

    echo json_encode($time);
?>