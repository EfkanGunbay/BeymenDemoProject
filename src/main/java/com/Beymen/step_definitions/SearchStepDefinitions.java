package com.Beymen.step_definitions;

import com.Beymen.pages.HomePage;
import com.Beymen.utilities.Driver;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import java.io.*;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.Random;

public class SearchStepDefinitions {
    String urunFiyat;
    String urunBilgi;

    @Then("Rastgele bir ürün seçilir")
    public void rastgele_urun_sec() {
        List<WebElement> urunler = Driver.getDriver().findElements(By.cssSelector(".m-productImageList"));
        urunler.get(new Random().nextInt(urunler.size())).click();
    }

    @And("Ürün bilgisi txt dosyasına yazılır")
    public void urun_bilgisi_yaz() throws IOException {
        urunBilgi = Driver.getDriver().findElement(By.className("o-productDetail__description")).getText();
        urunFiyat = Driver.getDriver().findElement(By.className("m-price__new")).getText();

        BufferedWriter writer = new BufferedWriter(new FileWriter("src/resources/urun_bilgisi.txt"));
        writer.write("Ürün: " + urunBilgi + "\nFiyat: " + urunFiyat);
        writer.close();
    }

    @And("Ürün sepete eklenir")
    public void urun_sepete_eklenir() {
        Driver.getDriver().findElement(By.id("addBasket")).click();
        Driver.getDriver().get("https://www.beymen.com/sepetim");
    }

    @And("Sepetteki fiyat ile ürün fiyatı karşılaştırılır")
    public void fiyat_karsilastir() {
        WebElement fiyat = Driver.getDriver().findElement(By.className("m-orderSummary__price"));
        assert fiyat.getText().contains(urunFiyat);
    }
}

