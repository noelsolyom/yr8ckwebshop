package com.training360.yr8ckwebshopapp.ui;

import com.training360.yr8ckwebshopapp.bl.BasketService;
import com.training360.yr8ckwebshopapp.model.Product.Product;
import com.training360.yr8ckwebshopapp.response.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class BasketController {

    private BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    //      http://localhost:8080/basket?userId=1&productId=1
    @RequestMapping(value = "/basket", method = RequestMethod.POST)
    public Response addProductToUsersBasket(@RequestParam long userId, @RequestParam long productId) {
        return basketService.addProductToUsersBasket(userId, productId);
    }

    //      http://localhost:8080/basket/delete?userId=2&productId=1
    @RequestMapping(value = "/basket/delete", method = RequestMethod.DELETE)
    public Response deleteProductFromUsersBasket(@RequestParam long userId, @RequestParam long productId){
        return basketService.deleteProductFromUsersBasket(userId, productId);
    }

    //      http://localhost:8080/basket?userId=1
    @RequestMapping(value = "/basket", method = RequestMethod.DELETE)
    public Response emptyUsersBasket(@RequestParam long userId) {
        return basketService.emptyUsersBasket(userId);
    }

    //      http://localhost:8080/basket?userId=2
    @RequestMapping(value = "/basket", method = RequestMethod.GET)
    public List<Product> listBasketProductsByUserId(@RequestParam long userId) {
        return basketService.listBasketProductsByUserId(userId);
    }
}
