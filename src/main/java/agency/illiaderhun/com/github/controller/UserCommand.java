package agency.illiaderhun.com.github.controller;

import agency.illiaderhun.com.github.model.dao.UserJdbcDao;
import agency.illiaderhun.com.github.model.daoFactory.UserDaoFactory;
import agency.illiaderhun.com.github.model.daoInterface.UserDao;
import agency.illiaderhun.com.github.model.entities.User;
import agency.illiaderhun.com.github.model.exeptions.IdInvalid;
import agency.illiaderhun.com.github.model.exeptions.InvalidSearchingString;
import agency.illiaderhun.com.github.model.utils.PasswordEncoder;
import org.apache.log4j.Logger;

/**
 * UserCommand work with {@link User}
 * Send to {@link UserJdbcDao} readByEmail(email) and return it
 *
 * @author Illia Derhun
 * @version 1.0
 */
public class UserCommand {

    private static final Logger LOGGER = Logger.getLogger(UserCommand.class.getSimpleName());
    private UserDao<User, Integer> userDao = UserDaoFactory.getUser("mysql");

    public User byEmailFindUserInDB(String email, String password) {
        LOGGER.info("findUserInDB email: " + email + " pass: " + password);
        User theUser = null;
        try {
            theUser = userDao.readByEmail(email);
            String encodedPassword = PasswordEncoder.encodeIt(password);
            if (theUser == null || !encodedPassword.equals(theUser.getCatchword())){
                theUser = null;
            }
        } catch (InvalidSearchingString invalidSearchingString) {
            theUser = null;
        }

        return theUser;
    }

    public String findUserNameById(Integer userId) {
        LOGGER.info("byIdFindUserInDB userId: " + userId);
        String name = "Unknown";
        try {
            User theUser = userDao.read(userId);
            if (theUser != null){
                name = theUser.getFirstName();
            }
        } catch (IdInvalid idInvalid) {
            idInvalid.printStackTrace();
        }

        return name;
    }

    public User createNewUser(String email, String password, String name, String surname, String phone) {
        LOGGER.info("createNewUser start");
        String encodPass = PasswordEncoder.encodeIt(password);
        User user = new User.Builder(0, 1, email)
                .catchword(encodPass)
                .firstName(name)
                .lastName(surname)
                .phone(phone)
                .build();

        try {
            userDao.readByEmail(email);
            user = null;
        } catch (InvalidSearchingString invalidSearchingString) {
            LOGGER.info("it is new user");
            userDao.create(user);
            try {
                user = userDao.readByEmail(email);
            } catch (InvalidSearchingString invalidSearchingString1) {
                invalidSearchingString1.printStackTrace();
            }
        }

        LOGGER.info("createNewUser return " + user);
        return user;
    }
}
