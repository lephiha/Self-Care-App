<?php
require_once("./config.php");
require_once("connection.php");
            $vnp_SecureHash = $_GET['vnp_SecureHash'];
            $inputData = array();
            foreach ($_GET as $key => $value) {
                if (substr($key, 0, 4) == "vnp_") {
                    $inputData[$key] = $value;
                }
            }
            $order_info = (string ) $_GET['vnp_OrderInfo'];
            $array = explode(" ", $order_info);
            $pid = $array[0];
            $docid = $array[1];
            $scheduleid = $array[2];
            unset($inputData['vnp_SecureHash']);
            ksort($inputData);
            $i = 0;
            $hashData = "";
            foreach ($inputData as $key => $value) {
                if ($i == 1) {
                    $hashData = $hashData . '&' . urlencode($key) . "=" . urlencode($value);
                } else {
                    $hashData = $hashData . urlencode($key) . "=" . urlencode($value);
                    $i = 1;
                }
            }
    
            $secureHash = hash_hmac('sha512', $hashData, $vnp_HashSecret);
            if ($secureHash == $vnp_SecureHash) {
                if ($_GET['vnp_ResponseCode'] == '00') {
                    echo "<span style='color:blue'>GD Thanh cong</span>";
                    $sql = "INSERT INTO appointment
                            VALUES (NULL, $pid, $scheduleid, $docid, false)"; 
                    $conn->query($sql);

                } 
                else {
                    echo "<span style='color:red'>GD Khong thanh cong</span>";
                    }
            } else {
                echo "<span style='color:red'>Chu ky khong hop le</span>";
                }
?>