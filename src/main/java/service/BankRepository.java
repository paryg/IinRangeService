package service;

import model.Bank;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class BankRepository {
    private Set<Bank> bankSet = new HashSet<>();
    private Properties myProperties;

    private final static String NAME_POSTFIX = ".name";
    private final static String RANGE_POSTFIX = ".range";

    @PostConstruct
    public void loadBankSet() {
        Set<String> banks = myProperties.stringPropertyNames().stream()
                .map(property -> property.substring(0, property.indexOf('.')))
                .collect(Collectors.toSet());
        for (String bankProperty :
                banks) {
            String bankName = myProperties.getProperty(String.format("%s%s", bankProperty, NAME_POSTFIX));
            String bankRange = myProperties.getProperty(String.format("%s%s", bankProperty, RANGE_POSTFIX));
            //if (!StringUtils.isEmpty(bankName) && !StringUtils.isEmpty(bankRange)) {
                bankSet.add(new Bank(bankName, bankRange));
            //}
        }
    }

    public Set<Bank> getBankSet() {
        return bankSet;
    }

    @Resource(name = "myProperties")
    public void setMyProperties(Properties myProperties) {
        this.myProperties = myProperties;
    }
}
