<?php
    require_once('connection.php');
    $stmt = $conn->prepare('SELECT *
                            FROM specialties');
    $stmt->execute();
    $stmt->bind_result($id, $sname);

    $spe_array = array();
    while($stmt->fetch()){
        $temp = array();
        $temp['id'] = $id;
        $temp['sname'] = $sname;
        array_push($spe_array,$temp);
    }
    echo json_encode($spe_array);
    $conn->close();
?>