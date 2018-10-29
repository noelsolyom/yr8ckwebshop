package com.training360.yr8ckwebshopapp.bl;

import com.training360.yr8ckwebshopapp.db.BasketDao;
import com.training360.yr8ckwebshopapp.db.ProductDao;
import com.training360.yr8ckwebshopapp.db.UserDao;
import com.training360.yr8ckwebshopapp.model.Product.Product;
import com.training360.yr8ckwebshopapp.response.Response;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BasketService {

    private BasketDao basketDao;
    private ProductDao productDao;
    private UserDao userDao;

    public BasketService(BasketDao basketDao, ProductDao productDao, UserDao userDao) {
        this.basketDao = basketDao;
        this.productDao = productDao;
        this.userDao = userDao;
    }

    public Response addProductToUsersBasket(long userId, long productId) {
        if (!checkParams(userId, productId).isOk()) {
            return checkParams(userId, productId);
        }
        if(productIsInBaset(userId, productId).isOk()) {
            return new Response("Product is already in basket.", false);
        }
        return basketDao.addProductToUsersBasket(userId, productId);
    }

    public Response deleteProductFromUsersBasket(long userId, long productId) {
        if (!checkParams(userId, productId).isOk()) {
            return checkParams(userId, productId);
        }
        if (!productIsInBaset(userId, productId).isOk()) {
            return productIsInBaset(userId, productId);
        }
        return basketDao.deleteProductFromUsersBasket(userId, productId);
    }

    public Response emptyUsersBasket(long userId){
        if (!checkUserId(userId).isOk()) {
            return checkUserId(userId);
        }
        if (basketDao.listBasketProductsByUserId(userId).size() < 1) {
            return new Response("Basket is already empty.", false);
        }
        return basketDao.emptyUsersBasket(userId); }

    public List<Product> listBasketProductsByUserId(long userId){
        return basketDao.listBasketProductsByUserId(userId);
    }

    private Response checkParams(long userId, long productId) {
        if (!checkUserId(userId).isOk()){ return checkUserId(userId); }
        if (!checkProductId(productId).isOk()){ return checkProductId(productId); }
        return new Response("Params are valid.", true);
    }
    private Response checkUserId(long userId) {
        if (userId < 1 ) {
            return new Response("Invalid user Id.", false);
        }
        if (!userDao.findUserByUserId(userId).isPresent()){
            return new Response("User does not exist.", false);
        }
        return new Response("User is present", true);
    }

    private Response checkProductId(long productId) {
        if (productId < 0 ) {
            return new Response("Invalid product Id.", false);
        }
        if (!productDao.findProductById(productId).isPresent()){
            return new Response("Product does not exist.", false);
        }
        return new Response("Product is present", true);
    }

    private Response productIsInBaset(long userId, long productId) {
        List<Product> usersProductsInBasket = listBasketProductsByUserId(userId);
        if (usersProductsInBasket.size() > 0) {
            for (Product p: usersProductsInBasket) {
                if (p.getId() == productId) {
                    return  new Response("Product is already in basket.", true);
                }
            }
        }
        return new Response("Product is not in users basket.", false);
    }
}
