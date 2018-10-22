package seedu.address.storage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * A class to access UserAccount stored in the hard disk as a JSON file
 */
public class JsonUserStorage implements UserStorage {

    private Path folderPath;
    private String filePathString;

    public JsonUserStorage(Path folderPath, Path filePath) throws IOException {
        this.folderPath = folderPath;
        filePathString = "./" + filePath.toString();

        if (Files.notExists(filePath)) {
            createUserFile();
        }
    }

    /**
     * Adds a new property in the JSON file.
     */
    @Override
    public void createUser(String username, String password) throws IOException {
        Gson gson = new Gson();
        JsonObject jsonObject = getUserAccounts();
        jsonObject.addProperty(username, password);

        writeJson(gson, jsonObject);
    }

    /**
     * Returns the user account JSON as a hash map JSON object.
     */
    @Override
    public JsonObject getUserAccounts() throws IOException {
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(new FileReader(filePathString));

        return jsonElement.getAsJsonObject();
    }

    /**
     * Creates a user account JSON file.
     */
    private void createUserFile() throws IOException {
        if (Files.notExists(folderPath)) {
            Files.createDirectory(folderPath);
        }

        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("admin", "root");

        writeJson(gson, jsonObject);
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
