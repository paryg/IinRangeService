package service;

public interface BankLifecycleService {
    Boolean isBankSuspended(String bankId) throws NoSuchBankException, TechnicalException;
}
