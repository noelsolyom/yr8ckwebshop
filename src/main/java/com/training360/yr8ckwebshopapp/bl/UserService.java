package com.training360.yr8ckwebshopapp.bl;

import com.training360.yr8ckwebshopapp.db.OrderDao;
import com.training360.yr8ckwebshopapp.db.UserDao;
import com.training360.yr8ckwebshopapp.model.User.User;
import com.training360.yr8ckwebshopapp.response.UserResponse;
import com.training360.yr8ckwebshopapp.response.Response;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserDao userDao;
    private OrderDao orderDao;

    public UserService(UserDao userDao, OrderDao orderDao) {
        this.userDao = userDao;
        this.orderDao = orderDao;
    }

    public Response createUser(String userName, String password, String familyName, String givenName) {
        if (!validateInputData(userName, password, familyName, givenName).isOk()) {
            return validateInputData(userName, password, familyName, givenName);
        }
        if (findUserByUserName(userName).getResponse().isOk())
            return new Response(findUserByUserName(userName).getResponse().getMessage(), false);
        else return userDao.createUser(userName.trim(), password.trim(), familyName.trim(), givenName.trim());
    }

    public UserResponse findUserByUserName(String userName) {
        if (userDao.findUserByUserName(userName).isPresent())
            return new UserResponse(userDao.findUserByUserName(userName).get(),
                    new Response("User with the provided user name already exists.", true));
        else return new UserResponse(null, new Response("No such user with the user name provided.", false));
    }

    public List<User> listUsers() {
        return userDao.listUsers();
    }

    public Response updateUser(String password, String familyName, String givenName, long userId) {
        if (password == null) {
            return new Response("Password can not be null.", false);
        }
        if (userId < 1) {
            return new Response("User Id is invalid.", false);
        }
        if (password.trim().equals("")) {
            if (!validateInputData("x", "passWord1", familyName, givenName).isOk()) {
                return validateInputData("x", "passWord1", familyName, givenName);
            }
            if (userDao.findUserByUserId(userId).isPresent()){
                return userDao.updateUserWithoutPassword(familyName.trim(), givenName.trim(), userId);
            } else {
                return new Response("No user with id provided.", false);
            }

        } else {
            if (!validateInputData("x", password, familyName, givenName).isOk()) {
                return validateInputData("x", password, familyName, givenName);
            }
            if (userDao.findUserByUserId(userId).isPresent()){
                return userDao.updateUserWithPassword(password, familyName.trim(), givenName.trim(), userId);
            } else {
                return new Response("No user with id provided.", false);
            }
        }
    }

    public Response deleteUser(long userId) {
        if (userDao.findUserByUserId(userId).isPresent()) {
            orderDao.archiveOrdersByUserId(userId);
            return userDao.deleteUser(userId);
        } else {
            return new Response("No user with id provided.", false);
        }
    }

    private Response validateInputData(String userName, String password, String familyName, String givenName) {
        if (!userNameValidator(userName).isOk()) return userNameValidator(userName);
        if (!familyNameValidator(familyName).isOk()) return familyNameValidator(familyName);
        if (!givenNameValidator(givenName).isOk()) return givenNameValidator(givenName);
        if (!passwordValidator(password).isOk()) return passwordValidator(password);
        else return new Response("Input fields are valid.", true);
    }

    private Response userNameValidator(String userName) {
        if (userName == null || userName.trim().length() == 0)
            return new Response("Please fill user name.", false);
        if (userName.matches("^[0-9].+"))
            return new Response("User name cannot start with a digit.", false);
        else return new Response("ok", true);
    }

    private Response familyNameValidator(String familyName) {
        if (familyName == null || familyName.trim().length() == 0)
            return new Response("Please fill family name.", false);
        else return new Response("ok", true);
    }

    private Response givenNameValidator(String givenName) {
        if (givenName == null || givenName.trim().length() == 0)
            return new Response("Please fill given name.", false);
        else return new Response("ok", true);
    }

    private Response passwordValidator(String password) {
        if (password == null)
            return new Response("Please fill password.", false);
        if (password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"))
            return new Response("Password is fine", true);
        else return new Response(
                "Password must be at least 8 characters long and must " +
                        "contain at least one each of the followings: " +
                        "digit, lower case letter, upper case letter. No whitespace allowed.", false);
    }
}
