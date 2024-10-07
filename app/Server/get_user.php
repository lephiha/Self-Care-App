<?php
require_once 'connection.php'; // Kết nối đến cơ sở dữ liệu

// Truy vấn để lấy danh sách người dùng
$sql = "SELECT username FROM user";
$result = $conn->query($sql);

$users = [];

if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $users[] = $row['username'];
    }
}

echo json_encode($users);
$conn->close();
?>
