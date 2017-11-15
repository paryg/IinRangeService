package com.krzysztof.jastrzebski.paymentor.service;

public interface BankLifecycleService {
    Boolean isBankSuspended(String bankId) throws NoSuchBankException, TechnicalException;
}
