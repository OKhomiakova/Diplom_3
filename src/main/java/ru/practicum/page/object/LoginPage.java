package ru.practicum.page.object;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.codeborne.selenide.WebDriverRunner.url;

import java.time.Duration;

public class LoginPage {

    public final static String URL_LOGIN = "https://stellarburgers.nomoreparties.site/login";

    // локатор поля "Email"
    @FindBy(how = How.XPATH, using = "//input[@name='name']")
    private SelenideElement emailInput;

    // локатор поля "Пароль"
    @FindBy(how = How.XPATH, using = "//input[@name='Пароль']")
    private SelenideElement passwordInput;

    @FindBy(how = How.XPATH, using = "//button[text()='Войти']")
    private SelenideElement loginFormButton;

    // локатор кнопки "Войти"
    @FindBy(how = How.XPATH, using = "//a[text()='Зарегистрироваться']")
    private SelenideElement registrationButton;

    // локатор ссылки "Зарегистрироваться"
    @FindBy(how = How.XPATH, using = "//a[text()='Восстановить пароль']")
    private SelenideElement recoverPasswordButton;

    @FindBy(how = How.XPATH, using = "//h2[text()='Вход']")
    private SelenideElement loginPageHeader;

    public void waitForLoadLoginPage() {
        loginPageHeader.shouldBe(Condition.visible, Duration.ofSeconds(4));
    }

    public void setEmailField(String userEmail) {
        emailInput.click();
        emailInput.setValue(userEmail);
    }

    public void setPasswordField(String userPassword) {
        passwordInput.click();
        passwordInput.setValue(userPassword);
    }

    public void loginButtonClick() {
        loginFormButton.click();
    }

    @Step("Клик по кнопке Зарегистрироваться")
    public void registerButtonClick() {
        registrationButton.click();
    }

    @Step("Получение текста заголовка Вход")
    public String getLoginPageHeaderText() {
        return loginPageHeader.getText();
    }

    @Step("Заполнение полей Email, Пароль и клик по кнопке Войти")
    public void fillInputsAndLogin(String email, String password) {
        setEmailField(email);
        setPasswordField(password);
        loginButtonClick();
    }

    @Step("Проверка загрузки страницы Входа")
    public boolean isLoginPageOpen() {
        loginPageHeader.shouldBe(Condition.visible);
        return url().equals(URL_LOGIN);
    }
}
