package com.training360.yr8ckwebshopapp.bl;

import com.training360.yr8ckwebshopapp.db.ProductDao;
import com.training360.yr8ckwebshopapp.model.Product.Product;
import com.training360.yr8ckwebshopapp.model.Product.ProductStatus;
import com.training360.yr8ckwebshopapp.response.ProductResponse;
import com.training360.yr8ckwebshopapp.response.Response;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    private ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Response createProduct(long number, String name, String url, String manufacturer, long currentPrice) {
        try {
            ProductService.validateInputData(number, name, url, manufacturer, currentPrice);
        } catch (IllegalArgumentException iae) {
            return new Response(iae.getMessage(), false);
        }
        if (findProductByNumber(number).isOk()) return new Response(findProductByNumber(number).getMessage(), false);
        if (findProductByUrl(url).getResponse().isOk())
            return new Response(findProductByUrl(url).getResponse().getMessage(), false);
        else return productDao.createProduct(number, name.trim(), url.trim(), manufacturer.trim(), currentPrice);
    }

    public ProductResponse findProductByUrl(String url) {
        if (productDao.findProductByUrl(url).isPresent())
            return new ProductResponse(productDao.findProductByUrl(url).get(),
                    new Response("Product with the provided URL already exists.", true));
        else return new ProductResponse(null, new Response("No such product with the URL provided.", false));
    }

    public List<Product> listProducts() {
        return productDao.listProducts();
    }

    public List<Product> scrollListProducts(long offset, long limit) {
        return productDao.scrollListProducts(offset, limit);
    }

    public Response updateProduct(long number,
                                  String name,
                                  String url,
                                  String manufacturer,
                                  long currentPrice,
                                  long productId) {
        try {
            ProductService.validateInputData(number, name, url, manufacturer, currentPrice);
        } catch (IllegalArgumentException ia) {
            return new Response(ia.getMessage(), false);
        }
        if (productDao.findProductByNumber(number).isPresent()){
            Product p = productDao.findProductByNumber(number).get();
            if (p.getId()!= productId) {
                return new Response(findProductByNumber(number).getMessage(), false);
            }
        }
        if (productDao.findProductByUrl(url).isPresent()) {
            Product p = productDao.findProductByUrl(url).get();
            if (p.getId() != productId) {
                return new Response(findProductByUrl(url).getResponse().getMessage(), false);
            }
        }
        return productDao.updateProduct(number, name, url, manufacturer, currentPrice, productId);
    }

    public Response deleteProductById(long productId) {
        if (productDao.findProductById(productId).isPresent()) {
            if (productDao.findProductById(productId).get().getProductStatus().equals(ProductStatus.DELETED)){
                return new Response("Product has been already deleted.", false);
            }
        }
        if (!productDao.findProductById(productId).isPresent()){
            return new Response("No product found to delete.", false);
        }
        return productDao.deleteProductById(productId);
    }

    public static void validateInputData(long number, String name, String url, String manufacturer, long currentPrice) {
        if (name == null || url == null || manufacturer == null) {
            throw new IllegalArgumentException("All input fields must be filled.");
        }
        if (name.trim().length() == 0 || url.trim().length() == 0 || manufacturer.trim().length() == 0) {
            throw new IllegalArgumentException("All input fields must be filled.");
        }
        if (number < 1 || currentPrice < 0) {
            throw new IllegalArgumentException("Id and price must be positive.");
        }
        if (currentPrice > 2e6) {
            throw new IllegalArgumentException("Maximum price is 2 000 000 EUR.");
        }
    }

    private Response findProductByNumber(long number) {
        if (productDao.findProductByNumber(number).isPresent())
            return new Response("Product with the provided number already exists.", true);
        else return new Response("No such product with the number provided.", false);
    }
}
