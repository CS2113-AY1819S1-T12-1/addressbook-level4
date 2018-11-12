package seedu.address.commons.exceptions;

/**
 * Signals that file encryption has failed.
 */
public class FileCryptoException extends Exception {
    /**
     * @param cause of the main exception
     */
    public FileCryptoException(Throwable cause) {
        super(cause);
    }
}