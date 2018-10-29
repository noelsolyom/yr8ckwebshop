package com.training360.yr8ckwebshopapp.response;

import com.training360.yr8ckwebshopapp.model.User.User;

public class UserResponse {
    private User user;
    private Response response;

    public UserResponse(User user, Response response) {
        this.user = user;
        this.response = response;
    }

    public User getUser() {
        return user;
    }

    public Response getResponse() {
        return response;
    }
}
