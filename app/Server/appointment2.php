<?php
    require_once("connection.php");
    $docid = $_GET["docid"];
    $stmt = $conn->prepare("SELECT A.appoid,A.pid, P.pname,D.docname, A.hasDone, A.scheduledate, A.starttime, A.endtime
                            FROM appointment as A, patient as P, doctor as D
                            WHERE A.docid = $docid and A.pid = P.pid and D.docid = A.docid");
    $stmt->execute();
    $stmt->bind_result($appoid,$pid,$pname,$docname,$hasDone,$scheduledate,$starttime,$endtime);

    $appo_array = array();
    while($stmt->fetch()){
        $temp = array();
        $temp['appoid'] = $appoid;
        $temp['pid'] = $pid;
        $temp['pname'] = $pname;
        $temp['docname'] = $docname;
        $temp['hasDone'] = $hasDone;
        $temp['scheduledate'] = $scheduledate;
        $temp['starttime'] = $starttime;
        $temp['endtime'] = $endtime;
        array_push($appo_array,$temp);
    }

    echo json_encode($appo_array);
?>