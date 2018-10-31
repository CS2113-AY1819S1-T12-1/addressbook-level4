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

/**
 * A class to access UserSession stored in the hard disk as a JSON file
 */
public class JsonUserStorage implements UserStorage {

    private String filePathString;
    private Map<String, String> userAccounts;

    public JsonUserStorage(Path filePath) throws IOException {
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

        writeJson(new Gson(), jsonObject);
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
        userAccounts = new Gson().fromJson(new FileReader(filePathString), type);
    }

    /**
     * Returns the user accounts as a JSON Object.
     */
    private JsonObject getJsonObject() throws FileNotFoundException {
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(new FileReader(filePathString));

        return jsonElement.getAsJsonObject();
    }

    /**
     * Creates a user account JSON file.
     */
    private void createUserFile() throws IOException {
        JsonObject jsonObject = new JsonObject();
        writeJson(new Gson(), jsonObject);
    }

    /**
     * Writes to the User JSON.
     */
    private void writeJson(Gson gson, JsonObject jsonObject) throws IOException {
        String json = gson.toJson(jsonObject);
        FileWriter file = new FileWriter(filePathString);
        file.write(json);
        file.flush();
    }
}
