package iuh.fit.bookstorebackend.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

/**
 * Small wrapper around Spring Security's BCrypt implementation.
 * BCrypt generates a random salt for every hash and stores it inside the hash string,
 * so there is nothing extra to save: one column (accounts.password) is enough.
 */
public final class PasswordHasher {
    // Work factor: each +1 doubles the time. 10 is the default, 12 is a good modern choice.
    private static final int STRENGTH = 12;
    // BCryptPasswordEncoder is thread-safe, so one shared instance is fine.
    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder(STRENGTH);

    private PasswordHasher() {
    }

    /** Hashes a raw password. Call this before saving an Account. */
    public static String hash(String rawPassword) {
        Objects.requireNonNull(rawPassword, "rawPassword must not be null");
        return ENCODER.encode(rawPassword);
    }

    /** Checks a raw password (e.g. from a login form) against a stored hash. */
    public static boolean matches(String rawPassword, String hashedPassword) {
        if (rawPassword == null || hashedPassword == null) {
            return false;
        }
        return ENCODER.matches(rawPassword, hashedPassword);
    }

    /**
     * True if the stored hash was made with a weaker strength than STRENGTH.
     * After a successful login, if this returns true, re-hash the password and save it.
     */
    public static boolean needsRehash(String hashedPassword) {
        return hashedPassword != null && ENCODER.upgradeEncoding(hashedPassword);
    }
}