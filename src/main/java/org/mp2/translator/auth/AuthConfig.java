package org.mp2.translator.auth;

public final class AuthConfig {
    private static final String USER = System.getenv().getOrDefault("BASIC_AUTH_USER", "admin");
    private static final String PASSWORD = System.getenv().getOrDefault("BASIC_AUTH_PASSWORD", "admin123");

    private AuthConfig() {
    }

    public static boolean isValid(String user, String password) {
        return USER.equals(user) && PASSWORD.equals(password);
    }
}
