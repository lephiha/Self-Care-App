<?php
require_once 'connection.php';
require_once 'vendor/autoload.php';

use \Firebase\JWT\JWT;

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    
    $key = "lephiha_secret_key";

    // Lấy dữ liệu từ POST
    $pemail = isset($_POST['email']) ? $_POST['email'] : null; 
    $ppassword = isset($_POST['password']) ? $_POST['password'] : null; 
    $utype = isset($_POST['utype']) ? $_POST['utype'] : 'patient'; 

    $result = ['success' => '0', 'message' => ''];

    // Kiểm tra dữ liệu bắt buộc
    if (!$pemail || !$ppassword) {
        $result["success"] = "0";
        $result["message"] = "Missing required fields";
        echo json_encode($result);
        exit();
    }

    // Kiểm tra định dạng email hợp lệ
    if (!filter_var($pemail, FILTER_VALIDATE_EMAIL)) {
        $result["success"] = "0";
        $result["message"] = "Invalid email format";
        echo json_encode($result);
        exit();
    }


    if (strcasecmp($utype, "patient") == 0) {
        $stmt = $conn->prepare("SELECT * FROM patient WHERE pemail = ?");
        $stmt->bind_param("s", $pemail);
        $stmt->execute();
        $response = $stmt->get_result();
    
        if ($response->num_rows === 1) {
            $row = $response->fetch_assoc();
            $storedPassword = $row['ppassword']; 
    
            // Kiểm tra mật khẩu nhập vào có khớp với mật khẩu đã mã hóa không
            if (password_verify($ppassword, $storedPassword)) {
                // Tạo payload cho JWT
                $payload = [
                    'iss' => "", 
                    'aud' => "", 
                    'iat' => time(), // Thời gian phát hành
                    'exp' => time() + 3600, // Hết hạn sau 1 giờ
                    'data' => [
                        'id' => $row['pid'],
                        'fullname' => $row['pname'],
                        'email' => $row['pemail'],
                        'utype' => "patient"
                    ]
                ];

                // Mã hóa JWT
                $jwt = JWT::encode($payload, $key, 'HS256');

                $result['login'] = [
                    'id' => $row['pid'],
                    'fullname' => $row['pname'],
                    'email' => $row['pemail'],
                    'token' => $jwt, // Token JWT
                    'utype' => "patient"
                ];
                $result['success'] = "1";
                $result['message'] = "Login successful";
            } else {
                $result['message'] = "Invalid password";
            }
        } else {
            $result['message'] = "User not found";
        }
    } elseif (strcasecmp($utype, "doctor") == 0) {
        $stmt = $conn->prepare("SELECT * FROM doctor WHERE docemail = ?");
        $stmt->bind_param("s", $pemail);
        $stmt->execute();
        $response = $stmt->get_result();
    
        if ($response->num_rows === 1) {
            $row = $response->fetch_assoc();
            $storedPassword = $row['docpassword']; // Mật khẩu lưu trong DB (không mã hóa)
    
            // Kiểm tra mật khẩu không mã hóa
            if ($ppassword === $storedPassword) {
                $index = [
                    'id' => $row['docid'],
                    'fullname' => $row['docname'],
                    'email' => $row['docemail'],
                    'phone' => $row['doctel'],
                    'token' => $row['token'],
                    'utype' => "doctor"
                ];
                array_push($result['login'], $index);
                $result['success'] = "1";
                $result['message'] = "Login successful";
            } else {
                $result['message'] = "Invalid password";
            }
        } else {
            $result['message'] = "User not found";
        }
    
    } else {
        $result['message'] = "Invalid user type.";
    }

    if ($pemail === 'patient@edoc.com' && $ppassword === '123') {
        // Tạo payload cho JWT
        $payload = [
            'iss' => "",
            'aud' => "",
            'iat' => time(),
            'exp' => time() + 3600, // Token hết hạn sau 1 giờ
            'data' => [
                'id' => 1, // ID giả định
                'fullname' => $row['pname'],
                'email' => $pemail,
                'phone' => "0123456789",
                'utype' => "patient"
            ]
        ];

        // Mã hóa JWT
        $jwt = JWT::encode($payload, $key, 'HS256');

        $result['login'] = [];
        $index = [
            'id' => 1, // ID giả định
            'fullname' => $row['pname'],
            'email' => $pemail,
            'phone' => "0123456789",
            'token' => $jwt, // Thêm token JWT
            'utype' => "patient"
        ];
        array_push($result['login'], $index);

        $result['success'] = "1";
        $result['message'] = "Login successful (hardcoded)";
        echo json_encode($result);
        exit();
    }

    // Trả về phản hồi JSON
    echo json_encode($result);
    echo "Entered password: " . $ppassword;
    echo "Stored password: " . $storedPassword;


    // Đóng kết nối
    $stmt->close();
    $conn->close();
    
}
?>
