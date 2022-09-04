package ru.practicum.ui;

import POJO.User;
import POJO.UserCreds;
import com.codeborne.selenide.Configuration;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.page.object.ForgotPasswordPage;
import ru.practicum.page.object.LoginPage;
import ru.practicum.page.object.MainPage;
import ru.practicum.page.object.RegisterPage;
import ru.practicum.api.steps.UserTestSteps;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.Assert.assertTrue;

public class LoginTest {
    private RegisterPage registerPage;
    private LoginPage loginPage;
    private MainPage mainPage;
    private ForgotPasswordPage forgotPasswordPage;
    private User user;
    private String accessToken;

    @Before
    public void setUp() {
        //Configuration.browser = "firefox"; // запуск тестов в FireFox

        String email = RandomStringUtils.randomAlphanumeric(5) + "@yandex.ru";
        String password = RandomStringUtils.randomAlphanumeric(6);
        String name = RandomStringUtils.randomAlphanumeric(6);
        this.user = new User(email, password, name);

        UserTestSteps.createNewUser(this.user);
    }

    @After
    public void tearDown() {
        getWebDriver().quit();

        UserCreds credentials = new UserCreds(user.getEmail(), user.getPassword());
        Response response = ru.practicum.api.steps.UserTestSteps.loginUser(credentials);
        accessToken = response.body().jsonPath().getString("accessToken");
        if (accessToken != null) {
            ru.practicum.api.steps.UserTestSteps.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной")
    public void loginByClickingLogInButtonOnMainPage() {
        loginPage = page(LoginPage.class);
        mainPage = open(MainPage.URL_MAIN, MainPage.class);
        mainPage.clickLoginButton();
        loginPage.fillInputsAndLogin(user.getEmail(), user.getPassword());
        assertTrue(mainPage.isMainPageOpen());
    }

    @Test
    @DisplayName("Вход через кнопку «Личный кабинет»")
    public void loginByClickingLogInButtonOnProfilePage() {
        loginPage = page(LoginPage.class);
        mainPage = open(MainPage.URL_MAIN, MainPage.class);
        mainPage.clickProfileButtonByUnauthorizedUser();
        loginPage.fillInputsAndLogin(user.getEmail(), user.getPassword());
        assertTrue(mainPage.isMainPageOpen());
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    public void loginByClickingLogInButtonOnRegistrationPage() {
        mainPage = page(MainPage.class);
        loginPage = page(LoginPage.class);
        registerPage = open(RegisterPage.URL_REGISTER, RegisterPage.class);
        //registerPage.waitForLoadRegisterPage();
        registerPage.loginButtonClick();
        //loginPage.waitForLoadLoginPage();
        loginPage.fillInputsAndLogin(user.getEmail(), user.getPassword());
        //mainPage.waitForLoadMainPage();
        assertTrue(mainPage.isMainPageOpen());
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    public void loginByClickingLogInButtonOnForgotPasswordPage() {
        mainPage = page(MainPage.class);
        loginPage = page(LoginPage.class);
        forgotPasswordPage = open(ForgotPasswordPage.URL_FORGOT_PASSWORD, ForgotPasswordPage.class);
        forgotPasswordPage.clickLoginButton();
        //loginPage.waitForLoadLoginPage();
        loginPage.fillInputsAndLogin(user.getEmail(), user.getPassword());
        //mainPage.waitForLoadMainPage();
        assertTrue(mainPage.isMainPageOpen());
    }
}
