package seedu.address.commons.util;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Utility class to encrypt and decrypt passwords.
 */
public class PasswordUtil {

    private static final int SALT_BYTE_SIZE = 16;
    private static final int HASH_BYTE_SIZE = 64 * 8;
    private static final int PKBDF2_ITERATIONS = 1000;

    /**
     * Returns the password encrypted using PBKDF2WithHmacSHA1.
     */
    public static String getEncryptedPassword(String plainPassword)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        return generateHash(plainPassword);
    }

    /**
     * Returns true if plainPassword is equal to encryptedPassword decrypted.
     */
    public static boolean validatePassword(String plainPassword, String encryptedPassword)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        String [] splits = encryptedPassword.split(":");
        int iterations = Integer.parseInt(splits[0]);
        byte[] salt = convertFromHex(splits[1]);
        byte[] hash = convertFromHex(splits[2]);
        char[] chars = plainPassword.toCharArray();

        PBEKeySpec pbeKeySpec = new PBEKeySpec(chars, salt, iterations, HASH_BYTE_SIZE);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = secretKeyFactory.generateSecret(pbeKeySpec).getEncoded();

        int diff = hash.length ^ testHash.length;
        for (int i = 0; i < hash.length && i < testHash.length; i++) {
            diff |= hash[i] ^ testHash[i];
        }

        return diff == 0;
    }

    /**
     * Generates the encrypted password using PBKDF2WithHmacSHA1.
     */
    private static String generateHash(String plainPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        char[] chars = plainPassword.toCharArray();
        byte[] salt = generateSalt();

        PBEKeySpec pbeKeySpec = new PBEKeySpec(chars, salt, PKBDF2_ITERATIONS, HASH_BYTE_SIZE);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = secretKeyFactory.generateSecret(pbeKeySpec).getEncoded();
        return PKBDF2_ITERATIONS + ":" + convertToHex(salt) + ":" + convertToHex(hash);
    }

    /**
     * Generates the salt used in password encryption.
     */
    private static byte[] generateSalt() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[SALT_BYTE_SIZE];
        secureRandom.nextBytes(salt);
        return salt;
    }

    /**
     * Converts an array of bytes to a hexadecimal string.
     */
    private static String convertToHex(byte[] array) {
        BigInteger bigInteger = new BigInteger(1, array);
        String hex = bigInteger.toString(SALT_BYTE_SIZE);
        int paddingLength = (array.length * 2) - hex.length();

        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        }

        return hex;
    }

    /**
     * Converts a hexadecimal string into an array of bytes.
     */
    private static byte[] convertFromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), SALT_BYTE_SIZE);
        }

        return bytes;
    }
}
