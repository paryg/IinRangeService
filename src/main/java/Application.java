import model.Card;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.*;

public class Application {
    public static void main(String[] args) throws TechnicalException, NoSuchBankException {
        ApplicationContext context =
                new ClassPathXmlApplicationContext(new String[] {"SpringBeans.xml"});

        BankLifecycleService bankLifecycleService = context.getBean(BankLifecycleServiceMock.class);
        System.out.println(bankLifecycleService.isBankSuspended("test"));

        EnrollmentService enrollmentService = context.getBean(EnrollmentService.class);
        enrollmentService.enrollCard(new Card("601234"));

    }
}
