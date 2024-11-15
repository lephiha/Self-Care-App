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
            $amount = (string ) $_GET['vnp_Amount'];
            $order_info = (string ) $_GET['vnp_OrderInfo'];
            $array = explode(" ", $order_info);
            $content = $array[0];
            $pid = $array[1];
            $scheduleid = $array[2];
            // var_dump($array);
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
                    if(strcmp($content,"book")==0){
                        $sql = "INSERT INTO appointment(pid, hasDone, docid, scheduledate, starttime, endtime)
                            SELECT $pid, 0, docid, scheduledate, starttime, endtime
                            FROM schedule
                            WHERE scheduleid = $scheduleid"; 
                        $conn->query($sql);
                        $curr_month = date("m");
                        $clinic_id = $conn->query("SELECT C.clinic_id
                                    FROM clinic as C, doctor as D, schedule as S
                                    WHERE D.docid = S.docid
                                    and D.chief_id = C.chief_id
                                    and S.scheduleid = $scheduleid")->fetch_assoc()['clinic_id'];
                        $currAmount = $conn->query("SELECT amount
                                        FROM income 
                                        WHERE clinic_id = $clinic_id
                                        and month = $curr_month")->fetch_assoc()['amount'];
                        $newAmount = $currAmount + $amount/(100*1000000);
                        $conn->query("UPDATE income
                                    SET amount = $newAmount
                                    where clinic_id = $clinic_id
                                    and month = $curr_month");
                        $sql = "UPDATE schedule
                                SET booked = 1
                                where scheduleid = $scheduleid";

                        $conn->query($sql);
                    }else{
                        $sql = "UPDATE videocall
                                SET paid = 1
                                WHERE pid = $pid
                                AND docid = $scheduleid"; 
                        $conn->query($sql); 
                    }
                    
                    $conn->close();
                    
                } 
                else {
                    echo "<span style='color:red'>GD Khong thanh cong</span>";
                    }
            } else {
                echo "<span style='color:red'>Chu ky khong hop le</span>";
                }
?>