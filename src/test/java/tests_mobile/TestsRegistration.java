package tests_mobile;

import models.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.AuthentificationScreen;
import screens.ContactListScreen;
import screens.ErrorScreen;
import screens.SplashScreen.*;

import static utils.UserFactory.*;

public class TestsRegistration extends TestBase {

    AuthentificationScreen authentificationScreen;


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
        Assert.assertTrue(new ErrorScreen(driver).validateErrorText("username=must not be blank",10));
    }
}
