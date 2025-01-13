<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    require_once 'connection.php'; 

    // Khóa mã hóa (bạn nên lưu khóa này ở nơi bảo mật, ví dụ file .env hoặc cấu hình bảo mật)
    $encryption_key = "your_secret_key"; // Thay thế bằng khóa mã hóa của bạn

    // Hàm mã hóa dữ liệu
    function encryptData($data, $key) {
        $cipher_method = "AES-128-CTR";
        $iv_length = openssl_cipher_iv_length($cipher_method);
        $iv = random_bytes($iv_length); // Tạo vector khởi tạo
        $encrypted_data = openssl_encrypt($data, $cipher_method, $key, 0, $iv);
        return base64_encode($encrypted_data . "::" . $iv); // Lưu cả dữ liệu mã hóa và vector khởi tạo
    }

    // Hàm giải mã dữ liệu
    function decryptData($encryptedData, $key) {
        $cipher_method = "AES-128-CTR";
        list($encrypted_data, $iv) = explode("::", base64_decode($encryptedData), 2);
        return openssl_decrypt($encrypted_data, $cipher_method, $key, 0, $iv);
    }

    // Lấy dữ liệu từ POST
    $pname = isset($_POST['fullname']) ? $_POST['fullname'] : null;
    $pemail = isset($_POST['email']) ? $_POST['email'] : null;
    $ptel = isset($_POST['phone']) ? $_POST['phone'] : null;
    $ppassword = isset($_POST['password']) ? $_POST['password'] : null;
    $utype = isset($_POST['utype']) ? $_POST['utype'] : 'patient';
    $paddress = isset($_POST['address']) ? $_POST['address'] : null;
    $pdob = isset($_POST['dob']) ? $_POST['dob'] : null;
    $pnic = isset($_POST['nic']) ? $_POST['nic'] : null;

    // Kiểm tra dữ liệu bắt buộc
    if (!$pname || !$pemail || !$ptel || !$ppassword) {
        $result["success"] = "0";
        $result["message"] = "Thiếu thông tin bắt buộc";
        echo json_encode($result);
        exit();
    }

    // Kiểm tra nếu email đã tồn tại
    $stmt_check = $conn->prepare("SELECT * FROM patient WHERE pemail = ?");
    $stmt_check->bind_param("s", $pemail);
    $stmt_check->execute();
    $result_check = $stmt_check->get_result();

    if ($result_check->num_rows > 0) {
        $result["success"] = "0";
        $result["message"] = "Email đã tồn tại";
        echo json_encode($result);
        exit();
    }

    // Mã hóa mật khẩu
    $hashedPassword = password_hash($ppassword, PASSWORD_BCRYPT);

    // Mã hóa dữ liệu nhạy cảm
    $encryptedEmail = encryptData($pemail, $encryption_key);
    $encryptedPhone = encryptData($ptel, $encryption_key);
    $encryptedAddress = $paddress ? encryptData($paddress, $encryption_key) : null;

    // Chèn dữ liệu vào bảng
    $stmt = $conn->prepare("INSERT INTO patient (pname, pemail, ptel, paddress, pdob, pnic, ppassword, utype) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
    $stmt->bind_param("ssssssss", $pname, $encryptedEmail, $encryptedPhone, $encryptedAddress, $pdob, $pnic, $hashedPassword, $utype);

    // Thực hiện câu lệnh SQL
    if ($stmt->execute()) {
        $result["success"] = "1";
        $result["message"] = "Đăng ký bệnh nhân thành công";
    } else {
        $result["success"] = "0";
        $result["message"] = "Lỗi: " . $stmt->error;
    }

    // Đóng kết nối
    $stmt->close();
    $conn->close();

    // Gửi kết quả trả về dưới dạng JSON
    echo json_encode($result);
}
?>
