<?php
    require_once('connection.php');
    $pid = $_GET['pid'];
    $stmt = $conn->prepare("SELECT R.scheduledate, P.pname, D.docname, C.clinic_name, C.address
                            FROM reschedule as R, patient as P, doctor as D, clinic as C
                            WHERE R.pid = $pid 
                            and R.pid = P.pid
                            and D.docid = R.docid
                            and D.chief_id = C.chief_id");
    $stmt->execute();
    $stmt->bind_result($scheduledate, $pname, $docname, $clinicname, $address);

    $spe_array = array();
    while($stmt->fetch()){
        $temp = array();
        $temp['scheduuledate'] = $scheduledate;
        $temp['pname'] = $pname;
        $temp['docname'] = $docname;
        $temp['clinicname'] = $clinicname;
        $temp['address'] = $address;
        array_push($spe_array,$temp);
    }
    echo json_encode($spe_array);
?>