package com.training360.yr8ckwebshopapp.ui;

import com.training360.yr8ckwebshopapp.bl.ProductService;
import com.training360.yr8ckwebshopapp.model.Product.Product;
import com.training360.yr8ckwebshopapp.response.ProductResponse;
import com.training360.yr8ckwebshopapp.response.Response;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //      http://localhost:8080/products/galaxy-s8
    @RequestMapping(value = "/products/{url}", method = RequestMethod.GET)
    public ProductResponse findProductByUrl(@PathVariable String url) {
        return (productService.findProductByUrl(url));
    }

    //      http://localhost:8080/products/
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<Product> listProducts() {
        return productService.listProducts();
    }

    //      http://localhost:8080/products/1/2
    @RequestMapping(value = "/products/{offset}/{limit}", method = RequestMethod.GET)
    public List<Product> scrollListProducts(@PathVariable long offset, @PathVariable long limit) {
            return productService.scrollListProducts(offset, limit);
    }

//    {
//        "number": 10,
//        "name": "Motorola A1",
//        "url": "motorola-a1",
//        "manufacturer": "Motorola",
//        "currentPrice": 100
//    }
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public Response createProduct(@RequestBody Product product) {
        return productService.createProduct(
                product.getNumber(),
                product.getName(),
                product.getUrl(),
                product.getManufacturer(),
                product.getCurrentPrice()
        );
    }

//    {
//        "number": 20,
//        "name": "Motorola A2",
//        "url": "motorola-a2",
//        "manufacturer": "Motorola",
//        "currentPrice": 200
//    }
    @RequestMapping(value = "/products/{productId}", method = RequestMethod.POST)
    public Response updateProductById(@RequestBody Product product, @PathVariable long productId) {
        return productService.updateProduct(
                product.getNumber(),
                product.getName(),
                product.getUrl(),
                product.getManufacturer(),
                product.getCurrentPrice(), productId
        );
    }

    //    http://localhost:8080/products/4
    @RequestMapping(value = "/products/{productId}", method = RequestMethod.DELETE)
    public Response deleteProductById(@PathVariable long productId) {
        return productService.deleteProductById(productId);
    }

}
