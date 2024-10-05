<?php
    require_once('connection.php');
    $chief_id = $_GET['chief_id'];
    $spe_id = $_GET['spe_id'];
    $stmt2 = $conn->prepare("SELECT D.docid, D.docemail, D.docname, D.specialties, S.sname, A.description, D.sex, D.price
                            from doctor as D, specialties as S, academicrank as A
                            where D.specialties = '$spe_id' and D.chief_id = '$chief_id'
                            and D.academic_rank = A.id and D.specialties = S.id" 
                            );
    $stmt2->execute();
    $stmt2->bind_result($doc_id,$docemail,$docname,$specialties, $sname,$academic_rank,$sex,$price);
    $doctor_array = array();
    while($stmt2->fetch()){
        $temp = array();
        $temp['doc_id'] = $doc_id;
        $temp['docemail'] = $docemail;
        $temp['docname'] = $docname;
        $temp['specialties'] = $specialties;
        $temp['sname'] = $sname;
        $temp['academic_rank'] = $academic_rank;
        $temp['sex'] = $sex;
        $temp['price'] = $price;
        array_push($doctor_array, $temp);
    }

    echo json_encode($doctor_array);
?>