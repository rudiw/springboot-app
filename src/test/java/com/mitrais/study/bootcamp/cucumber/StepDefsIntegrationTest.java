package com.mitrais.study.bootcamp.cucumber;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.springframework.http.HttpStatus;

public class StepDefsIntegrationTest extends SpringIntegrationTest {

    @When("^Client call api cucumber hello$")
    public void callHello() throws Throwable {
        System.out.println(String.format("Call hello"));
        executeGet("http://localhost:8080/rest/cucumber/hello");
    }

    @Then("^Client receives status code of (\\d+)$")
    public void getStatus(int statusCode) throws Throwable {
        System.out.println(String.format("Get Status"));
        final HttpStatus currentStatusCode = latestResponse.getTheResponse().getStatusCode();
        Assert.assertThat("status code  is incorrect : " +
                latestResponse.getBody(), currentStatusCode.value(), Matchers.is(statusCode));
    }

    @And("^Client receives (.+)$")
    public void getBody(String version) throws Throwable {
        System.out.println(String.format("Get body"));
        Assert.assertThat(latestResponse.getBody(), Matchers.is(version));
    }

}
