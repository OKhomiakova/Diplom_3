package ru.practicum.page.object;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.WebDriverRunner.url;

public class ProfilePage {
    public final static String URL_PROFILE = "https://stellarburgers.nomoreparties.site/account/profile";

    //Ссылка Профиль
    @FindBy(how = How.XPATH, using = "//a[text()='Профиль']")
    private SelenideElement linkProfile;
    //Конструктор
    @FindBy(how = How.XPATH, using = "//p[text()='Конструктор']")
    private SelenideElement constructorButton;
    //Логотип Stellar Burgers
    @FindBy(how = How.XPATH, using = "//div[@class='AppHeader_header__logo__2D0X2']")
    private SelenideElement logoStellarBurgers;

    //Кнопка Выход
    @FindBy(how = How.XPATH, using = "//button[text()='Выход']")
    private SelenideElement logout;


    public MainPage clickConstructorButton() {
        constructorButton.click();
        return page(MainPage.class);
    }
    public MainPage clicklogoStellarBurgers() {
        logoStellarBurgers.click();
        return page(MainPage.class);
    }
    public LoginPage clickLogout() {
        logout.click();
        return page(LoginPage.class);
    }
    public boolean isProfilePageOpen(){
        linkProfile.shouldBe(visible);
        return url().equals(ProfilePage.URL_PROFILE);

    }
}
