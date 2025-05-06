package com.Beymen.runners;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;


@CucumberOptions(
        plugin = {"json:target/cucumber1.json"},
        features = "@target/rerun.txt",
        glue = "com/Beymen/step_definitions"
)
public class FailedTestRunner extends AbstractTestNGCucumberTests {





}
