package seedu.address.storage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import seedu.address.commons.exceptions.FileCryptoException;
import seedu.address.commons.util.FileCryptoUtil;

/**
 * A class to access UserSession stored in the hard disk as a JSON file
 */
public class JsonUserStorage implements UserStorage {

    private String cryptoKey;
    private String filePathString;
    private Path filePath;
    private Path lockedPath;
    private Map<String, String> userAccounts;

    public JsonUserStorage(String cryptoKey, Path filePath, Path lockedPath) throws IOException {
        this.cryptoKey = cryptoKey;
        this.filePath = filePath;
        this.lockedPath = lockedPath;
        filePathString = "./" + filePath.toString();

        if (Files.notExists(filePath)) {
            createUserFile();
        }

        setUserAccounts();
    }

    /**
     * Adds a new property in the JSON file.
     */
    @Override
    public void createUser(String username, String password) throws IOException {
        JsonObject jsonObject = getJsonObject();
        jsonObject.addProperty(username, password);

        writeJson(false, new Gson(), jsonObject);
        setUserAccounts();
    }

    /**
     * Returns the user accounts as a map.
     */
    @Override
    public Map<String, String> getUserAccounts() {
        return userAccounts;
    }

    /**
     * Sets the user account.
     */
    private void setUserAccounts() throws IOException {
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        userAccounts = new Gson().fromJson(readJson(), type);
    }

    /**
     * Returns the user accounts as a JSON Object.
     */
    private JsonObject getJsonObject() throws FileNotFoundException {
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(readJson());

        return jsonElement.getAsJsonObject();
    }

    /**
     * Creates a user account JSON file.
     */
    private void createUserFile() throws IOException {
        JsonObject jsonObject = new JsonObject();
        writeJson(true, new Gson(), jsonObject);
    }

    /**
     * Reads the User JSON.
     */
    private FileReader readJson() throws FileNotFoundException {
        decrypt();
        FileReader fileReader = new FileReader(filePathString);
        encrypt();
        return fileReader;
    }

    /**
     * Writes to the User JSON.
     */
    private void writeJson(boolean isSetup, Gson gson, JsonObject jsonObject) throws IOException {
        if (isSetup) {
            decrypt();
        }

        String json = gson.toJson(jsonObject);
        FileWriter file = new FileWriter(filePathString);
        file.write(json);
        file.flush();
        encrypt();
    }

    /**
     * Encrypts file.
     */
    private void encrypt() {
        try {
            FileCryptoUtil.encrypt(cryptoKey, filePath, lockedPath);
        } catch (FileCryptoException e) {
            e.printStackTrace();
        }
    }

    /**
     * Decrypts file.
     */
    private void decrypt() {
        try {
            FileCryptoUtil.decrypt(cryptoKey, lockedPath, filePath);
        } catch (FileCryptoException e) {
            e.printStackTrace();
        }
    }
}
