package com.bestbuy.studentinfo;

import com.bestbuy.bestbuyinfo.StoresSteps;
import com.bestbuy.testbase.TestBaseStores;
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

public class StoresCURDWithSteps extends TestBaseStores {


    static String name = "Best Buy Services13";
    static String type = "WhiteBox";
    static String address = " 2 Athena Road";
    static String address2 = "";
    static String city = "Jamnagar";
    static String state = " Gujarat";
    static String zip = " 382350";
    static double lat = 45.23568;
    static double lng = -81.65421;
    static String hours = "Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8";
    static int storeId;

    @Steps
    StoresSteps storesSteps;

    @Title("This will create new store")
    @Test
    public void test001() {

        ValidatableResponse response = storesSteps.createStores(name, type, address, address2, city, state, zip, lat, lng, hours);
        response.log().all().statusCode(201);
    }

    @Title("Getting the store information with Name:")
    @Test
    public void test002() {

        HashMap<String, Object> storeMap = storesSteps.getStoreByName(name);


        Assert.assertThat(storeMap, hasValue(name));
        storeId = (int) storeMap.get("storeId");
    }

    @Title("Updating store information and verify the updated information")
    @Test
    public void test003() {
        name = name + ("_added");
        storesSteps.updateStore(storeId, name, type, address, address2, city, state, zip, lat, lng, hours).log().all().statusCode(200);

        HashMap<String, Object> storeMap = storesSteps.getStoreByName(name);
        Assert.assertThat(storeMap, hasValue(name));
    }

    @Title("Deleting store with id and verify that user is deleted")
    @Test
    public void test004() {
        storesSteps.deleteStore(storeId).statusCode(204);
        storesSteps.getStoreById(storeId).statusCode(404);

    }
}