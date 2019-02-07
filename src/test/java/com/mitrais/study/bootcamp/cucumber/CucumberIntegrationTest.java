package com.mitrais.study.bootcamp.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features"
    )
public class CucumberIntegrationTest extends SpringIntegrationTest {

}
