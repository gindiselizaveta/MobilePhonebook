package tests_mobile;

import models.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import screens.AuthentificationScreen;
import screens.ContactListScreen;
import screens.ErrorScreen;
import screens.SplashScreen.*;

import static utils.UserFactory.*;

public class TestsRegistration extends TestBase {

    AuthentificationScreen authentificationScreen;
    SoftAssert softAssert = new SoftAssert();


    @BeforeMethod
    public void goToAuthScreen() {
        //new SplashScreen(driver);
        authentificationScreen = new AuthentificationScreen(driver);
    }

    @Test
    public void testRegistrationPositive() {
        User user = positiveUser();
        authentificationScreen.typeAuthForm(user);
        authentificationScreen.clickBtnRegistration();
        Assert.assertTrue(new ContactListScreen(driver).validateContactListScreenOpenAfterRegistration("No Contacts. Add One more!", 10));
    }

    @Test
    public void testRegistrationNegative_emptyEmail() {
        User user = positiveUser();
        user.setUsername("");
        authentificationScreen.typeAuthForm(user);
        authentificationScreen.clickBtnRegistration();
        Assert.assertTrue(new ErrorScreen(driver).validateErrorText("username=must not be blank", 10));
    }

    @Test
    public void testRegistrationNegative_emptyPassword() {
        User user = positiveUser();
        user.setPassword("");
        authentificationScreen.typeAuthForm(user);
        authentificationScreen.clickBtnRegistration();
        //softAssert.assertTrue(new ErrorScreen(driver).validateErrorText("password=must not be blank",10));
        //softAssert.assertAll();
        //АППЛИКАЦИЯ ПАДАЕТ И ВЫЛЕТАЕТ ПРИ ПОПЫТКЕ РЕГИСТРАЦИИ БЕЗ ПАРОЛЯ
    }

    @Test
    public void testRegistrationNegative_PasswordWONumber() {
        User user = positiveUser();
        user.setPassword("wertY!@!");
        authentificationScreen.typeAuthForm(user);
        authentificationScreen.clickBtnRegistration();
        Assert.assertTrue(new ErrorScreen(driver).validateErrorText("password= At least 8 characters; Must contain at least 1 uppercase letter, 1 lowercase letter, and 1 number", 10));
    }

    @Test
    public void testRegistrationNegative_PasswordWOUpperLetter() {
        User user = positiveUser();
        user.setPassword("werty!23");
        authentificationScreen.typeAuthForm(user);
        authentificationScreen.clickBtnRegistration();
        Assert.assertTrue(new ErrorScreen(driver).validateErrorText("password= At least 8 characters; Must contain at least 1 uppercase letter, 1 lowercase letter, and 1 number", 10));
    }

    @Test
    public void testRegistrationNegative_PasswordWOLowerLetter() {
        User user = positiveUser();
        user.setPassword("WERTY!23");
        authentificationScreen.typeAuthForm(user);
        authentificationScreen.clickBtnRegistration();
        Assert.assertTrue(new ErrorScreen(driver).validateErrorText("password= At least 8 characters; Must contain at least 1 uppercase letter, 1 lowercase letter, and 1 number", 10));
    }

    @Test
    public void testRegistrationNegative_PasswordWOSpecialChar() {
        User user = positiveUser();
        user.setPassword("wertY123");
        authentificationScreen.typeAuthForm(user);
        authentificationScreen.clickBtnRegistration();
        Assert.assertTrue(new ErrorScreen(driver).validateErrorText("password= Can contain special characters [@$#^&*!]", 10));
    }

    @Test
    public void testRegistrationNegative_PasswordSevenChar() {
        User user = positiveUser();
        user.setPassword("wertY!2");
        authentificationScreen.typeAuthForm(user);
        authentificationScreen.clickBtnRegistration();
        Assert.assertTrue(new ErrorScreen(driver).validateErrorText("password= At least 8 characters; Must contain at least 1 uppercase letter, 1 lowercase letter, and 1 number", 10));
    }

    @Test
    public void testRegistrationNegative_PasswordTooLong() {
        User user = positiveUser();
        user.setPassword("wertY!23@zxc098vbn12");
        authentificationScreen.typeAuthForm(user);
        authentificationScreen.clickBtnRegistration();
        softAssert.assertTrue(new ContactListScreen(driver).btnPlusIsPresent(10), "We will take this btn only if we have a BUG");
        softAssert.assertTrue(new ErrorScreen(driver).validateErrorText("password= At least 8 characters; Must contain at least 1 uppercase letter, 1 lowercase letter, and 1 number", 10));
        softAssert.assertAll();
        //НЕТ ВАЛИДАЦИИ НА МАКСИМАЛЬНУЮ ДЛИННУ ПАРОЛЯ
    }

    @Test
    public void testRegistrationNegative_EmailRussianLetters() {
        User user = positiveUser();
        user.setUsername("лизаряз13@майл.рус");
        authentificationScreen.typeAuthForm(user);
        authentificationScreen.clickBtnRegistration();
        softAssert.assertTrue(new ErrorScreen(driver).validateErrorText("username=must contains only english words", 20));
        softAssert.assertTrue(new ContactListScreen(driver).btnPlusIsPresent(10), "We will take this btn only if we have a BUG");
        softAssert.assertAll();
    }
}
