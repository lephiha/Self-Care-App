<?php
    require_once("connection.php");
    $image = $_POST['image'];
    $appoid = $_POST['appoid'];
    $sql="UPDATE appointment
          SET diagImage = '$image'
          WHERE appoid = $appoid";
    $conn->query($sql);
    $array = array("result" => "Success");
    echo json_encode($array);
    $conn->close();
?>