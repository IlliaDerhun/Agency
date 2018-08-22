package agency.illiaderhun.com.github.controller.service.impl;

import agency.illiaderhun.com.github.controller.UserCommand;
import agency.illiaderhun.com.github.controller.service.UserService;
import agency.illiaderhun.com.github.model.daoFactory.UserDaoFactory;
import agency.illiaderhun.com.github.model.daoInterface.UserDao;
import agency.illiaderhun.com.github.model.entities.User;
import agency.illiaderhun.com.github.model.exeptions.IdInvalidExcepiton;
import agency.illiaderhun.com.github.model.exeptions.InvalidSearchingStringException;
import agency.illiaderhun.com.github.model.utils.PasswordEncoder;
import org.apache.log4j.Logger;

/**
 * Help in work with UserDao for {@link UserCommand}
 *
 * @author Illia Derhun
 * @version 1.0
 */
public class UserControllerHelper implements UserService {

    private static final Logger LOGGER = Logger.getLogger(UserControllerHelper.class.getSimpleName());
    private UserDao<User, Integer> userDao = UserDaoFactory.getUser("mysql");

    /**
     * Validates user by email and password through comparing encoded passwords
     *
     * @param email validated and checked email
     * @param password validated and checked password
     * @return valid {@link User} in case it exist and password the same as in DB
     */
    @Override
    public User byEmailFindUserInDB(String email, String password) {
        LOGGER.info("findUserInDB email: " + email + " pass: " + password);
        User theUser = null;
        try {
            theUser = userDao.readByEmail(email);
            String encodedPassword = PasswordEncoder.encodeIt(password);
            if (theUser == null || !encodedPassword.equals(theUser.getCatchword())){
                theUser = null;
            }
        } catch (InvalidSearchingStringException invalidSearchingStringException) {
            theUser = null;
        }

        return theUser;
    }

    /**
     * Find {@link User} by userId and return it name
     *
     * @param userId for searching name by this id
     * @return valid name if something exist by this id
     */
    @Override
    public String findUserNameById(Integer userId) {
        LOGGER.info("byIdFindUserInDB userId: " + userId);
        String name = "Unknown";
        try {
            User theUser = userDao.read(userId);
            if (theUser != null){
                name = theUser.getFirstName();
            }
        } catch (IdInvalidExcepiton idInvalidExcepiton) {
            idInvalidExcepiton.printStackTrace();
        }

        return name;
    }

    @Override
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
            // If user exist return null
            userDao.readByEmail(email);
            user = null;
        } catch (InvalidSearchingStringException invalidSearchingStringException) {
            // If user doesn't exist try throw InvalidSearchingStringException
            LOGGER.info("it is new user");
            userDao.create(user);
            try {
                user = userDao.readByEmail(email);
            } catch (InvalidSearchingStringException invalidSearchingStringException1) {
                invalidSearchingStringException1.printStackTrace();
            }
        }

        LOGGER.info("createNewUser return " + user);
        return user;
    }

    /**
     * Find {@link User} by userId and return valid entity
     *
     * @param userId for searching entity by this id
     * @return valid {@link User} entity if it exist
     */
    @Override
    public User readUserById(Integer userId) {
        LOGGER.info("readUserById userId: " + userId);
        User theUser = null;
        try {
            theUser = userDao.read(userId);
        } catch (IdInvalidExcepiton idInvalidExcepiton) {
            idInvalidExcepiton.printStackTrace();
        }

        return theUser;
    }

    /**
     * Find and update current user
     *
     * @param theUser validated and already checked entity with updated fields
     */
    @Override
    public void updateUser(User theUser) {
        userDao.update(theUser);
    }

}
