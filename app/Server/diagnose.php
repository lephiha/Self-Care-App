<?php
    require_once("connection.php");
    $pid = $_GET['pid'];
    $stmt = $conn->prepare("SELECT A.appoid, P.pname,D.docname,A.hasDone, A.scheduledate, A.starttime, A.endtime, A.diagImage
                            FROM appointment as A, doctor as D, patient as P
                            WHERE A.pid = $pid and A.docid = D.docid and A.pid = P.pid and A.diagImage is not null");
    $stmt->execute();
    $stmt->bind_result($appoid,$pname,$docname,$hasDone,$scheduledate,$starttime,$endtime,$image);

    $appo_array = array();
    while($stmt->fetch()){
        $temp = array();
        $temp['appoid'] = $appoid;
        $temp['pname'] = $pname;
        $temp['docname'] = $docname;
        $temp['hasDone'] = $hasDone;
        $temp['scheduledate'] = $scheduledate;
        $temp['starttime'] = $starttime;
        $temp['endtime'] = $endtime;
        $temp['image'] = $image;
        array_push($appo_array,$temp);
    }

    echo json_encode($appo_array);
?>