package com.Beymen.step_definitions;

import com.Beymen.pages.HomePage;
import com.Beymen.pages.SearchPage;
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
public class HomeStepDefinitions {

SearchPage searchPage=new SearchPage();
    @Then("Anasayfanin Acildigi Kontrol Edilir")
    public void anasayfaninAcildigiKontrolEdilir() {
        String expectedUrl = "https://www.beymen.com/tr";
        String actualUrl = Driver.getDriver().getCurrentUrl();

        if (actualUrl.equals(expectedUrl)) {
            System.out.println("Ana sayfa açıldı.");
        } else {
            System.out.println("Ana sayfa AÇILMADI!");
        }
    }

    @And("Arama kutusuna Excel'den gelen {string} kelimesi girilir")
    public void excelden_kelime_girilir(String kelime) throws Exception {
        String value = readFromExcel(kelime.equals("şort") ? 0 : 1);
        WebElement searchBox = searchPage.searchBar;
        searchBox.sendKeys(value);
    }


    @And("Arama kutusu temizlenir")
    public void arama_kutusu_temizlenir() {
        searchPage.searchBar.clear();
    }

    @And("Enter tuşuna basılır")
    public void enter_tusuna_basilir() {
        searchPage.searchBar.sendKeys(Keys.ENTER);
    }

    public String readFromExcel(int columnIndex) throws Exception {
        FileInputStream fis = new FileInputStream("src/resources/arama.xlsx");
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);
        String value = sheet.getRow(0).getCell(columnIndex).getStringCellValue();
        workbook.close();
        return value;
    }
}



