package com.lph.selfcareapp.Login;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHelper {
    //method to hash a pass
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    //method verify a pass
    public static boolean verifyPassword(String enteredPassword, String hashedPassword) {
        return BCrypt.checkpw(enteredPassword, hashedPassword);
    }
}
