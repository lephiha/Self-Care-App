<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $sender_username = $_POST['sender_username'] ?? '';
    $receiver_username = $_POST['receiver_username'] ?? '';

    // Kiểm tra dữ liệu đầu vào
    if (empty($sender_username) || empty($receiver_username)) {
        echo json_encode(['success' => 0, 'message' => 'Both usernames are required.']);
        exit();
    }

    // Kết nối cơ sở dữ liệu
    require_once 'connection.php'; // File kết nối database

    // Truy vấn để lấy tin nhắn giữa hai người dùng
    $stmt = $conn->prepare("SELECT * FROM messages WHERE (sender_username = ? AND receiver_username = ?) OR (sender_username = ? AND receiver_username = ?) ORDER BY timestamp ASC");
    $stmt->bind_param("ssss", $sender_username, $receiver_username, $receiver_username, $sender_username);
    $stmt->execute();
    $result = $stmt->get_result();

    $messages = [];
    while ($row = $result->fetch_assoc()) {
        $messages[] = $row;
    }

    echo json_encode(['success' => 1, 'messages' => $messages]);

    // Đóng kết nối
    $stmt->close();
    $conn->close();
} else {
    echo json_encode(['success' => 0, 'message' => 'Invalid request method.']);
}
?>
