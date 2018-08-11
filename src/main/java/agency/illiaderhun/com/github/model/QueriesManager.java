package agency.illiaderhun.com.github.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class QueriesManager {

    public static Properties getProperties(String forWhom){

        Properties properties = new Properties();

        StringBuffer pathToProperties = new StringBuffer("src/main/resources/queries/" + forWhom + "/queries.properties");

        try (FileInputStream fileInputStream = new FileInputStream(String.valueOf(pathToProperties))){
            properties.load(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
