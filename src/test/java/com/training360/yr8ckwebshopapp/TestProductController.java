package com.training360.yr8ckwebshopapp;

import com.training360.yr8ckwebshopapp.model.Product.Product;
import com.training360.yr8ckwebshopapp.response.ProductResponse;
import com.training360.yr8ckwebshopapp.ui.ProductController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "classpath:/clearDatabase.sql")
public class TestProductController {

    @Autowired
    private ProductController productController;
    private List<Product> productList = new ArrayList<>();

    @Before
    public void init() {
        productList.add(new Product(1L, "Galaxy S8", "galaxy-s8", "Samsung", 129L));
        productList.add(new Product(2L, "iPhone 8", "iphone-8", "Apple", 2190L));
    }

    @Test
    public void TestFindByUrlExistingProduct() {
        assertTrue(productController.findProductByUrl("galaxy-s8").getResponse().isOk());
        assertEquals("Product with the provided URL already exists.", productController.findProductByUrl("galaxy-s8").getResponse().getMessage());

        assertEquals(1L, productController.findProductByUrl("galaxy-s8").getProduct().getNumber());
        assertEquals("Galaxy S8", productController.findProductByUrl("galaxy-s8").getProduct().getName());
        assertEquals("galaxy-s8", productController.findProductByUrl("galaxy-s8").getProduct().getUrl());
        assertEquals("Samsung", productController.findProductByUrl("galaxy-s8").getProduct().getManufacturer());
        assertEquals(500L, productController.findProductByUrl("galaxy-s8").getProduct().getCurrentPrice());
        assertEquals("ACTIVE", productController.findProductByUrl("galaxy-s8").getProduct().getProductStatus().toString());
    }

    @Test
    public void TestFindByUrlNonExistingProduct() {
        assertFalse(productController.findProductByUrl("galaxy").getResponse().isOk());
        assertEquals("No such product with the URL provided.", productController.findProductByUrl("galaxy").getResponse().getMessage());

        assertThat(productController.findProductByUrl("galaxy").getProduct(), is(nullValue()));
    }

    @Test
    public void TestListProducts() {
        assertThat(productController.listProducts().size(), is(6));
    }

    @Test
    public void TestScrollListProductsInRange() {
        assertThat(productController.scrollListProducts(1,2).size(), is(2));
    }

    @Test
    public void TestScrollListProductsOutOfRange() {
        assertThat(productController.scrollListProducts(10,2), is(new ArrayList()));
    }

    @Test
    public void CreateProductValid() {
        assertTrue(productController.createProduct(new Product(7L, " Name Name ", " name-name ", " Manufacturer ", 100L)).isOk());
        assertEquals("Product has been created", productController.createProduct(new Product(8L, "Name Name", "name-name2", "Manufacturer", 100L)).getMessage());
        ProductResponse productResponse = productController.findProductByUrl("name-name");
        assertEquals("Name Name", productResponse.getProduct().getName());
    }

    @Test
    public void CreateProductDuplicatedNumber() {
        assertTrue(productController.createProduct(new Product(7L, "Name Name", "name-name", "Manufacturer", 100)).isOk());
        assertEquals("Product with the provided number already exists.", productController.createProduct(new Product(7L, "Name Name", "name-name2", "Manufacturer", 100L)).getMessage());
    }

    @Test
    public void CreateProductDuplicatedUrl() {
        assertEquals("Product with the provided URL already exists.", productController.createProduct(new Product(7L, "Galaxy S8", "galaxy-s8-2", "Samsung", 500)).getMessage());
        assertFalse(productController.createProduct(new Product(8L, "Galaxy S8", "galaxy-s8-2", "Samsung", 500)).isOk());
    }

    @Test
    public void CreateProductInvalidNumber() {
        assertFalse(productController.createProduct(new Product(-1L, "Name Name", "name-name", "Manufacturer", 100L)).isOk());
        assertEquals("Id and price must be positive.", productController.createProduct(new Product(-1L, "Name Name", "name-name", "Manufacturer", 100L)).getMessage());
    }

    @Test
    public void CreateProductInvalidPrice() {
        assertFalse(productController.createProduct(new Product(4L, "Name Name", "name-name", "Manufacturer", -1L)).isOk());
        assertEquals("Id and price must be positive.", productController.createProduct(new Product(5L, "Name Name", "name-name", "Manufacturer", -1L)).getMessage());
    }

    @Test
    public void CreateProductInvalidNumber2() {
        assertFalse(productController.createProduct(new Product(0L, "Name Name", "name-name", "Manufacturer", 100L)).isOk());
        assertEquals("Id and price must be positive.", productController.createProduct(new Product(-1L, "Name Name", "name-name", "Manufacturer", 100L)).getMessage());
    }

    @Test
    public void CreateProductInvalidPrice2() {
        assertFalse(productController.createProduct(new Product(4L, "Name Name", "name-name", "Manufacturer", 2000001L)).isOk());
        assertEquals("Maximum price is 2 000 000 EUR.", productController.createProduct(new Product(4L, "Name Name", "name-name", "Manufacturer", 2000001L)).getMessage());
    }

    @Test
    public void CreateProductInvalidName() {
        assertFalse(productController.createProduct(new Product(4L, "", "name-name", "Manufacturer", 100L)).isOk());
        assertEquals("All input fields must be filled.", productController.createProduct(new Product(4L, null, "name-name", "Manufacturer", 100L)).getMessage());
    }

    @Test
    public void CreateProductInvalidUrl() {
        assertFalse(productController.createProduct(new Product(4L, "Name Name", "", "Manufacturer", 100L)).isOk());
        assertEquals("All input fields must be filled.", productController.createProduct(new Product(4L, "Name Name", null, "Manufacturer", 100L)).getMessage());
    }

    @Test
    public void CreateProductInvalidManufacturer() {
        assertFalse(productController.createProduct(new Product(4L, "Name Name", "url-url", "", 100L)).isOk());
        assertEquals("All input fields must be filled.", productController.createProduct(new Product(4L, "Name Name", "url-url", null, 100L)).getMessage());
    }

    @Test
    public void UpdateProductValidNumber() {
        assertTrue(productController.createProduct(new Product(7L, "Name Name", "url-url", "Manufacturer", 100L)).isOk());
        long id = productController.findProductByUrl("url-url").getProduct().getId();
        assertEquals("Product has been updated.", productController.updateProductById(new Product(8L, "Name Name", "url-url", "Manufacturer", 100L), id).getMessage());
    }

    @Test
    public void UpdateProductDuplicatedNumber() {
        assertTrue(productController.createProduct(new Product(7L, "Name Name", "url-url", "Manufacturer", 100L)).isOk());
        assertEquals("Product with the provided number already exists.", productController.updateProductById(new Product(7L, "Name Name2", "url-url2", "Manufacturer", 100L), 1L).getMessage());
    }

    @Test
    public void UpdateProductInvalidNumber() {
        assertTrue(productController.createProduct(new Product(7L, "Name Name", "url-url", "Manufacturer", 100L)).isOk());
        long id = productController.findProductByUrl("url-url").getProduct().getId();
        assertEquals("Id and price must be positive.", productController.updateProductById(new Product(-1L, "Name Name", "url-url2", "Manufacturer", 100L), id).getMessage());
    }

    @Test
    public void UpdateProductInvalidNumber2() {
        assertTrue(productController.createProduct(new Product(7L, "Name Name", "url-url", "Manufacturer", 100L)).isOk());
        long id = productController.findProductByUrl("url-url").getProduct().getId();
        assertEquals("Id and price must be positive.", productController.updateProductById(new Product(0L, "Name Name", "url-url2", "Manufacturer", 100L), id).getMessage());
    }

    @Test
    public void UpdateProductDuplicatedUrl() {
        assertTrue(productController.createProduct(new Product(7L, "Name Name", "url-url", "Manufacturer", 100L)).isOk());
        long id = productController.findProductByUrl("url-url").getProduct().getId();
        assertEquals("Product with the provided URL already exists.", productController.updateProductById(new Product(8L, "Name Name", "galaxy-s8", "Manufacturer", 100L), id).getMessage());
    }

    @Test
    public void UpdateProductInvalidUrl() {
        assertTrue(productController.createProduct(new Product(7L, "Name Name", "url-url", "Manufacturer", 100L)).isOk());
        long id = productController.findProductByUrl("url-url").getProduct().getId();
        assertEquals("All input fields must be filled.", productController.updateProductById(new Product(7L, "Name Name", "", "Manufacturer", 100L), id).getMessage());
    }

    @Test
    public void UpdateProductInvalidUrl2() {
        assertTrue(productController.createProduct(new Product(7L, "Name Name", "url-url", "Manufacturer", 100L)).isOk());
        long id = productController.findProductByUrl("url-url").getProduct().getId();
        assertEquals("All input fields must be filled.", productController.updateProductById(new Product(8L, "Name Name", null, "Manufacturer", 100L), id).getMessage());
    }

    @Test
    public void UpdateProductValidPrice() {
        assertTrue(productController.createProduct(new Product(7L, "Name Name", "url-url", "Manufacturer", 100L)).isOk());
        long id = productController.findProductByUrl("url-url").getProduct().getId();
        assertEquals("Product has been updated.", productController.updateProductById(new Product(7L, "Name Name", "url-url", "Manufacturer", 200L), id).getMessage());
    }

    @Test
    public void UpdateProductValidPrice2() {
        assertTrue(productController.createProduct(new Product(7L, "Name Name", "url-url", "Manufacturer", 100L)).isOk());
        long id = productController.findProductByUrl("url-url").getProduct().getId();
        assertEquals("Product has been updated.", productController.updateProductById(new Product(7L, "Name Name", "url-url", "Manufacturer", 0L), id).getMessage());
    }

    @Test
    public void UpdateProductInvalidPrice() {
        assertTrue(productController.createProduct(new Product(7L, "Name Name", "url-url", "Manufacturer", 100L)).isOk());
        long id = productController.findProductByUrl("url-url").getProduct().getId();
        assertEquals("Id and price must be positive.", productController.updateProductById(new Product(4L, "Name Name", "url-url", "Manufacturer", -1), id).getMessage());
    }

    @Test
    public void UpdateProductInvalidPrice2() {
        assertTrue(productController.createProduct(new Product(7L, "Name Name", "url-url", "Manufacturer", 100L)).isOk());
        long id = productController.findProductByUrl("url-url").getProduct().getId();
        assertEquals("Maximum price is 2 000 000 EUR.", productController.updateProductById(new Product(4L, "Name Name", "url-url", "Manufacturer", 2000001L), id).getMessage());
    }

    @Test
    public void UpdateProductValidName() {
        assertTrue(productController.createProduct(new Product(7L, "Name Name", "url-url", "Manufacturer", 100L)).isOk());
        long id = productController.findProductByUrl("url-url").getProduct().getId();
        assertEquals("Product has been updated.", productController.updateProductById(new Product(7L, "Name Name2", "url-url", "Manufacturer", 100L), id).getMessage());
    }

    @Test
    public void UpdateProductInvalidName() {
        assertTrue(productController.createProduct(new Product(7L, "Name Name", "url-url", "Manufacturer", 100L)).isOk());
        long id = productController.findProductByUrl("url-url").getProduct().getId();
        assertEquals("All input fields must be filled.", productController.updateProductById(new Product(4L, "", "url-url", "Manufacturer", 100L), id).getMessage());
    }

    @Test
    public void UpdateProductInvalidName2() {
        assertTrue(productController.createProduct(new Product(7L, "Name Name", "url-url", "Manufacturer", 100L)).isOk());
        long id = productController.findProductByUrl("url-url").getProduct().getId();
        assertEquals("All input fields must be filled.", productController.updateProductById(new Product(4L, null, "url-url", "Manufacturer", 100L), id).getMessage());
    }

    @Test
    public void UpdateProductValidManufacturer() {
        assertTrue(productController.createProduct(new Product(7L, "Name Name", "url-url", "Manufacturer", 100L)).isOk());
        long id = productController.findProductByUrl("url-url").getProduct().getId();
        assertEquals("Product has been updated.", productController.updateProductById(new Product(7L, "Name Name", "url-url", "Manufacturer2", 100L), id).getMessage());
    }

    @Test
    public void UpdateProductInvalidManufacturer() {
        assertTrue(productController.createProduct(new Product(7L, "Name Name", "url-url", "Manufacturer", 100L)).isOk());
        long id = productController.findProductByUrl("url-url").getProduct().getId();
        assertEquals("All input fields must be filled.", productController.updateProductById(new Product(4L, "Name Name", "url-url", "", 100L), id).getMessage());
    }

    @Test
    public void UpdateProductInvalidManufacturer2() {
        assertTrue(productController.createProduct(new Product(7L, "Name Name", "url-url", "Manufacturer", 100L)).isOk());
        long id = productController.findProductByUrl("url-url").getProduct().getId();
        assertEquals("All input fields must be filled.", productController.updateProductById(new Product(4L, "Name Name", "url-url", null, 100L), id).getMessage());
    }

    @Test
    public void DeleteProduct() {
        assertTrue(productController.createProduct(new Product(7L, "Name Name", "url-url", "Manufacturer", 100L)).isOk());
        long id = productController.findProductByUrl("url-url").getProduct().getId();
        assertEquals("Product succesfully deleted.", productController.deleteProductById(id).getMessage());
    }

    @Test
    public void DeleteAlreadyDeletedProduct() {
        assertTrue(productController.createProduct(new Product(7L, "Name Name", "url-url", "Manufacturer", 100L)).isOk());
        long id = productController.findProductByUrl("url-url").getProduct().getId();
        productController.deleteProductById(id);
        assertEquals("Product has been already deleted.", productController.deleteProductById(id).getMessage());
    }

    @Test
    public void DeleteInexistingProduct() {
        assertEquals("No product found to delete.", productController.deleteProductById(0L).getMessage());
        assertTrue(productController.createProduct(new Product(7L, "Name Name", "url-url", "Manufacturer", 100L)).isOk());
    }
}
