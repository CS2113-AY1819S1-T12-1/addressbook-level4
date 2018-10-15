package seedu.address.storage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * A class to access UserAccount stored in the hard disk as a JSON file
 */
public class JsonUserStorage {

    private Path folderPath;
    private Path filePath;
    private String folderPathString;

    public JsonUserStorage(Path folderPath, Path filePath) throws IOException {
        this.filePath = filePath;
        this.folderPath = folderPath;
        folderPathString = "./" + folderPath.toString();

        if (Files.notExists(filePath)) {
            createUserFile();
        }
    }

    /**
     * Returns the user account JSON as a hash map JSON object.
     */
    public JSONObject getUserAccounts() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object object = parser.parse(new FileReader(folderPathString));
        return (JSONObject) object;
    }

    /**
     * Creates a user account JSON file.
     */
    @SuppressWarnings("unchecked")
    private void createUserFile() throws IOException {
        Files.createDirectory(folderPath);
        Files.createFile(filePath);

        JSONObject object = new JSONObject();
        object.put("admin", "root");

        FileWriter file = new FileWriter(folderPathString);
        file.write(object.toJSONString());
        file.flush();
    }
}
