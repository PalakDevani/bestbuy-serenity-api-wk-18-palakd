package com.bestbuy.bestbuyinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.ProductPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;


public class ProductsSteps {
    @Step("creating product with name:{0},type:{1},price:{2},shipping:{3},upc:{4},description:{5},manufacture:{6},model:{7},url:{8},image:{9}")

    public ValidatableResponse createProduct(String name, String type, double price, int shipping, String upc, String description, String manufacturer, String model, String url, String image) {
        ProductPojo productPojo = new ProductPojo();

        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setShipping(shipping);
        productPojo.setUpc(upc);
        productPojo.setDescription(description);
        productPojo.setManufacturer(manufacturer);
        productPojo.setModel(model);
        productPojo.setUrl(url);
        productPojo.setImage(image);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(productPojo)
                .when()
                .post(EndPoints.CREATE_PRODUCT)
                .then();
    }

    @Step("Getting the product information with name: {0}")

    public HashMap<String, Object> getProductInfoByName(String name) {
        String p1 = "data.findAll{it.name == '";
        String p2 = "'}.get(0)";
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .headers("Accept", "*/*")
                .when()
                .get(EndPoints.GET_ALL_PRODUCTS)
                .then()
                .statusCode(200)
                .extract()
                .path(p1 + name + p2);
    }

    @Step("Updating product information with productId: {0}, name: {1}, type: {2}, price: {3}, shipping:{4},upc:{5},description:{6},manufacture:{7},model:{8},url:{9},image:{10}")

    public ValidatableResponse updateProduct(int productId, String name, String type, double price, int shipping, String upc, String description, String manufacturer, String model, String url, String image) {

        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setShipping(shipping);
        productPojo.setUpc(upc);
        productPojo.setManufacturer(manufacturer);
        productPojo.setModel(model);
        productPojo.setUrl(url);
        productPojo.setImage(image);
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .pathParam("productID", productId)
                .body(productPojo)
                .when()
                .put(EndPoints.UPDATE_PRODUCTS_BY_ID)
                .then();
    }

    @Step("Deleting product information with productId: {0}")

    public ValidatableResponse deleteProduct(int productId) {
        return SerenityRest.given().log().all()
                .pathParam("productId", productId)
                .when()
                .delete(EndPoints.DELETE_PRODUCTS_BY_ID)
                .then();
    }

    @Step("Getting product information with productId: {0}")

    public ValidatableResponse getProductById(int productId) {
        return SerenityRest.given().log().all()
                .pathParam("productID", productId)
                .when()
                .get(EndPoints.GET_PRODUCTS_BY_ID)
                .then();

    }


}
