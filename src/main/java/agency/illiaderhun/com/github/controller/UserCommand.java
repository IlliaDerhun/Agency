package agency.illiaderhun.com.github.controller;

import agency.illiaderhun.com.github.controller.service.impl.UserControllerHelper;
import agency.illiaderhun.com.github.model.dao.UserJdbcDao;
import agency.illiaderhun.com.github.model.entities.User;
import org.apache.log4j.Logger;

/**
 * UserCommand work with {@link User} through {@link UserControllerHelper}
 * Send to {@link UserJdbcDao} readByEmail(email) and return it
 *
 * @author Illia Derhun
 * @version 1.0
 */
public class UserCommand {

    private static final Logger LOGGER = Logger.getLogger(UserCommand.class.getSimpleName());

    private UserControllerHelper userControllerHelper = new UserControllerHelper();

    public User validateUserByEmailPassword(String email, String password) {
        LOGGER.info("findUserInDB email: " + email + " pass: " + password);

        User theUser = userControllerHelper.byEmailFindUserInDB(email, password);

        return theUser;
    }

    public String readNameByUserId(Integer userId) {
        LOGGER.info("byIdFindUserInDB userId: " + userId);

        String name = userControllerHelper.findUserNameById(userId);

        return name;
    }

    public User getUserInfoById(Integer userId){
        LOGGER.info("getUserInfoId start with userId: " + userId);
        return new UserControllerHelper().readUserById(userId);
    }

    public User createNewUser(String email, String password, String name, String surname, String phone) {
        LOGGER.info("createNewUser start");

        User theUser = userControllerHelper.createNewUser(email, password, name, surname, phone);

        LOGGER.info("createNewUser return " + theUser);
        return theUser;
    }

    public void updateRoleIdForUser(Integer userId, Integer roleId){
        User theUser = getUserInfoById(userId);
        theUser.setRoleId(roleId);

        userControllerHelper.updateUser(theUser);
    }
}
