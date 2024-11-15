<?php
    require_once("connection.php");
    $pid = $_GET["pid"];
    $stmt = $conn->prepare("SELECT A.appoid,A.docid, P.pname,D.docname,A.hasDone, A.scheduledate, A.starttime, A.endtime, C.clinic_name,C.address
                            FROM appointment as A, doctor as D, patient as P, clinic as C
                            WHERE A.pid = $pid 
                            and A.docid = D.docid 
                            and A.pid = P.pid
                            and D.chief_id = C.chief_id");
    $stmt->execute();
    $stmt->bind_result($appoid,$docid,$pname,$docname,$hasDone,$scheduledate,$starttime,$endtime,$clinic_name,$address);

    $appo_array = array();
    while($stmt->fetch()){
        $temp = array();
        $temp['appoid'] = $appoid;
        $temp['docid'] = $docid;
        $temp['pname'] = $pname;
        $temp['docname'] = $docname;
        $temp['hasDone'] = $hasDone;
        $temp['scheduledate'] = $scheduledate;
        $temp['starttime'] = $starttime;
        $temp['endtime'] = $endtime;
        $temp['clinic_name'] = $clinic_name;
        $temp['address'] = $address;
        array_push($appo_array,$temp);
    }

    echo json_encode($appo_array);
?>