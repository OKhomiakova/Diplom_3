package ru.practicum.page.object;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.WebDriverRunner.url;

import java.time.Duration;

public class MainPage {
    public static String URL_MAIN = "https://stellarburgers.nomoreparties.site/";

    // локатор кнопки "Войти в аккаунт"
    @FindBy(how = How.XPATH, using = "//button[text()='Войти в аккаунт']")
    private SelenideElement loginButton;

    // локатор кнопки "Личный кабинет"
    @FindBy(how = How.XPATH, using = "//p[text()='Личный Кабинет']")
    private SelenideElement profileButton;

    // локатор заголовок "Соберите бургер"
    @FindBy(how = How.XPATH, using = "//h1[text()='Соберите бургер']")
    private SelenideElement assembleBurgerHeader;

    // локатор раздела "Булки"
    @FindBy(how = How.XPATH, using = "//div[span[text()='Булки']]")
    private SelenideElement bunTab;

    // локатор раздела "Соусы"
    @FindBy(how = How.XPATH, using = "//div[span[text()='Соусы']]")
    private SelenideElement sauceTab;

    // локатор раздела "Начинки"
    @FindBy(how = How.XPATH, using = "//*[text()='Начинки']")
    private SelenideElement fillingsTab;

    // локатор класса после выбора раздела
    @FindBy(how = How.CLASS_NAME, using = "tab_tab_type_current__2BEPc")
    private SelenideElement ingredientSection;

    public void waitForLoadMainPage() {
        assembleBurgerHeader.shouldBe(visible, Duration.ofSeconds(3));
    }

    @Step("Клик на кнопку Войти в аккаунт")
    public LoginPage clickLoginButton() {
        loginButton.click();
        return page(LoginPage.class);
    }

    @Step("Клик на кнопку Личный кабинет (неавторизованный пользователь)")
    public LoginPage clickProfileButtonByUnauthorizedUser() {
        profileButton.click();
        return page(LoginPage.class);
    }

    @Step("Клик на кнопку Личный кабинет (авторизованный пользователь)")
    public ProfilePage clickProfileButtonByAuthorizedUser() {
        profileButton.shouldBe(visible).click();
        return page(ProfilePage.class);
    }
    @Step("Клик на раздел Булки")
    public MainPage clickBunsTab() {
        bunTab.click();
        return this;
    }

    @Step("Переход в раздел Булки")
    public boolean isBunsTabDisplayed() {
        return ingredientSection.getText().contentEquals("Булки");
    }
    @Step("Клик на раздел Соусы")
    public MainPage clickSauceTab() {
        sauceTab.click();
        return this;
    }

    @Step("Переход в раздел Соусы")
    public boolean isSauceTabDisplayed() {
        return ingredientSection.getText().contentEquals("Соусы");
    }

    @Step("Клик на раздел Начинки")
    public MainPage clickFillingsTab() {
        fillingsTab.click();
        return this;
    }

    @Step("переход в раздел Начинки")
    public boolean isFillingsTabDisplayed() {
        return ingredientSection.getText().contentEquals("Начинки");
    }

    @Step("Проверка загрузки страницы")
    public boolean isMainPageOpen() {
        assembleBurgerHeader.shouldBe(visible);
        return url().equals(URL_MAIN);
    }
}
