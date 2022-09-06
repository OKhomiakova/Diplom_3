package ru.practicum.ui;

import POJO.User;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.page.object.LoginPage;
import ru.practicum.page.object.RegisterPage;
import ru.practicum.ui.steps.UserTestSteps;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.Assert.assertTrue;

public class RegistrationTest {
    private RegisterPage registerPage;
    private LoginPage loginPage;
    private static UserTestSteps userTestSteps;
    private static User user;
    private String accessToken;

    @Before
    public void setUp() {
        //Configuration.browser = "firefox"; // запуск тестов в FireFox

        loginPage = page(LoginPage.class);
        registerPage = open(RegisterPage.URL_REGISTER, RegisterPage.class);

        user = User.generateRandomUser();
        userTestSteps = new UserTestSteps();
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
    @DisplayName("Успешная регистрацию")
    public void loginUserSuccess() {
        registerPage.fillInputsFieldsAndRegister(user.getName(), user.getEmail(), user.getPassword());
        assertTrue(loginPage.isLoginPageOpen());
    }

    @Test
    @DisplayName("Регистрация пользователя с некорректным паролем")
    public void loginUserWithInvalidPassword() {
        registerPage.fillInputsFieldsAndRegister(user.getName(), user.getEmail(), RandomStringUtils.randomAlphanumeric(5));
        User credentials = new User(user.getEmail(), user.getPassword(), null);
        accessToken = userTestSteps.loginUser(credentials);
        registerPage.getInvalidPasswordErrorMessage().shouldBe(visible);
        registerPage.getInvalidPasswordErrorText().equals("Некорректный пароль");
    }
}
