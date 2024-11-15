<?php
    require_once("connection.php");
    $pid = $_GET['pid'];
    $stmt = $conn->prepare("SELECT V.docid, V.docAvailable, V.paid, D.docname, S.sname, A.description,D.price,D.sex,D.docemail
                            FROM videocall as V, doctor as D, academicrank as A, specialties as S
                            WHERE pid = '$pid' and V.docid = D.docid and S.id = D.specialties and D.academic_rank = A.id");

    $stmt->execute();
    $stmt->bind_result($docid,$docAvailable,$paid,$docname,$sname,$acacdemicrank,$price,$sex,$docemail);
    $doctor_array = array();
    while($stmt->fetch()){
        $temp = array();
        $temp['docid'] = $docid;
        $temp['docAvailable'] = $docAvailable;
        $temp['paid'] = $paid;
        $temp['docname'] = $docname;
        $temp['sname'] = $sname;
        $temp['acacdemicrank'] = $acacdemicrank;
        $temp['price'] = $price;
        $temp['sex'] = $sex;
        $arr = explode("@", $docemail);
        $call_id = $arr[0];
        $temp['call_id'] = $call_id;
        array_push($doctor_array,$temp);
    }

    echo json_encode($doctor_array);
?>