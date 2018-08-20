package agency.illiaderhun.com.github.model.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordEncoderTest {

    @Test
    public void encodeItReturnValidEncodedPassword() {
        String expected = "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3"; //123
        String actual = PasswordEncoder.encodeIt("123");
        assertEquals(expected, actual);
    }

    @Test
    public void encodeItReturnInValidEncodedPassword() {
        String expected = "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3"; //123
        String actual = PasswordEncoder.encodeIt("1234");
        assertNotEquals(expected, actual);
    }
}