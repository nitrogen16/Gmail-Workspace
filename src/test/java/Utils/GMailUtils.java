package Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class GMailUtils {

    private static Map<String, String> credentials;

    public GMailUtils() {
        setCredentials();
    }

    private void setCredentials() {
        String delimiter = ":";
        credentials = new HashMap<>();
        try(Stream<String> lines = Files.lines(Paths.get("src/main/resources/credentials.txt"))){
            lines.filter(line -> line.contains(delimiter)).forEach(
                    line -> credentials.putIfAbsent(line.split(delimiter)[0], line.split(delimiter)[1])
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> getCredentials(){
        return credentials;
    }
}
