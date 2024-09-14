<?php
    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        $username = $_POST['username'];
        $password = $_POST['password'];

        require_once 'connection.php';

        $sql = "SELECT * FROM user WHERE username='$username'";
        $response = mysqli_query($conn, $sql);
        $result = array();
        $result['login'] = array();

        if (mysqli_num_rows($response) === 1) {
            $row = mysqli_fetch_assoc($response);
            
            if (password_verify($password, $row['password'])) {
                $index['userid'] = $row['userid'];
                $index['username'] = $row['username'];
                $index['fullname'] = $row['fullname']; 
                $index['email'] = $row['email']; 

                array_push($result['login'], $index);
                $result['success'] = "1";
                echo json_encode($result);
            } else {
                $result['success'] = "0";
                $result['message'] = "Invalid password";
                echo json_encode($result);
            }
        } else {
            $result['success'] = "0";
            $result['message'] = "User not found";
            echo json_encode($result);
        }
        mysqli_close($conn);
    }
?>
