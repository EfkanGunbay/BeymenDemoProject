package com.Beymen.runners;

import com.Beymen.utilities.*;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterSuite;

import java.util.concurrent.TimeUnit;


@CucumberOptions(
      plugin = {"pretty","html:target/cucumber.html","json:target/cucumber.json"},
        glue = "com/Beymen/step_definitions",
        features = "src/test/resources/Features",
        publish = true,
        tags = "@Reg and not ignore"

)
public class CukesRunner extends AbstractTestNGCucumberTests {




    public void setUp(){
        Driver.getDriver().manage().window().maximize();
        Driver.getDriver().manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        Driver.getDriver().get(ConfigReader.getProperty("url_live"));
    }

    @AfterSuite
    public void send(){
        try {

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}