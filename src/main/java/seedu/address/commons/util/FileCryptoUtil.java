package seedu.address.commons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import seedu.address.commons.exceptions.FileCryptoException;

/**
 * A utility class to encrypt and decrypt files.
 */
public class FileCryptoUtil {

    private static final String ALGO = "AES";
    private static final String TRANSFORMATION = "AES";

    /**
     * Encrypts a given file
     * @param key used as an AES key
     * @param inputPath file to be encrypted
     * @param outputPath file to be decrypted
     * @throws FileCryptoException
     */
    public static void encrypt(String key, Path inputPath, Path outputPath) throws FileCryptoException {
        executeCipher(Cipher.ENCRYPT_MODE, key, inputPath.toFile(), outputPath.toFile());
    }

    /**
     * Decrypts a given file
     * @param inputPath file to be decrypted
     * @param outputPath file to be encrypted
     * @throws FileCryptoException
     */
    public static void decrypt(Path inputPath, Path outputPath) throws FileCryptoException {
        String[] path = inputPath.toString().split("/");
        String key = path[1];
        executeCipher(Cipher.DECRYPT_MODE, key, inputPath.toFile(), outputPath.toFile());
    }

    /**
     * Executes encryption/decryption
     */
    private static void executeCipher(int cipherMode, String key, File inputFile, File outputFile)
            throws FileCryptoException {

        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGO);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[ (int) inputFile.length()];
            inputStream.read(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            byte[] outputBytes = cipher.doFinal(inputBytes);
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();
            inputFile.delete();
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException
                | BadPaddingException | IllegalBlockSizeException | IOException e) {
            throw new FileCryptoException(e);
        }
    }
}
