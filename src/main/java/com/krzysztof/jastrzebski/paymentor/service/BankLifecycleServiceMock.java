package com.krzysztof.jastrzebski.paymentor.service;

import org.springframework.stereotype.Service;

@Service
public class BankLifecycleServiceMock implements BankLifecycleService {
    @Override
    public Boolean isBankSuspended(String bankId) throws NoSuchBankException, TechnicalException {
        return false;
    }
}
