package com.krzysztof.jastrzebski.paymentor.service;

import com.krzysztof.jastrzebski.paymentor.model.Bank;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class BankRepository {

    @Resource(name = "bankProperties")
    private Properties bankProperties;

    private Set<Bank> bankSet = new HashSet<>();

    private final static String NAME_POSTFIX = "name";
    private final static String RANGE_POSTFIX = "range";
    private final static String PROPERTY_TEMPLATE = "%s.%s";

    @PostConstruct
    public void loadBankSet() {
        Set<String> banks = extractShortBankNamesFromProperties(bankProperties);
        for (String bankProperty : banks) {
            String bankName = bankProperties.getProperty(String.format(PROPERTY_TEMPLATE, bankProperty, NAME_POSTFIX));
            String bankRange = bankProperties.getProperty(String.format(PROPERTY_TEMPLATE, bankProperty, RANGE_POSTFIX));
            if (!StringUtils.isEmpty(bankName) && !StringUtils.isEmpty(bankRange)) {
                bankSet.add(new Bank(bankName, bankRange));
            }
        }
    }

    private Set<String> extractShortBankNamesFromProperties(Properties properties) {
        return properties.stringPropertyNames().stream()
                .map(property -> property.substring(0, property.indexOf('.')))
                .collect(Collectors.toSet());
    }

    public Set<Bank> getBankSet() {
        return bankSet;
    }

    protected void setBankProperties(Properties bankProperties) {
        this.bankProperties = bankProperties;
    }
}
