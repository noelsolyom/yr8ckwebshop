package com.training360.yr8ckwebshopapp.ui;

import com.training360.yr8ckwebshopapp.bl.UserService;
import com.training360.yr8ckwebshopapp.model.User.User;
import com.training360.yr8ckwebshopapp.response.Response;
import com.training360.yr8ckwebshopapp.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //      http://localhost:8080/user
    @RequestMapping("/user")
    public User getUser(Authentication authentication){
        if (authentication == null) {
            return new User(); }
        else {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String user_name = userDetails.getUsername();
            String role =  new ArrayList<GrantedAuthority>(userDetails.getAuthorities()).get(0)
                    .getAuthority();
            //TODO: visszateres u-val;
            User u = userService.findUserByUserName(user_name).getUser();

            return new User(u.getId(), u.getUserName(), u.getUserRole(), u.getFamilyName(), u.getGivenName(), u.getUserStatus());
        }
    }

//    {
//        "userName": "johndoe",
//         "password": "passWord1",
//        "familyName": "John",
//        "givenName": "Doe"
//    }
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public Response createUser(@RequestBody User user) {
        return userService.createUser(
                user.getUserName(),
                user.getPassword(),
                user.getFamilyName(),
                user.getGivenName());
    }

    //      http://localhost:8080/users
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> listUsers() {
        return userService.listUsers();
    }

//    {
//        "password": "stronPass2",
//         "familyName": "Jim",
//         "givenName": "Doe"
//    }
    @RequestMapping(value = "/users/{userId}", method = RequestMethod.POST)
    public Response updateUser(@RequestBody User user, @PathVariable long userId) {
        return userService.updateUser(
                user.getPassword(),
                user.getFamilyName(),
                user.getGivenName(), userId
        );
    }

    //      http://localhost:8080/users/3
    @RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
    public Response deleteUser(@PathVariable long userId) {
        return userService.deleteUser(userId);
    }

    //      http://localhost:8080/users/search/admin
    @RequestMapping(value = "/users/search/{userName}", method = RequestMethod.GET)
    public UserResponse findUserByUserName(@PathVariable String userName) {
        return userService.findUserByUserName(userName);
    }

}
