package agency.illiaderhun.com.github.model.utils;

import org.apache.log4j.Logger;

import java.security.MessageDigest;

/**
 * PasswordEncoder is a password hashing class
 * based on the sha-256 cipher
 *
 * @author Illia Derhun
 * @version 1.0
 */
public class PasswordEncoder {

    private static final Logger LOGGER = Logger.getLogger(PasswordEncoder.class.getSimpleName());

    /**
     * Code the string by sha-256 cipher and return it coded
     *
     * @param base string for crypt
     * @return coded base
     */
    public static String encodeIt(String base) {
        LOGGER.info("encodeIt start with base: " + base);
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            LOGGER.info("encodeIt return: " + hexString.toString());
            return hexString.toString();
        } catch(Exception ex){
            LOGGER.error("encodeId caught Exception");
            LOGGER.trace(ex);
            throw new RuntimeException(ex);
        }
    }
}
