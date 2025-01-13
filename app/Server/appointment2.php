<?php
    require_once("connection.php");

    //$key = "LPH-secret-key"; // Khóa bí mật (cần bảo mật)
    //$iv = "LPH-secret-iv";   // Vector khởi tạo (cần bảo mật)

    //function decryptData($encrypted, $key, $iv) {
      //  return openssl_decrypt(base64_decode($encrypted), 'AES-256-CBC', $key, 0, $iv);
    //}

//$encrypted_docid = $_GET["docid"];
$docid = decryptData($encrypted_docid, $key, $iv);
    $docid = $_GET["docid"];
    $stmt = $conn->prepare("SELECT A.appoid,A.pid, P.pname,D.docname, A.hasDone, A.scheduledate, A.starttime, A.endtime
                            FROM appointment as A, patient as P, doctor as D
                            WHERE A.docid = $docid and A.pid = P.pid and D.docid = A.docid");
    $stmt->execute();
    $stmt->bind_result($appoid,$pid,$pname,$docname,$hasDone,$scheduledate,$starttime,$endtime);

    $appo_array = array();
    while($stmt->fetch()){
        $temp = array();
        $temp['appoid'] = $appoid;
        $temp['pid'] = $pid;
        $temp['pname'] = $pname;
        $temp['docname'] = $docname;
        $temp['hasDone'] = $hasDone;
        $temp['scheduledate'] = $scheduledate;
        $temp['starttime'] = $starttime;
        $temp['endtime'] = $endtime;
        array_push($appo_array,$temp);
    }

    echo json_encode($appo_array);
?>
<?php
require_once("connection.php");

$key = "your-secret-key"; // Khóa bí mật (cần bảo mật)
$iv = "your-secret-iv";   // Vector khởi tạo (cần bảo mật)

function decryptData($encrypted, $key, $iv) {
    return openssl_decrypt(base64_decode($encrypted), 'AES-256-CBC', $key, 0, $iv);
}

$encrypted_docid = $_GET["docid"];
$docid = decryptData($encrypted_docid, $key, $iv);

$stmt = $conn->prepare("SELECT A.appoid, A.pid, P.pname, D.docname, A.hasDone, A.scheduledate, A.starttime, A.endtime
                        FROM appointment AS A
                        JOIN patient AS P ON A.pid = P.pid
                        JOIN doctor AS D ON A.docid = D.docid
                        WHERE A.docid = ?");
$stmt->bind_param("i", $docid);
$stmt->execute();
$stmt->bind_result($appoid, $pid, $pname, $docname, $hasDone, $scheduledate, $starttime, $endtime);

$appo_array = array();
while ($stmt->fetch()) {
    $temp = array(
        'appoid' => $appoid,
        'pid' => $pid,
        'pname' => $pname,
        'docname' => $docname,
        'hasDone' => $hasDone,
        'scheduledate' => $scheduledate,
        'starttime' => $starttime,
        'endtime' => $endtime
    );
    array_push($appo_array, $temp);
}

echo json_encode($appo_array);
?>
*/