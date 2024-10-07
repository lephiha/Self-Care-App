<?php
    require_once("connection.php");
    $pid = $_GET["pid"];
    $stmt = $conn->prepare("SELECT A.appoid, D.docname,A.hasDone, A.scheduledate, A.starttime, A.endtime
                            FROM appointment as A, doctor as D
                            WHERE pid = $pid and A.docid = D.docid");
    $stmt->execute();
    $stmt->bind_result($appoid,$docname,$hasDone,$scheduledate,$starttime,$endtime);

    $appo_array = array();
    while($stmt->fetch()){
        $temp = array();
        $temp['appoid'] = $appoid;
        $temp['docname'] = $docname;
        $temp['hasDone'] = $hasDone;
        $temp['scheduledate'] = $scheduledate;
        $temp['starttime'] = $starttime;
        $temp['endtime'] = $endtime;
        array_push($appo_array,$temp);
    }

    echo json_encode($appo_array);
?>