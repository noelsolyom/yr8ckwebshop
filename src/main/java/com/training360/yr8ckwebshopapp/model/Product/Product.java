package com.training360.yr8ckwebshopapp.model.Product;

public class Product {

    private long id;
    private long number;
    private String name;
    private String url;
    private String manufacturer;
    private long currentPrice;
    private ProductStatus productStatus;

    public Product() {
    }

    public Product(long number, String name, String url, String manufacturer, long currentPrice) {
        if ((name != null) && (url != null) && (manufacturer != null)  ) {
            this.id = 0;
            this.number = number;
            this.name = name.trim();
            this.url = url.trim();
            this.manufacturer = manufacturer.trim();
            this.currentPrice = currentPrice;
            this.productStatus = ProductStatus.ACTIVE;
        }
    }

    public Product(long id, long number, String name, String url, String manufacturer, long currentPrice) {
        this(number, name, url, manufacturer, currentPrice);
        if (id > 0 && number > 0 && currentPrice >= 0) {
            this.id = id;
        }
    }

    public Product(long id, long number, String name, String url, String manufacturer, long currentPrice, ProductStatus productStatus) {
        this(id, number, name, url, manufacturer, currentPrice);
        if (id > 0 && number > 0 && currentPrice >= 0 && productStatus != null) {
            this.productStatus = productStatus;
        }
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setCurrentPrice(long currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public long getId() {
        return id;
    }

    public long getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public long getCurrentPrice() {
        return currentPrice;
    }
}
