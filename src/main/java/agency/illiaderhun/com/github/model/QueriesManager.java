package agency.illiaderhun.com.github.model;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

/**
 * Create properties with queries for entities
 *
 * @author Illia Derhun
 * @version 1.0
 */
public class QueriesManager {

    private static final Logger LOGGER = Logger.getLogger(QueriesManager.class.getSimpleName());

    /**
     * Build and read path to properties
     * Load properties by built path
     *
     * @param forWhom entities's name for whom needs queries
     * @return properties with accurate queries
     */
    public static Properties getProperties(String forWhom){
        LOGGER.info("getProperties start find property for " + forWhom);

        String pathToProperties = "queries/"+ forWhom + "/queries.properties";
        LOGGER.info("built path to prop: " + pathToProperties);
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        Properties properties = new Properties();

        try(InputStream resourceStream = loader.getResourceAsStream(pathToProperties)) {
            properties.load(resourceStream);
            LOGGER.error("Property has been loaded");
        } catch (FileNotFoundException ex){
            LOGGER.info("Property caught FileNotFoundException");
            LOGGER.trace(ex);
            ex.printStackTrace();
        } catch (IOException e) {
            LOGGER.error("Property caught IOException");
            LOGGER.trace(e);
            e.printStackTrace();
        }

        LOGGER.info("Property return properties " + properties);
        return properties;
    }
}
