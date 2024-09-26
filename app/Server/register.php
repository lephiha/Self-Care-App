<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    // Lấy dữ liệu từ POST và kiểm tra xem các trường có tồn tại không
    $fullname = isset($_POST['fullname']) ? $_POST['fullname'] : null;
    $email = isset($_POST['email']) ? $_POST['email'] : null;
    $phone = isset($_POST['phone']) ? $_POST['phone'] : null;
    $username = isset($_POST['username']) ? $_POST['username'] : null;
    $password = isset($_POST['password']) ? $_POST['password'] : null;
    $utype = isset($_POST['utype']) ? $_POST['utype'] : null; // Kiểm tra và gán null nếu không có utype

    // Mã hóa mật khẩu nếu tồn tại
    if ($password) {
        $password = password_hash($password, PASSWORD_DEFAULT);
    }

    
    require_once 'connection.php';

    // Kiểm tra username đã tồn tại chưa, sử dụng prepared statements để tránh SQL Injection
    $sql1 = $conn->prepare("SELECT * FROM user WHERE username = ?");
    $sql1->bind_param("s", $username);
    $sql1->execute();
    $rs = $sql1->get_result();

    // Nếu username đã tồn tại
    if ($rs->num_rows === 1) {
        $result["success"] = "2";
        $result["message"] = "Username already exists";
        echo json_encode($result);
        mysqli_close($conn);

    } else {
        // Chuẩn bị truy vấn thêm thông tin người dùng vào cơ sở dữ liệu
        $sql = $conn->prepare("INSERT INTO user (fullname, email, phone, username, password, utype) 
                               VALUES (?, ?, ?, ?, ?, ?)");
        $sql->bind_param("ssssss", $fullname, $email, $phone, $username, $password, $utype);

        // Thực hiện truy vấn và kiểm tra kết quả
        if ($sql->execute()) {
            $result["success"] = "1";
            $result["message"] = "Registration successful";
            echo json_encode($result);
        } else {
            $result["success"] = "0";
            $result["message"] = "Registration error";
            echo json_encode($result);
        }


        mysqli_close($conn);
    }

} else {
    // Nếu không phải phương thức POST, trả về thông báo lỗi
    $result["success"] = "0";
    $result["message"] = "Invalid request method";
    echo json_encode($result);
}
?>
