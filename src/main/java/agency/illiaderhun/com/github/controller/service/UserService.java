package agency.illiaderhun.com.github.controller.service;

import agency.illiaderhun.com.github.model.entities.User;

public interface UserService {

    User byEmailFindUserInDB(String email, String password);
    String findUserNameById(Integer userId);
    User createNewUser(String email, String password, String name, String surname, String phone);
    User readUserById(Integer userId);
    void updateUser(User theUser);

}
