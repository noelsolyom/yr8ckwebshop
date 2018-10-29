package com.training360.yr8ckwebshopapp.response;

public class Response {

    private String message;
    private boolean ok;

    public Response(String message, boolean ok) {
        if(message == null || message.trim().equals("")){
            throw new IllegalArgumentException("Response object message is invalid");
        }
        this.message = message.trim();
        this.ok = ok;
    }

    public void setMessage(String message) {
        if(message == null || message.trim().equals("")){
            throw new IllegalArgumentException("Response object message is invalid");
        }
        this.message = message.trim();
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getMessage() {
        return message;
    }

    public boolean isOk() {
        return ok;
    }
}
