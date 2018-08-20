package agency.illiaderhun.com.github.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class QueriesManagerTest {

    @Test
    public void getPropertiesForUserReturnValidQuery() {
        String expected = "SELECT * FROM agency_test.user WHERE user_id = ?;";
        String actual = QueriesManager.getProperties("user").getProperty("select");
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void getPropertiesForInvalidEntityThrowNPE() {
        QueriesManager.getProperties("invalid");
    }
}