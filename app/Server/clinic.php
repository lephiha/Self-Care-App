<?php
    require_once('connection.php');
    $stmt = $conn->prepare('SELECT *
                            FROM clinic');
    $stmt->execute();
    $stmt->bind_result($clinic_id,$clinic_name,$address,$clinic_image,$chief_id,$latitude,$longitude);

    $clinic_array = array();
    while($stmt->fetch()){
        $temp = array();
        $temp['clinic_id'] = $clinic_id;
        $temp['clinic_name'] = $clinic_name;
        $temp['address'] = $address;
        $temp['clinic_image'] = $clinic_image;
        $temp['chief_id'] = $chief_id;
        $temp['latitude'] = $latitude;
        $temp['longitude'] = $longitude;
        array_push($clinic_array,$temp);
    }

    echo json_encode($clinic_array);

?>