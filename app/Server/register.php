<?php
	if($_SERVER['REQUEST_METHOD'] == 'POST') {

		$username = $_POST['username'];
		$password = $_POST['password'];

		$password = password_hash($password, PASSWORD_DEFAULT);

		require_once 'connection.php';

			$sql1 = "SELECT * FROM user WHERE username = '$username'";
			$rs = mysqli_query($conn, $sql1);
			$data = mysqli_fetch_array($rs, MYSQLI_NUM);

			if(mysqli_num_rows($rs) === 1) {
				$result["success"] = "2";
				$result["message"] = "exist";

				echo json_encode($result);
				mysqli_close($conn);

			}else {
				$sql = "INSERT INTO user (username, password) VALUES ('$username', '$password')";

				if(mysqli_query($conn, $sql)) {
					$result["success"] = "1";
					$result["message"] = "success";

					echo json_encode($result);
					mysqli_close($conn);
				}
				else {
					$result["success"] = "0";
					$result["message"] = "error";

					echo json_encode($result);
					mysqli_close($conn);
				}
			}

			

	}

?>