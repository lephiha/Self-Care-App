<?php
	if($_SERVER['REQUEST_METHOD'] == 'POST') {

		
		$fullname = $_POST['fullname'];  // Get fullname from form
		$email = $_POST['email'];
		$phone = $_POST['phone'];        // Get phone from form
		$username = $_POST['username'];
		$password = $_POST['password'];

		$password = password_hash($password, PASSWORD_DEFAULT);  // Hash the password

		require_once 'connection.php';

		// Check if username already exists
		$sql1 = "SELECT * FROM user WHERE username = '$username'";
		$rs = mysqli_query($conn, $sql1);
		$data = mysqli_fetch_array($rs, MYSQLI_NUM);

		if(mysqli_num_rows($rs) === 1) {
			$result["success"] = "2";
			$result["message"] = "Username already exists";

			echo json_encode($result);
			mysqli_close($conn);

		} else {
			// Insert user details into the database
			$sql = "INSERT INTO user (fullname, email, phone, username, password) 
					VALUES ('$fullname','$email', '$phone', '$username', '$password')";

			if(mysqli_query($conn, $sql)) {
				$result["success"] = "1";
				$result["message"] = "Registration successful";

				echo json_encode($result);
				mysqli_close($conn);
			} else {
				$result["success"] = "0";
				$result["message"] = "Registration error";

				echo json_encode($result);
				mysqli_close($conn);
			}
		}
	}
?>
