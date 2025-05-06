package com.Beymen.step_definitions;

import com.Beymen.pages.BasketPage;
import com.Beymen.utilities.Driver;
import io.cucumber.java.en.And;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class BasketStepDefinitions {
BasketPage basketPage=new BasketPage();
    @And("Ürün adedi 2 yapılır ve doğrulanır")
    public void adet_arttir() {
       basketPage.quantityIncreaseButton.click();
        String adet = basketPage.quantityIncreaseButton.getAttribute("value");
        assert adet.equals("2");
    }

    @And("Ürün sepetten silinir ve sepetin boş olduğu doğrulanır")
    public void sepet_bos_kontrol() {
       basketPage.deleteProductButton.click();
        WebElement bosSepet = Driver.getDriver().findElement(By.className("m-empty__messageTitle"));
        assert bosSepet.getText().toLowerCase().contains("boş");
    }
}