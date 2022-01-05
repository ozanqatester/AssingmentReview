package com.java.runner;


import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/resources/features/",
        glue = "com.java.stepdefs",
        monochrome = true,
        tags = { "@GetBookingApi" }
)
public class TestRunner {
}

