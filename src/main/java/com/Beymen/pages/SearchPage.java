package com.Beymen.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchPage extends BasePage{

    @FindBy(className ="o-header__search--input")
    public WebElement searchBar;
}
