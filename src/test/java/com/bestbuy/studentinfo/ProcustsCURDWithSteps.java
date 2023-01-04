package com.bestbuy.studentinfo;

import com.bestbuy.bestbuyinfo.ProductsSteps;
import com.bestbuy.testbase.TestBaseProducts;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;


import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)

public class ProcustsCURDWithSteps extends TestBaseProducts {

    static String name = "Revlon HairDrayer" + TestUtils.getRandomValue();
    static String type = "Personal electronics";
    static double price = 59.99;
    static int shipping = 3;
    static String upc = "04133341";
    static String description = "hairdrayer for personal use";
    static String manufacturer = "Revlon";
    static String model = "RV2022M";
    static String url = "http://www.bestbuy.com";
    static String image = "http://img.bbystatic.com";
    static int productId;

    @Steps
    ProductsSteps productsSteps;

    @Title("This will create new product")
    @Test

    public void test01() {
        ValidatableResponse response = productsSteps.createProduct(name, type, price, shipping, upc, description, manufacturer, model, url, image);
        response.log().all().statusCode(201);

    }

    @Title("This will verify if the new product added successfully")
    @Test

    public void test02() {
        HashMap<String, Object> productMap = productsSteps.getProductInfoByName(name);
        Assert.assertThat(productMap, hasValue(name));
        productId = (int) productMap.get("id");

    }

    @Title("Update the product information and verify the updated information")
    @Test
    public void test003() {
        name = name + "_updated";

        productsSteps.updateProduct(productId, name, type, price, shipping, upc, description, manufacturer, model, url, image)
                .log().all().statusCode(200);


        HashMap<String, Object> productMap = productsSteps.getProductInfoByName(name);
        Assert.assertThat(productMap, hasValue(name));
    }

    @Title("Delete the product and verify if the product is deleted!")
    @Test
    public void test004() {

        productsSteps.deleteProduct(productId).statusCode(204);
        productsSteps.getProductById(productId).statusCode(404);

    }


}




