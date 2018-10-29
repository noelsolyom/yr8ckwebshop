package com.training360.yr8ckwebshopapp;

import com.training360.yr8ckwebshopapp.model.Basket.Basket;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestBasket {

    @Test
    public void normalInit() {
        Basket basket = new Basket(1,1,1);
        assertEquals(1L, basket.getId());
        assertEquals(1L, basket.getUserId());
        assertEquals(1L, basket.getProductId());
    }

    @Test
    public void setThenGetAttributes() {
        Basket basket = new Basket();
        basket.setId(10L);
        basket.setUserId(10L);
        basket.setProductId(10L);
        assertEquals(10L, basket.getId());
        assertEquals(10L, basket.getUserId());
        assertEquals(10L, basket.getProductId());

    }
}
