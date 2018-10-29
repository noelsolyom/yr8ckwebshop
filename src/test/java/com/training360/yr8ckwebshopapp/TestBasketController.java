package com.training360.yr8ckwebshopapp;

import com.training360.yr8ckwebshopapp.ui.BasketController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "classpath:/clearDatabase.sql")
public class TestBasketController {

    @Autowired
    private BasketController basketController;

    @Test
    public void addItemToBasket() {
        assertTrue(basketController.addProductToUsersBasket(2, 1).isOk());
        assertEquals(basketController.addProductToUsersBasket(2, 5).getMessage(), "Item successfully added into basket.");
    }

    @Test
    public void addDuplicateItem() {
        basketController.deleteProductFromUsersBasket(1,1);
        basketController.addProductToUsersBasket(1, 1);
        assertFalse(basketController.addProductToUsersBasket(1, 1).isOk());
        assertEquals(basketController.addProductToUsersBasket(1, 1).getMessage(), "Product is already in basket.");
    }

    @Test
    public void addInvalidItem() {
        assertFalse(basketController.addProductToUsersBasket(1, 2000).isOk());
        assertEquals("Product does not exist.",basketController.addProductToUsersBasket(1, 20000).getMessage());

    }

    @Test
    public void addInvalidItemId() {
        assertFalse(basketController.addProductToUsersBasket(1, -1).isOk());
        assertEquals("Invalid product Id.", basketController.addProductToUsersBasket(1, -1).getMessage());
    }

    @Test
    public void addIntoInvalidUser() {
        assertFalse(basketController.addProductToUsersBasket(5000, 1).isOk());
        assertEquals("User does not exist.",basketController.addProductToUsersBasket(5000, 1).getMessage());
    }

    @Test
    public void addIntoinvalidUserId() {
        assertFalse(basketController.addProductToUsersBasket(-1, 1).isOk());
        assertEquals(basketController.addProductToUsersBasket(-1, 1).getMessage(), "Invalid user Id.");
    }

    @Test
    public void emptyUsersBasket() {
        basketController.emptyUsersBasket(1);
        basketController.addProductToUsersBasket(1, 1);
        basketController.addProductToUsersBasket(1, 2);
        assertEquals(2, basketController.listBasketProductsByUserId(1).size());
        assertTrue(basketController.emptyUsersBasket(1).isOk());
        basketController.emptyUsersBasket(1);
        basketController.addProductToUsersBasket(1, 1);
        basketController.addProductToUsersBasket(1, 2);
        assertEquals(basketController.emptyUsersBasket(1).getMessage(), "Basket is empty.");
    }

    @Test
    public void emptyemptyUsersBasket() {
        basketController.emptyUsersBasket(1);
        assertFalse(basketController.emptyUsersBasket(1).isOk());
        assertEquals(basketController.emptyUsersBasket(1).getMessage(), "Basket is already empty.");
    }

    @Test
    public void emptyInvalidUserBasket() {
        assertFalse(basketController.emptyUsersBasket(100).isOk());
        assertEquals(basketController.emptyUsersBasket(20000).getMessage(), "User does not exist.");

    }

    @Test
    public void emptyInvalidUserBasket2() {
        assertFalse(basketController.emptyUsersBasket(-1).isOk());
        assertEquals(basketController.emptyUsersBasket(-1).getMessage(), "Invalid user Id.");
    }

    @Test
    public void deleteProductFromBasket() {
        basketController.addProductToUsersBasket(1, 1);
        basketController.addProductToUsersBasket(1, 2);
        basketController.addProductToUsersBasket(1, 3);
        assertTrue(basketController.deleteProductFromUsersBasket(1,1).isOk());
        assertEquals(basketController.deleteProductFromUsersBasket(1,2).getMessage(), "Product has been deleted succesfully.");
    }

    @Test
    public void deleteNonExistentProduct() {
        basketController.deleteProductFromUsersBasket(1,1);
        assertFalse(basketController.deleteProductFromUsersBasket(1,1).isOk());
        assertEquals("Product is not in users basket.",basketController.deleteProductFromUsersBasket(1,1).getMessage());
    }

    @Test
    public void delteProductFromInvalidUser() {
        assertFalse(basketController.deleteProductFromUsersBasket(5000,1).isOk());
        assertEquals("User does not exist.",basketController.deleteProductFromUsersBasket(5000,1).getMessage());
    }

    @Test
    public void delteProductFromInvalidUser2() {
        assertFalse(basketController.deleteProductFromUsersBasket(-1,1).isOk());
        assertEquals("Invalid user Id.",basketController.deleteProductFromUsersBasket(-1,1).getMessage());
    }

    @Test
    public void deleteIllegalproduct() {
        assertFalse(basketController.deleteProductFromUsersBasket(1,-1).isOk());
        assertEquals(basketController.deleteProductFromUsersBasket(1,-1).getMessage(), "Invalid product Id.");
    }

    @Test
    public void listBasketProducts() {
        basketController.emptyUsersBasket(1);
        basketController.addProductToUsersBasket(1,1);
        basketController.addProductToUsersBasket(1,2);
        basketController.addProductToUsersBasket(1,3);
        assertEquals(3, basketController.listBasketProductsByUserId(1).size());
    }
}
