<?php
    require_once('connection.php');
    $start_time = array("7:00:00","8:00:00","9:00:00","10:00:00","11:00:00","13:00:00","14:00:00","15:00:00");
    $end_time = array("8:00:00","9:00:00","10:00:00","11:00:00","12:00:00","14:00:00","15:00:00","16:00:00");
    $stmt = $conn->prepare('SELECT docid
                            FROM doctor');
    $stmt->execute();
    $result= $stmt->get_result();
    while ($row = $result->fetch_assoc()) {
        $docid = $row['docid'];
        $curr_date = date('Y-m-d');
        $index = 0;
        while ($curr_date < date('Y-m-d', strtotime('+7 days'))) {
            $i = 0;
            while($i< count($start_time)){
                $sql = "INSERT INTO schedule (scheduleid, docid, scheduledate, starttime, endtime, booked)
                    VALUES (NULL, $docid, '$curr_date', '$start_time[$i]', '$end_time[$i]', false)";
                // print($sql); 
                // print("\n");
                $conn->query($sql);
                $i++;
            }
            $index++;
            $curr_date = date('Y-m-d', strtotime("+$index days")); // Increment date
        }
    }
?>