<?php
require_once 'connection.php';
require_once 'vendor/autoload.php';

use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $pemail = isset($_POST['email']) ? $_POST['email'] : null;

    $result = ['success' => '0', 'message' => ''];

    if (!$pemail) {
        $result["message"] = "Email is required";
        echo json_encode($result);
        exit();
    }

    // Kiểm tra email tồn tại trong bảng patient
    $stmt = $conn->prepare("SELECT * FROM patient WHERE pemail = ?");
    $stmt->bind_param("s", $pemail);
    $stmt->execute();
    $response = $stmt->get_result();

    if ($response->num_rows === 1) {
        $row = $response->fetch_assoc();
        $fullname = $row['pname']; // Lấy tên đầy đủ của user

        // Tạo mã xác nhận
        $verificationCode = strtoupper(substr(md5(rand()), 0, 6));

        // Lưu mã xác nhận vào bảng password_reset
        $stmtInsert = $conn->prepare("INSERT INTO password_reset (email, code, created_at) VALUES (?, ?, NOW())");
        $stmtInsert->bind_param("ss", $pemail, $verificationCode);
        $stmtInsert->execute();

        // Gửi email xác nhận đến admin
        $mail = new PHPMailer(true);
        try {
            // Cấu hình PHPMailer
            $mail->isSMTP();
            $mail->Host = 'smtp.gmail.com';
            $mail->SMTPAuth = true;
            $mail->Username = 'leeha867@gmail.com'; // Email gửi
            $mail->Password = ''; // Mật khẩu ứng dụng
            $mail->SMTPSecure = PHPMailer::ENCRYPTION_STARTTLS;
            $mail->Port = 587;

            // Thiết lập thông tin người gửi và người nhận
            $mail->setFrom('leeha867@gmail.com', 'E-Doctor Support');
            $mail->addAddress('phihasky@gmail.com', 'Admin'); // Email quản trị nhận mã xác nhận

            // Nội dung email
            $mail->isHTML(true);
            $mail->Subject = "Password Reset Request";
            $mail->Body = "
                Hello Admin,<br><br>
                User <b>$fullname</b> has requested a password reset.<br>
                Email: <b>$pemail</b><br>
                Verification Code: <b>$verificationCode</b>
            ";

            $mail->send();

            $result['success'] = '1';
            $result['message'] = 'Verification code sent to admin email';
        } catch (Exception $e) {
            $result['message'] = "Mail could not be sent. Error: {$mail->ErrorInfo}";
        }
    } else {
        $result['message'] = 'Email not found';
    }

    echo json_encode($result);
    exit();
}
?>
