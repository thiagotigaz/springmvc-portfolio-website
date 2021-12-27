package br.com.supercloud.cms;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderMain {
    public static void main(String[] args) {
        String encoded = new BCryptPasswordEncoder().encode("userpass");
        System.out.println(encoded);
    }
}
