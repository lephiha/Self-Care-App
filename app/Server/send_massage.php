<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $sender_username = $_POST['sender_username'] ?? '';
    $receiver_username = $_POST['receiver_username'] ?? '';
    $message = $_POST['message'] ?? '';

    // Kiểm tra các trường có rỗng không
    if (empty($sender_username) || empty($receiver_username) || empty($message)) {
        echo json_encode(['success' => 0, 'message' => 'All fields are required.']);
        exit();
    }

    // Kết nối CSDL
    require_once 'connection.php';

    // Thêm tin nhắn vào bảng messages
    $stmt = $conn->prepare("INSERT INTO messages (sender_username, receiver_username, message) VALUES (?, ?, ?)");
    $stmt->bind_param("sss", $sender_username, $receiver_username, $message);

    if ($stmt->execute()) {
        echo json_encode(['success' => 1, 'message' => 'Message sent successfully.']);
    } else {
        echo json_encode(['success' => 0, 'message' => 'Failed to send message.']);
    }

    // Đóng kết nối
    $stmt->close();
    $conn->close();
}
?>
