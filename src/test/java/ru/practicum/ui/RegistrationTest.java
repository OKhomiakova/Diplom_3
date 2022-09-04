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
import ru.practicum.page.object.LoginPage;
import ru.practicum.page.object.RegisterPage;
import ru.practicum.api.steps.UserTestSteps;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.Assert.assertTrue;

public class RegistrationTest {
    private RegisterPage registerPage;
    private LoginPage loginPage;
    private User user;
    private String accessToken;

    @Before
    public void setUp() {
        //Configuration.browser = "firefox"; // запуск тестов в FireFox

        loginPage = page(LoginPage.class);
        registerPage = open(RegisterPage.URL_REGISTER, RegisterPage.class);

        String email = RandomStringUtils.randomAlphanumeric(5) + "@yandex.ru";
        String password = RandomStringUtils.randomAlphanumeric(6);
        String name = RandomStringUtils.randomAlphanumeric(6);
        this.user = new User(email, password, name);
    }

    @After
    public void tearDown() {
        getWebDriver().quit();

        UserCreds credentials = new UserCreds(user.getEmail(), user.getPassword());
        Response response = UserTestSteps.loginUser(credentials);
        accessToken = response.body().jsonPath().getString("accessToken");
        if (accessToken != null) {
            UserTestSteps.deleteUser(accessToken);
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
        registerPage.getInvalidPasswordErrorMessage().shouldBe(visible);
        registerPage.getInvalidPasswordErrorText().equals("Некорректный пароль");
    }

}
