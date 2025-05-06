package com.Beymen.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BasketPage extends BasePage{

    @FindBy(className ="m-basket__quantity__plus")
    public WebElement quantityIncreaseButton;

    @FindBy(className ="m-basket__remove")
    public WebElement deleteProductButton;


}
