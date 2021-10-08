package application.product;

import java.util.ArrayList;
import java.util.List;

public class Product {

    private String id;
    private String name;
    private String description;
    private String sku;

    private List<String> availablePrices;

    public Product(String id, String name, String description, String sku) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.sku = sku;

        this.availablePrices = new ArrayList<String>();
    }

    public Product(String name, String description, String id, String sku, List<String> availablePrices) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.sku = sku;

        this.availablePrices = availablePrices;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public List<String> getAvailablePrices() {
        return availablePrices;
    }

    public void setAvailablePrices(List<String> availablePrices) {
        this.availablePrices = availablePrices;
    }
}
