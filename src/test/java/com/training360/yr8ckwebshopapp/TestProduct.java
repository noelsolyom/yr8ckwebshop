package com.training360.yr8ckwebshopapp;

import com.training360.yr8ckwebshopapp.model.Product.Product;
import com.training360.yr8ckwebshopapp.model.Product.ProductStatus;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class TestProduct {

    @Test
    public void normalInit() {
        Product product = new Product(1L,1L, "Name Name", "url-url", "Manufacturer", 1L, ProductStatus.ACTIVE);
        assertEquals(1L, product.getId());
        assertEquals(1L, product.getNumber());
        assertEquals(1L, product.getCurrentPrice());
        assertThat(product.getName(), equalTo("Name Name"));
        assertThat(product.getUrl(), equalTo("url-url"));
        assertThat(product.getManufacturer(), equalTo("Manufacturer"));
        assertThat(product.getProductStatus(), equalTo(ProductStatus.ACTIVE));
    }

    @Test
    public void normalInitTrim() {
        Product product = new Product(1L,1L, " Name Name ", " url-url ", " Manufacturer ", 1L, ProductStatus.DELETED);
        assertEquals(1L, product.getId());
        assertEquals(1L, product.getNumber());
        assertEquals(1L, product.getCurrentPrice());
        assertThat(product.getName(), equalTo("Name Name"));
        assertThat(product.getUrl(), equalTo("url-url"));
        assertThat(product.getManufacturer(), equalTo("Manufacturer"));
        assertThat(product.getProductStatus(), equalTo(ProductStatus.DELETED));
    }

    @Test
    public void setThenGetAttributes() {
        Product product = new Product();
        product.setId(1L);
        product.setNumber(1L);
        product.setName("Name Name");
        product.setUrl("url-url");
        product.setManufacturer("Manufacturer");
        product.setCurrentPrice(1L);
        product.setProductStatus(ProductStatus.ACTIVE);
        assertEquals(1L, product.getId());
        assertEquals(1L, product.getNumber());
        assertEquals(1L, product.getCurrentPrice());
        assertThat(product.getName(), equalTo("Name Name"));
        assertThat(product.getUrl(), equalTo("url-url"));
        assertThat(product.getManufacturer(), equalTo("Manufacturer"));
        assertThat(product.getProductStatus(), equalTo(ProductStatus.ACTIVE));
    }
}
