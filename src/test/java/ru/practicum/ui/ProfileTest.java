package ru.practicum.ui;

import POJO.User;
import POJO.UserCreds;
import com.codeborne.selenide.Configuration;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import ru.practicum.page.object.*;
import ru.practicum.api.steps.UserTestSteps;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.Assert.assertTrue;

public class ProfileTest {
    private WebDriver driver;
    private RegisterPage registerPage;
    private LoginPage loginPage;
    private MainPage mainPage;
    private ProfilePage profilePage;
    private ForgotPasswordPage forgotPasswordPage;
    private User user;
    private String accessToken;

    @Before
    public void setUp() {
        //Configuration.browser = "firefox"; // запуск тестов в FireFox

        registerPage = open(RegisterPage.URL_REGISTER, RegisterPage.class);

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
    @DisplayName("Переход по клику на «Личный кабинет» (авторизированный пользователь)")
    @Description("Переход по клику на «Личный кабинет» (авторизированный пользователь) -> личный кабинет пользователя")
    public void clickProfileButtonWithAuthorizedUserOpensProfilePage() {
        mainPage = page(MainPage.class);
        profilePage = page(ProfilePage.class);
        loginPage = open(LoginPage.URL_LOGIN, LoginPage.class);
        loginPage.fillInputsAndLogin(user.getEmail(), user.getPassword());
        mainPage.clickProfileButtonByAuthorizedUser();
        assertTrue(profilePage.isProfilePageOpen());
    }

    @Test
    @DisplayName("Переход по клику на «Личный кабинет» (не авторизированный пользователь)")
    @Description("Переход по клику на «Личный кабинет» (не авторизированный пользователь) -> страница авторизации")
    public void clickProfileButtonWithUnauthorizedUserOpensLoginPage() {
        loginPage = page(LoginPage.class);
        mainPage = open(MainPage.URL_MAIN, MainPage.class);
        mainPage.clickProfileButtonByUnauthorizedUser();
        assertTrue(loginPage.isLoginPageOpen());
    }

    @Test
    @DisplayName("Переход по клику на «Конструктор» из личного кабинета")
    public void clickOnConstructorButtonOpensCorrectPage() {
        mainPage = page(MainPage.class);
        profilePage = page(ProfilePage.class);
        loginPage = open(LoginPage.URL_LOGIN, LoginPage.class);
        loginPage.fillInputsAndLogin(user.getEmail(), user.getPassword());
        mainPage.clickProfileButtonByAuthorizedUser();
        profilePage.clickConstructorButton();
        assertTrue(mainPage.isMainPageOpen());
    }

    @Test
    @DisplayName("Переход по клику на логотип Stellar Burgers из личного кабинета")
    public void clickOnStellarBurgersOpensCorrectPage() {
        mainPage = page(MainPage.class);
        profilePage = page(ProfilePage.class);
        loginPage = open(LoginPage.URL_LOGIN, LoginPage.class);
        loginPage.fillInputsAndLogin(user.getEmail(), user.getPassword());
        mainPage.clickProfileButtonByAuthorizedUser();
        profilePage.clicklogoStellarBurgers();
        assertTrue(mainPage.isMainPageOpen());
    }

    @Test
    @DisplayName("Выход по кнопке «Выйти» в личном кабинете")
    public void userLogoutSuccess() {
        mainPage = page(MainPage.class);
        profilePage = page(ProfilePage.class);
        loginPage = open(LoginPage.URL_LOGIN, LoginPage.class);
        loginPage.fillInputsAndLogin(user.getEmail(), user.getPassword());
        mainPage.clickProfileButtonByAuthorizedUser();
        profilePage.clickLogout();
        assertTrue(loginPage.isLoginPageOpen());
    }
}
