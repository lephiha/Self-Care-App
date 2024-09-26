<?php  
if ($_SERVER['REQUEST_METHOD'] == 'POST') {  
    // Lấy thông tin đăng nhập từ yêu cầu POST  
    $username = $_POST['username'] ?? '';  
    $password = $_POST['password'] ?? '';  
    $utype = $_POST['utype'] ?? ''; // Lấy vai trò từ yêu cầu POST  

    require_once 'connection.php';  

    // Khởi tạo phản hồi  
    $result = [  
        'login' => [],  
        'success' => "0",  
        'message' => ""  
    ];  

    // Kiểm tra thông tin đăng nhập không rỗng  
    if (empty($username) || empty($password) || empty($utype)) {  
        $result['message'] = "All fields are required.";  
        echo json_encode($result);  
        exit();  
    }  

    // Sử dụng prepared statements để tránh SQL Injection  
    $stmt = $conn->prepare("SELECT * FROM user WHERE username = ?");  
    $stmt->bind_param("s", $username);  
    $stmt->execute();  
    $response = $stmt->get_result();  

    if ($response->num_rows === 1) {  
        $row = $response->fetch_assoc();  
        
        // Kiểm tra mật khẩu  
        if (password_verify($password, $row['password'])) {  
            // Kiểm tra vai trò (không phân biệt chữ hoa chữ thường)  
            if (strcasecmp($row['utype'], $utype) === 0) {  
                $index = [  
                    'username' => $row['username'],  
                    'fullname' => $row['fullname'],  
                    'email' => $row['email'],  
                    'phone' => $row['phone'],  
                    'utype' => $row['utype'] // Lấy loại tài khoản (role)  
                ];  
                
                array_push($result['login'], $index);  
                $result['success'] = "1";  
                $result['message'] = "Login successful";  
            } else {  
                 
                $result['message'] = "Role does not match";  
            }  
        } else {  
             
            $result['message'] = "Invalid password";  
        }  
    } else {  
        
        $result['message'] = "User not found";  
    }  

      
    echo json_encode($result);  

    // Đóng kết nối và statement  
    $stmt->close();  
    $conn->close();  
}  
?>
