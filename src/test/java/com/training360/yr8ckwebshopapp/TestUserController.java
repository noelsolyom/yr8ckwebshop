package com.training360.yr8ckwebshopapp;

import com.training360.yr8ckwebshopapp.model.User.User;
import com.training360.yr8ckwebshopapp.ui.UserController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "classpath:/clearDatabase.sql")
public class TestUserController {

    private Authentication authentication;

    @Autowired
    private UserController userController;
    private User newUser = new User ( "lagzi", "Daaridoo1",  "Daari", "Doe");
    private User newUser2 = new User ( "lagzi2", "Daaridoo1",  "Daari", "Doe");
    private User newUser3 = new User ( " lagzi3 ", "Daaridoo1",  " Daari ", " Doe ");

    @Test
    public void createNewUser() {
        assertTrue(userController.createUser(newUser).isOk());
        assertEquals("User has been created.", userController.createUser(newUser2).getMessage());
    }

    @Test
    public void createNewUserTrim() {
        assertTrue(userController.createUser(newUser3).isOk());
        assertTrue(userController.findUserByUserName("lagzi3").getResponse().isOk());
    }

    @Test
    public void createSameUser() {
        assertTrue(userController.createUser(newUser).isOk());
        assertFalse(userController.createUser(newUser).isOk());
        assertEquals("User with the provided user name already exists.", userController.createUser(newUser).getMessage());
    }

    @Test
    public void createNewStartZero() {
        assertFalse(userController.createUser(new User ( "0A", "Daaridoo1",  "Daari", "Doe")).isOk());
        assertFalse(userController.createUser(new User ( "0A", "Daaridoo1",  "Daari", "Doe")).isOk());
        assertEquals("User name cannot start with a digit.", userController.createUser(new User ( "0A", "Daaridoo1",  "Daari", "Doe")).getMessage());
        assertEquals("User name cannot start with a digit.", userController.createUser(new User ( "0A", "Daaridoo1",  "Daari", "Doe")).getMessage());
    }

    @Test
    public void createNewWithoutUserName() {
        assertFalse(userController.createUser(new User ( "", "Daaridoo1",  "Daari", "Doe")).isOk());
        assertFalse(userController.createUser(new User ( null, "Daaridoo1",  "Daari", "Doe")).isOk());
        assertEquals("Please fill user name.", userController.createUser(new User ( "", "Daaridoo1",  "Daari", "Doe")).getMessage());
        assertEquals("Please fill user name.", userController.createUser(new User ( null, "Daaridoo1",  "Daari", "Doe")).getMessage());
    }

    @Test
    public void createNewStartingWithNumber() {
        assertFalse(userController.createUser(new User ( "0lajcsi", "Daaridoo1",  "Daari", "Doe")).isOk());
        assertEquals("User name cannot start with a digit.", userController.createUser(new User ( "0lajcsi", "Daaridoo1",  "Daari", "Doe")).getMessage());
        }

    @Test
    public void createNewWithoutPassword() {
        assertFalse(userController.createUser(new User ( "lajcsi", " ",  "Daari", "Doe")).isOk());
        assertFalse(userController.createUser(new User ( "lajcsi", null,  "Daari", "Doe")).isOk());
        assertEquals("Password must be at least 8 characters long and must contain at least one each of the followings: digit, lower case letter, upper case letter. No whitespace allowed.", userController.createUser(new User ( "lajcsi", "",  "Daari", "Doe")).getMessage());
        assertEquals("Please fill password.", userController.createUser(new User ( "lajcsi", null,  "Daari", "Doe")).getMessage());
    }

    @Test
    public void createNewWrongPassword() {
        assertFalse(userController.createUser(new User ( "lajcsi", "a",  "Daari", "Doe")).isOk());
        assertFalse(userController.createUser(new User ( "lajcsi", "aaaaaaaa",  "Daari", "Doe")).isOk());
        assertFalse(userController.createUser(new User ( "lajcsi", "AAAAAAAA",  "Daari", "Doe")).isOk());
        assertFalse(userController.createUser(new User ( "lajcsi", "aAa1",  "Daari", "Doe")).isOk());
        assertFalse(userController.createUser(new User ( "lajcsi", "passWord 1",  "Daari", "Doe")).isOk());

        assertEquals("Password must be at least 8 characters long and must contain at least one each of the followings: digit, lower case letter, upper case letter. No whitespace allowed.", userController.createUser(new User ( "lajcsi", "a",  "Daari", "Doe")).getMessage());
        assertEquals("Password must be at least 8 characters long and must contain at least one each of the followings: digit, lower case letter, upper case letter. No whitespace allowed.", userController.createUser(new User ( "lajcsi", "aaaaaaaa",  "Daari", "Doe")).getMessage());
        assertEquals("Password must be at least 8 characters long and must contain at least one each of the followings: digit, lower case letter, upper case letter. No whitespace allowed.", userController.createUser(new User ( "lajcsi", "AAAAAAAA",  "Daari", "Doe")).getMessage());
        assertEquals("Password must be at least 8 characters long and must contain at least one each of the followings: digit, lower case letter, upper case letter. No whitespace allowed.", userController.createUser(new User ( "lajcsi", "aAa1",  "Daari", "Doe")).getMessage());
        assertEquals("Password must be at least 8 characters long and must contain at least one each of the followings: digit, lower case letter, upper case letter. No whitespace allowed.", userController.createUser(new User ( "lajcsi", "passWord 1",  "Daari", "Doe")).getMessage());
    }

    @Test
    public void createNewWithoutFamilyName() {
        assertFalse(userController.createUser(new User ( "lajcsi", "Daaridoo1",  "", "Doe")).isOk());
        assertFalse(userController.createUser(new User ( "lajcsi", "Daaridoo1",  null, "Doe")).isOk());
        assertEquals("Please fill family name.", userController.createUser(new User ( "lajcsi", "Daaridoo1",  "", "Doe")).getMessage());
        assertEquals("Please fill family name.", userController.createUser(new User ( "lajcsi", "Daaridoo1",  null, "Doe")).getMessage());
    }

    @Test
    public void createNewWithoutGivenName() {
        assertFalse(userController.createUser(new User ( "lajcsi", "Daaridoo1",  "Daari", "")).isOk());
        assertFalse(userController.createUser(new User ( "lajcsi", "Daaridoo1",  "Daari", null)).isOk());
        assertEquals("Please fill given name.", userController.createUser(new User ( "lajcsi", "Daaridoo1",  "Daari", "")).getMessage());
        assertEquals("Please fill given name.", userController.createUser(new User ( "lajcsi", "Daaridoo1",  "Daari", null)).getMessage());

    }

    @Test
    public void listUsers() {
        assertEquals(2, userController.listUsers().size());
        userController.createUser(new User ( "lagzi", "Daaridoo1",  "Daari", "Doe"));
        assertEquals(3, userController.listUsers().size());
    }

    @Test
    public void updateUserValid() {
        userController.createUser(newUser);
        long id = userController.findUserByUserName("lagzi").getUser().getId();
        assertEquals(userController.updateUser(new User("asdfASDF1", "yyy", "zzz"), id).getMessage(), "User update succesfull.");
        assertEquals(userController.findUserByUserName("lagzi").getUser().getFamilyName(), "yyy");
        assertEquals(userController.updateUser(new User("", "AAA", "BBB"), id).getMessage(), "User update succesfull.");
        assertEquals(userController.findUserByUserName("lagzi").getUser().getGivenName(), "BBB");
    }

    @Test
    public void updateUserInvalidId() {
        assertEquals(userController.updateUser(new User("asdfASDF1", "yyy", "zzz"), 10000).getMessage(), "No user with id provided.");
        assertEquals(userController.updateUser(new User("asdfASDF1", "yyy", "zzz"), 10000).getMessage(), "No user with id provided.");
        assertEquals(userController.updateUser(new User("asdfASDF1", "yyy", "zzz"), 10000).isOk(), false);
        assertEquals(userController.updateUser(new User("asdfASDF1", "yyy", "zzz"), 10000).isOk(), false);
    }

    @Test
    public void updateUserInvalidId2() {
        assertEquals(userController.updateUser(new User("", "yyy", "zzz"), 10000).getMessage(), "No user with id provided.");
        assertEquals(userController.updateUser(new User("", "yyy", "zzz"), 10000).getMessage(), "No user with id provided.");
        assertEquals(userController.updateUser(new User("", "yyy", "zzz"), 10000).isOk(), false);
        assertEquals(userController.updateUser(new User("", "yyy", "zzz"), 10000).isOk(), false);
    }

    @Test
    public void updateUserInvalidFamilyNameWithPasswordIdPresent() {
        userController.createUser(newUser);
        long id = userController.findUserByUserName("lagzi").getUser().getId();
        assertEquals(userController.updateUser(new User("asdfASDF1", "", "zzz"), id).getMessage(), "Please fill family name.");
        assertEquals(userController.updateUser(new User("asdfASDF1", null, "zzz"), id).getMessage(), "Please fill family name.");
        assertEquals(userController.updateUser(new User("asdfASDF1", "", "zzz"), id).isOk(), false);
        assertEquals(userController.updateUser(new User("asdfASDF1", null, "zzz"), id).isOk(), false);
    }

    @Test
    public void updateUserInvalidGivenNameWithPasswordIdPresent() {
        userController.createUser(newUser);
        long id = userController.findUserByUserName("lagzi").getUser().getId();
        assertEquals(userController.updateUser(new User("asdfASDF1", "xxx", ""), id).getMessage(), "Please fill given name.");
        assertEquals(userController.updateUser(new User("asdfASDF1", "xxx", null), id).getMessage(), "Please fill given name.");
        assertEquals(userController.updateUser(new User("asdfASDF1", "xxx", ""), id).isOk(), false);
        assertEquals(userController.updateUser(new User("asdfASDF1", "xxx", null), id).isOk(), false);
    }

    @Test
    public void updateUserInvalidFamilyNameWithoutPasswordIdNotPresent() {
        userController.createUser(newUser);
        assertEquals(userController.updateUser(new User("", "", "zzz"), 0).getMessage(), "User Id is invalid.");
        assertEquals(userController.updateUser(new User("", null, "zzz"), 0).getMessage(), "User Id is invalid.");
        assertEquals(userController.updateUser(new User("", "", "zzz"), 0).isOk(), false);
        assertEquals(userController.updateUser(new User("", null, "zzz"), 0).isOk(), false);
    }

    @Test
    public void updateUserInvalidGivenNameWithoutPasswordIdNotPresent() {
        userController.createUser(newUser);
        assertEquals(userController.updateUser(new User("", "xxx", ""), 0).getMessage(), "User Id is invalid.");
        assertEquals(userController.updateUser(new User("", "xxx", null), 0).getMessage(), "User Id is invalid.");
        assertEquals(userController.updateUser(new User("", "xxx", ""), 0).isOk(), false);
        assertEquals(userController.updateUser(new User("", "xxx", null), 0).isOk(), false);
    }

    @Test
    public void updateUserInvalidFamilyNameWithPasswordIdNotPresent() {
        userController.createUser(newUser);
        assertEquals(userController.updateUser(new User("asdfASDF1", "", "zzz"), 0).getMessage(), "User Id is invalid.");
        assertEquals(userController.updateUser(new User("asdfASDF1", null, "zzz"), 0).getMessage(), "User Id is invalid.");
        assertEquals(userController.updateUser(new User("asdfASDF1", "", "zzz"), 0).isOk(), false);
        assertEquals(userController.updateUser(new User("asdfASDF1", null, "zzz"), 0).isOk(), false);
    }

    @Test
    public void updateUserInvalidGivenNameWithPasswordIdNotPresent() {
        userController.createUser(newUser);
        assertEquals(userController.updateUser(new User("asdfASDF1", "xxx", ""), 0).getMessage(), "User Id is invalid.");
        assertEquals(userController.updateUser(new User("asdfASDF1", "xxx", null), 0).getMessage(), "User Id is invalid.");
        assertEquals(userController.updateUser(new User("asdfASDF1", "xxx", ""), 0).isOk(), false);
        assertEquals(userController.updateUser(new User("asdfASDF1", "xxx", null), 0).isOk(), false);
    }

    @Test
    public void updateUserInvalidFamilyNameWithoutPasswordIdPresent() {
        userController.createUser(newUser);
        long id = userController.findUserByUserName("lagzi").getUser().getId();
        assertEquals(userController.updateUser(new User("", "", "zzz"), id).getMessage(), "Please fill family name.");
        assertEquals(userController.updateUser(new User("", null, "zzz"), id).getMessage(), "Please fill family name.");
        assertEquals(userController.updateUser(new User("", "", "zzz"), id).isOk(), false);
        assertEquals(userController.updateUser(new User("", null, "zzz"), id).isOk(), false);
    }

    @Test
    public void updateUserInvalidGivenNameWithoutPasswordIdPresent() {
        userController.createUser(newUser);
        long id = userController.findUserByUserName("lagzi").getUser().getId();
        assertEquals(userController.updateUser(new User("", "xxx", ""), id).getMessage(), "Please fill given name.");
        assertEquals(userController.updateUser(new User("", "xxx", null), id).getMessage(), "Please fill given name.");
        assertEquals(userController.updateUser(new User("", "xxx", ""), id).isOk(), false);
        assertEquals(userController.updateUser(new User("", "xxx", null), id).isOk(), false);
    }

    @Test
    public void updateUserInvalidPassword() {
        userController.createUser(newUser);
        long id = userController.findUserByUserName("lagzi").getUser().getId();
        String message = "Password must be at least 8 characters long and must contain at least one each of the followings: digit, lower case letter, upper case letter. No whitespace allowed.";

        assertEquals(userController.updateUser(new User(null, "yyy", "zzz"), id).getMessage(), "Password can not be null.");
        assertEquals(userController.updateUser(new User("a", "passWord 1", "zzz"), id).getMessage(), message);
        assertEquals(userController.updateUser(new User("a", "yyy", "zzz"), id).getMessage(), message);
        assertEquals(userController.updateUser(new User("a1", "yyy", "zzz"), id).getMessage(), message);
        assertEquals(userController.updateUser(new User("a1B", "yyy", "zzz"), id).getMessage(), message);
        assertEquals(userController.updateUser(new User("xxxxxxxxxxx", "yyy", "zzz"), id).getMessage(), message);
        assertEquals(userController.updateUser(new User("xxxxxxxxxxxA", "yyy", "zzz"), id).getMessage(), message);
        assertEquals(userController.updateUser(new User("XXXXXXXXXXXX1", "yyy", "zzz"), id).getMessage(), message);
        assertEquals(userController.updateUser(new User("htdADG4", "yyy", "zzz"), id).getMessage(), message);
        assertEquals(userController.updateUser(new User("passWord 1", "yyy", "zzz"), id).getMessage(), message);

        assertEquals(userController.updateUser(new User(null, "yyy", "zzz"), id).isOk(), false);
        assertEquals(userController.updateUser(new User("a", "yyy", "zzz"), id).isOk(), false);
        assertEquals(userController.updateUser(new User("a1", "yyy", "zzz"), id).isOk(), false);
        assertEquals(userController.updateUser(new User("a1B", "yyy", "zzz"), id).isOk(), false);
        assertEquals(userController.updateUser(new User("xxxxxxxxxxx", "yyy", "zzz"), id).isOk(), false);
        assertEquals(userController.updateUser(new User("xxxxxxxxxxxA", "yyy", "zzz"), id).isOk(), false);
        assertEquals(userController.updateUser(new User("XXXXXXXXXXXX1", "yyy", "zzz"), id).isOk(), false);
        assertEquals(userController.updateUser(new User("htdADG4", "yyy", "zzz"), id).isOk(), false);
        assertEquals(userController.updateUser(new User("passWord 1", "yyy", "zzz"), id).isOk(), false);

    }

    @Test
    public void deleteUser() {
        userController.createUser(newUser);
        long id = userController.findUserByUserName("lagzi").getUser().getId();
        assertEquals(userController.deleteUser(id).getMessage(), "User successfully deleted.");
        userController.createUser(newUser);
        long id2 = userController.findUserByUserName("lagzi").getUser().getId();
        assertEquals(userController.deleteUser(id2).isOk(), true);
    }

    @Test
    public void deleteAlreadyDeletedUser() {
        userController.createUser(newUser);
        long id = userController.findUserByUserName("lagzi").getUser().getId();
        userController.deleteUser(id);
        assertEquals(userController.deleteUser(id).getMessage(), "No user with id provided.");
        assertEquals(userController.deleteUser(id).isOk(), false);
    }

    @Test
    public void deletInexistingUser() {
        userController.deleteUser(1L);
        assertEquals(userController.deleteUser(1L).getMessage(), "No user with id provided.");
        assertEquals(userController.deleteUser(1l).isOk(), false);
    }

    @Test
    public void findUser() {
        userController.createUser(newUser);
        assertEquals("User with the provided user name already exists.", userController.findUserByUserName("lagzi").getResponse().getMessage());
        assertEquals(userController.findUserByUserName("lagzi").getResponse().isOk(), true);
    }

    @Test
    public void findInexistingUser() {
        userController.createUser(newUser);
        assertEquals(userController.findUserByUserName("jhkg").getResponse().getMessage(), "No such user with the user name provided.");
        assertEquals(userController.findUserByUserName("lahjkgzi").getResponse().isOk(), false);
    }

    @Test
    public void findNull() {
        userController.createUser(newUser);
        assertEquals(userController.findUserByUserName(null).getResponse().getMessage(), "No such user with the user name provided.");
        assertEquals(userController.findUserByUserName(null).getResponse().isOk(), false);
    }

    @Test
    public void getGuestUser() {
        assertThat(userController.getUser(authentication).getId(), is(0L));
        assertEquals(null, userController.getUser(authentication).getUserRole());
    }
}
