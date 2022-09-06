package ru.practicum.ui;

import POJO.User;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import ru.practicum.page.object.*;
import ru.practicum.ui.steps.UserTestSteps;

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
    private static UserTestSteps userTestSteps;
    private static User user;
    private String accessToken;

    @Before
    public void setUp() {
        //Configuration.browser = "firefox"; // запуск тестов в FireFox

        registerPage = open(RegisterPage.URL_REGISTER, RegisterPage.class);

        user = User.generateRandomUser();
        userTestSteps = new UserTestSteps();
        userTestSteps.createNewUser(user);
    }

    @After
    public void tearDown() {
        getWebDriver().quit();

        User credentials = new User(user.getEmail(), user.getPassword(), null);
        accessToken = userTestSteps.loginUser(credentials);
        if (accessToken != null) {
            userTestSteps.deleteUser(accessToken);
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
