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
    if(strcasecmp($utype,"patient")==0){
        $stmt = $conn->prepare("SELECT * FROM patient WHERE pemail = ?");  
        $stmt->bind_param("s", $username);  
        $stmt->execute();  
        $response = $stmt->get_result();  

        if ($response->num_rows === 1) {  
            $row = $response->fetch_assoc();  
            
            // Kiểm tra mật khẩu  
            if (strcasecmp($password, $row['ppassword'])==0) {  
                // Kiểm tra vai trò (không phân biệt chữ hoa chữ thường)  
                 
                    $index = [
                        'id' => $row['pid'],  
                        'username' => $row['pname'],  
                        'fullname' => $row['pname'],  
                        'email' => $row['pemail'],  
                        'phone' => $row['ptel'],  
                        'token' => $row['token'],
                        'utype' => "patient" // Lấy loại tài khoản (role)  
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
    }else{
        //login as doctor
        $stmt = $conn->prepare("SELECT * FROM doctor WHERE docemail = ?");  
        $stmt->bind_param("s", $username);  
        $stmt->execute();  
        $response = $stmt->get_result();  

        if ($response->num_rows === 1) {  
            $row = $response->fetch_assoc();  
            
            // Kiểm tra mật khẩu  
            if (strcasecmp($password, $row['docpassword'])==0) {  
                // Kiểm tra vai trò (không phân biệt chữ hoa chữ thường)  
                    $index = [
                        'username' => $row['docname'], 
                        'id' => $row['docid'],   
                        'fullname' => $row['docname'],  
                        'email' => $row['docemail'],  
                        'phone' => $row['doctel'],
                        'token' => $row['token'],  
                        'utype' => "doctor" // Lấy loại tài khoản (role) 
                    ];  
                    
                    array_push($result['login'], $index);  
                    $result['success'] = "1";  
                    $result['message'] = "Login successful";  
                } 
             else {  
                
                $result['message'] = "Invalid password";  
            }  
        } else {  
            
            $result['message'] = "User not found";  
        }  
    }
    // Sử dụng prepared statements để tránh SQL Injection  
    

      
    echo json_encode($result);  

    // Đóng kết nối và statement  
    $stmt->close();  
    $conn->close();  
}  
?>
