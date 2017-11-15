package com.krzysztof.jastrzebski.paymentor;

import com.krzysztof.jastrzebski.paymentor.model.Card;
import com.krzysztof.jastrzebski.paymentor.model.Response;
import com.krzysztof.jastrzebski.paymentor.service.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
    public static void main(String[] args) throws TechnicalException, NoSuchBankException {
        ApplicationContext context =
                new ClassPathXmlApplicationContext(new String[] {"SpringBeans.xml"});

        BankLifecycleService bankLifecycleService = context.getBean(BankLifecycleServiceMock.class);
        System.out.println(bankLifecycleService.isBankSuspended("test"));

        EnrollmentService enrollmentService = context.getBean(EnrollmentService.class);
        Response result = enrollmentService.enrollCard(new Card("601234"));
        System.out.println(result.getType());
        Response result2 = enrollmentService.enrollCard(new Card("53581234"));
        System.out.println(result2.getType());
    }
}
