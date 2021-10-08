# Description

A Spring Boot service application that reads in data XML and JSON files
and exposes the information via a RESTful API.

## Data Files Information

  - `products.xml` contains master data for all products
  - `prices.json` contains price information for all products

## Use cases

The API is designed in a way that allows the following use cases:

1. List all products with their master data and without prices.
**Entrypoint:** `/products`
2. Show a single product with master data and all of its available prices.
**Entrypoint:** `/products/{id}` with `{id}` corresponding to the attribute id for a given product in `products.xml`
3. Show a single price for one product and a specific unit.
**Entrypoint:** `/price?id={id}&unit={unit}` with `{id}` corresponding to the attribute `id` for a given Product in `products.xml` and `{unit}` corresponding to the `unit` for an entry corresponding to this product in `prices.json`. 
**OR:** `/price_sku?sku={sku}&unit={unit}` with `{sku}` corresponding to the `id` for a given entry in `prices.json` and `{unit}` corresponding to the `unit` for the same entry.
	 
## Information

* For a website of an online supermarket, it can use this or a similarly designed API to list the products in its "browse products" section of the website. For websites that sell many different types of products, it is useful to add a "type" to each product, so that the website can have browse by the type of items similar to how the Bringmeister website has.  
* Also, for the specific page to each product, the 2nd use case listed in the requirement is beneficial, as it returns the master data (name, description, etc...) and all the prices, which can in turn be displayed by the frontend in the product page.
* In case the user wants to find the price for a specific "number of units" of the product, then the API also can be used as it fulfills the 3rd use as noted above.