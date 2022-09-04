package ru.practicum.page.object;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import io.qameta.allure.Step;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Condition.visible;

public class RegisterPage {

    public final static String URL_REGISTER = "https://stellarburgers.nomoreparties.site/register";

        // локатор полей "Имя" и "Email"
        @FindBy(how = How.XPATH, using = "//input[@name='name']")
        private List<SelenideElement> nameAndEmailInputs;

//        // локатор поля "Имя"
//        @FindBy(how = How.XPATH, using = "//label[text()='Имя']/../..")
//        private SelenideElement nameInput;
//
//        // локатор поля "Email"
//        @FindBy(how = How.XPATH, using = "//label[text()='Email']/../..")
//        private SelenideElement emailInput;

        // локатор поля "Пароль"
        @FindBy(how = How.XPATH, using = "//input[@name='Пароль']")
        private SelenideElement passwordInput;

        // локатор кнопки "Зарегистрироваться"
        @FindBy(how = How.XPATH, using = "//button[text()='Зарегистрироваться']")
        private SelenideElement registrationButton;

        // локатор ссылки "Войти"
        @FindBy(how = How.XPATH, using = "//a[text()='Войти']")
        private SelenideElement loginButton;

        // локатор ошибки пароля
        @FindBy(how = How.XPATH, using = "//p[text()='Некорректный пароль']")
        private SelenideElement invalidPasswordErrorMessage;

        @FindBy(how = How.XPATH, using = "//h2[text()='Регистрация']")
        private SelenideElement registrationText;

    public void waitForLoadRegisterPage() {
        registrationText.shouldBe(visible, Duration.ofSeconds(3));
    }

    public SelenideElement getInvalidPasswordErrorMessage() {
        return invalidPasswordErrorMessage;
    }

        public void setNameField(String userName) {
            nameAndEmailInputs.get(0).click();
            nameAndEmailInputs.get(0).setValue(userName);
        }

        public void setEmailField(String userEmail) {
            nameAndEmailInputs.get(1).click();
            nameAndEmailInputs.get(1).setValue(userEmail);
        }

        public void setPasswordField(String userPassword) {
            passwordInput.click();
            passwordInput.setValue(userPassword);
        }

        public void registrationButtonClick() {
            registrationButton.click();
        }

        @Step("Получение текста ошибки при некорректном пароле")
        public String getInvalidPasswordErrorText() {
            return invalidPasswordErrorMessage.getText();
        }

        @Step("Клик по кнопке Войти")
        public void loginButtonClick() {
            loginButton.click();
        }

        @Step("Заполнение полей Имя, Email, Пароль и клик по кнопке Зарегистрироваться")
        public void fillInputsFieldsAndRegister(String name, String email, String password) {
            setNameField(name);
            setEmailField(email);
            setPasswordField(password);
            registrationButtonClick();
        }
}
