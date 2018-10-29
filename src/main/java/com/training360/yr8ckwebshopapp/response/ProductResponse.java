package com.training360.yr8ckwebshopapp.response;

import com.training360.yr8ckwebshopapp.model.Product.Product;

public class ProductResponse {

    private Product product;
    private Response response;

    public ProductResponse(Product product, Response response) {
        this.product = product;
        this.response = response;
    }

    public Product getProduct() {
        return product;
    }

    public Response getResponse() {
        return response;
    }
}
