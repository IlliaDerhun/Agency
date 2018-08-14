package agency.illiaderhun.com.github.model;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

public class QueriesManager {

    private static final Logger LOGGER = Logger.getLogger(QueriesManager.class.getSimpleName());

    public static Properties getProperties(String forWhom){
        LOGGER.info("getProperties start find property for " + forWhom);

        String pathToProperties = "queries/"+ forWhom + "/queries.properties";

        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        Properties properties = new Properties();

        try(InputStream resourceStream = loader.getResourceAsStream(pathToProperties)) {
            properties.load(resourceStream);
            LOGGER.info("Property has been loaded");
        } catch (FileNotFoundException ex){
            LOGGER.info("Property caught FileNotFoundException");
            ex.printStackTrace();
        }
        catch (IOException e) {
            LOGGER.info("Property caught IOException");
            e.printStackTrace();
        }

        LOGGER.info("Property return properties " + properties);
        return properties;
    }
}
