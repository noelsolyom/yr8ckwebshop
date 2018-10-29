package com.training360.yr8ckwebshopapp;

import com.training360.yr8ckwebshopapp.response.Response;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestResponse {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void normalInit() {
        Response response = new Response("ok", true);
        assertEquals("ok", response.getMessage());
        assertTrue(response.isOk());
    }

    @Test
    public void normalInitTrim() {
        Response response = new Response(" ok ", false);
        assertEquals("ok", response.getMessage());
        assertTrue(!response.isOk());
    }

    @Test
    public void setThenGetAttributes() {
        Response response = new Response("ok", true);
        response.setMessage("Error");
        response.setOk(false);
        assertEquals("Error", response.getMessage());
        assertTrue(!response.isOk());
    }

    @Test
    public void initWithNullMessage() {
        expectedException.expectMessage("Response object message is invalid");
        Response response = new Response(null, true);
        response.setMessage(null);
    }

    @Test
    public void setMessageNull() {
        expectedException.expectMessage("Response object message is invalid");
        Response response = new Response("ok", true);
        response.setMessage(null);
    }
}
