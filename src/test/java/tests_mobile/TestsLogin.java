package tests_mobile;

import models.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.AuthentificationScreen;
import screens.ContactListScreen;
import screens.ErrorScreen;

import static utils.UserFactory.positiveUser;

public class TestsLogin extends TestBase {

    AuthentificationScreen authentificationScreen;


    @BeforeMethod
    public void goToAuthScreen() {
        //new SplashScreen(driver);
        authentificationScreen = new AuthentificationScreen(driver);
    }

    @Test
    public void testLoginPositive() {
        User user = new User("lizkatest@mail.ru", "wertY!23");
        authentificationScreen.typeAuthForm(user);
        authentificationScreen.clickBtnLogin();
        Assert.assertTrue(new ContactListScreen(driver).btnPlusIsPresent(10));
    }

    @Test
    public void testLoginNegative_emptyEmail() {
        User user = new User("", "wertY!23");
        authentificationScreen.typeAuthForm(user);
        authentificationScreen.clickBtnLogin();
        Assert.assertTrue(new ErrorScreen(driver).validateErrorText("Login or Password incorrect",10));
    }

    @Test
    public void testLoginNegative_emptyPassword() {
        User user = new User("lizkatest@mail.ru", "");
        authentificationScreen.typeAuthForm(user);
        authentificationScreen.clickBtnLogin();
        Assert.assertTrue(new ErrorScreen(driver).validateErrorText("Login or Password incorrect",10));
    }

    @Test
    public void testLoginNegative_noSuchUser() {
        User user = new User("lizkatest@gmail.ru", "wertY!23");
        authentificationScreen.typeAuthForm(user);
        authentificationScreen.clickBtnLogin();
        Assert.assertTrue(new ErrorScreen(driver).validateErrorText("Login or Password incorrect",10));
    }

}
