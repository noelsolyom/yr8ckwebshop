package com.training360.yr8ckwebshopapp;

import com.training360.yr8ckwebshopapp.model.User.User;
import com.training360.yr8ckwebshopapp.model.User.UserRole;
import com.training360.yr8ckwebshopapp.model.User.UserStatus;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class TestUser {

    @Test
    public void normalInit() {
        User user = new User(
                1L,
                "user",
                UserRole.USER,
                "Family",
                "Given",
                UserStatus.ACTIVE);
        assertEquals(1L, user.getId());
        assertThat(user.getUserName(), equalTo("user"));
        assertThat(user.getPassword(), equalTo(null));
        assertThat(user.getUserRole(), equalTo(UserRole.USER));
        assertThat(user.getFamilyName(), equalTo("Family"));
        assertThat(user.getGivenName(), equalTo("Given"));
        assertThat(user.getUserStatus(), equalTo(UserStatus.ACTIVE));
    }

    @Test
    public void setThenGetAttributes() {
        User user = new User();
        user.setId(1L);
        user.setUserName("user");
        user.setPassword("password");
        user.setUserRole(UserRole.USER);
        user.setFamilyName("Family");
        user.setGivenName("Given");
        user.setUserStatus(UserStatus.DELETED);
        assertEquals(1L, user.getId());
        assertThat(user.getUserName(), equalTo("user"));
        assertThat(user.getPassword(), equalTo("password"));
        assertThat(user.getUserRole(), equalTo(UserRole.USER));
        assertThat(user.getFamilyName(), equalTo("Family"));
        assertThat(user.getGivenName(), equalTo("Given"));
        assertThat(user.getUserStatus(), equalTo(UserStatus.DELETED));
    }
}
