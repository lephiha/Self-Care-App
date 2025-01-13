<?php
require_once 'connection.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $pemail = isset($_POST['email']) ? $_POST['email'] : null;
    $code = isset($_POST['code']) ? $_POST['code'] : null;
    $newPassword = isset($_POST['new_password']) ? $_POST['new_password'] : null;

    $result = ['success' => '0', 'message' => ''];

    // Kiểm tra các trường không được để trống
    if (!$pemail || !$code || !$newPassword) {
        $result['message'] = 'All fields are required';
        echo json_encode($result);
        exit();
    }

    // Kiểm tra độ mạnh mật khẩu mới
    if (strlen($newPassword) < 8 || !preg_match('/[A-Z]/', $newPassword) || !preg_match('/[0-9]/', $newPassword)) {
        $result['message'] = 'Password must be at least 8 characters long, include at least one uppercase letter and one number';
        echo json_encode($result);
        exit();
    }

    // Kiểm tra mã xác nhận
    $stmt = $conn->prepare("SELECT * FROM password_reset WHERE email = ? AND code = ? AND created_at >= NOW() - INTERVAL 15 MINUTE ORDER BY created_at DESC LIMIT 1");
    $stmt->bind_param("ss", $pemail, $code);
    $stmt->execute();
    $response = $stmt->get_result();

    if ($response->num_rows === 1) {
        // Mã xác nhận hợp lệ, cập nhật mật khẩu mới
        $hashedPassword = password_hash($newPassword, PASSWORD_BCRYPT);

        $stmtUpdate = $conn->prepare("UPDATE patient SET ppassword = ? WHERE pemail = ?");
        $stmtUpdate->bind_param("ss", $hashedPassword, $pemail);

        if ($stmtUpdate->execute()) {
            // Xóa mã xác nhận sau khi sử dụng
            $stmtDelete = $conn->prepare("DELETE FROM password_reset WHERE email = ?");
            $stmtDelete->bind_param("s", $pemail);
            $stmtDelete->execute();

            $result['success'] = '1';
            $result['message'] = 'Password updated successfully';
        } else {
            $result['message'] = 'Failed to update password';
        }
    } else {
        $result['message'] = 'Invalid or expired verification code';
    }

    echo json_encode($result);
    exit();
}
?>
