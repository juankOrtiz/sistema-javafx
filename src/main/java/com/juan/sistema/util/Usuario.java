package com.juan.sistema.util;

import org.mindrot.jbcrypt.BCrypt;

public class Usuario {
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
