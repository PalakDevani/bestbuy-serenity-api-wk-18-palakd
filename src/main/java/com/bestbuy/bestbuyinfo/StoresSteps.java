package com.bestbuy.bestbuyinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.StorePojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class StoresSteps {
    @Step("creating store with: storeId:{0},name:{1},type:{2},address:{3},address2:{4},city:{5},state:{6},zip:{7},lat:{8},lang:{9},hours:{10} ")

    public ValidatableResponse createStores(String name, String type, String address, String address2, String city, String state, String zip, double lat, double lng, String hours) {
        StorePojo storePojo = new StorePojo();

        storePojo.setName(name);
        storePojo.setType(type);
        storePojo.setAddress(address);
        storePojo.setAddress2(address2);
        storePojo.setCity(city);
        storePojo.setState(state);
        storePojo.setZip(zip);
        storePojo.setLat(lat);
        storePojo.setLng(lng);
        storePojo.setHours(hours);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(storePojo)
                .when()
                .post()
                .then();
    }

    @Step("Getting the store information with name: {0}")

    public HashMap<String, Object> getStoreByName(String name) {
        String p1 = "findAll{it.name == '";
        String p2 = "'}.get(0)";
        return SerenityRest.given().log().all()
                .when()
                .get(EndPoints.STORES)
                .then()
                .statusCode(200)
                .extract()
                .path(p1 + name + p2);
    }

    @Step("Updating store information with storeId:{0},name:{1},type:{2},address:{3},address2:{4},city:{5},state:{6},zip:{7},lat:{8},lang:{9},hours:{10}  ")

    public ValidatableResponse updateStore(int storeId,String name,String type, String address, String address2, String city, String state, String zip, double lat, double lang, String hours) {

        StorePojo storePojo = new StorePojo();
        storePojo.setName(name);
        storePojo.setType(type);
        storePojo.setAddress(address);
        storePojo.setAddress2(address2);
        storePojo.setCity(city);
        storePojo.setState(state);
        storePojo.setZip(zip);
        storePojo.setLat(lat);
        storePojo.setLng(lang);
        storePojo.setHours(hours);
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .pathParam("storeId", storeId)
                .body(storePojo)
                .when()
                .put(EndPoints.UPDATE_STORE_BY_ID)
                .then();
    }

    @Step("Deleting store information with storeId: {0}")

    public ValidatableResponse deleteStore(int storeId) {
        return SerenityRest.given().log().all()
                .pathParam("storeId", storeId)
                .when()
                .delete(EndPoints.DELETE_STORE_BY_ID)
                .then();
    }

    @Step("Getting store information with storeId: {0}")

    public ValidatableResponse getStoreById(int storeId) {
        return SerenityRest.given().log().all()
                .pathParam("storeId", storeId)
                .when()
                .get(EndPoints.GET_STORE_BY_ID)
                .then();

    }


}
