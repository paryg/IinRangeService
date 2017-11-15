package com.krzysztof.jastrzebski.paymentor.service;

import com.krzysztof.jastrzebski.paymentor.model.Bank;
import com.krzysztof.jastrzebski.paymentor.model.Card;
import com.krzysztof.jastrzebski.paymentor.model.Response;
import com.krzysztof.jastrzebski.paymentor.model.ResponseType;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {

    private static Logger log = Logger.getLogger(EnrollmentService.class);

    @Autowired
    private BankLifecycleService bankLifecycleService;
    @Autowired
    private BankRepository bankRepository;

    public Response enrollCard(Card card) {
        Validate.isTrue(card != null, "Cannot process null card!");
        List<Bank> supportedBanks;
        try {
            supportedBanks = getSupportedBanks(getAllCorrespondingBanks(card));
        } catch (TechnicalException e) {
            log.warn("TechnicalException from BankLifecycleService!", e);
            return new Response(card.getCardNumber(), ResponseType.EXCEPTION);
        }

        if (supportedBanks.isEmpty()) {
            return new Response(card.getCardNumber(), ResponseType.REJECT);
        } else if (supportedBanks.size() > 1) {
            log.debug(String.format("Card %s is eligible for multiple supported banks! (%s)", card, supportedBanks));
            return new Response(card.getCardNumber(), ResponseType.AMBIGUOUS_IIN, supportedBanks);
        }
        return new Response(card.getCardNumber(), ResponseType.APPROVE, supportedBanks);
    }

    public List<Response> enrollCards(List<Card> cards) {
        List<Response> responses = new ArrayList<>();
        for (Card card :
                cards) {
            responses.add(enrollCard(card));
        }
        return responses;
    }

    private List<Bank> getAllCorrespondingBanks(Card card) {
        return bankRepository.getBankSet().stream()
                .filter(bank -> bank.inRange(card))
                .collect(Collectors.toList());
    }

    private List<Bank> getSupportedBanks(List<Bank> correspondingBanks) throws TechnicalException {
        List<Bank> supportedBanks = new ArrayList<>();
        for (Bank correspondingBank : correspondingBanks) {
            if(isBankSupported(correspondingBank.getName())) {
                supportedBanks.add(correspondingBank);
            }
        }
        return supportedBanks;
    }

    private Boolean isBankSupported(String bankName) throws TechnicalException {
        try {
            return !bankLifecycleService.isBankSuspended(bankName);
        } catch (NoSuchBankException e) {
            log.info(String.format("Bank %s is not supported by BankLifecycleService! Returning false.", bankName), e);
            return false;
        }
    }

    protected void setBankLifecycleService(BankLifecycleService bankLifecycleService) {
        this.bankLifecycleService = bankLifecycleService;
    }

    protected void setBankRepository(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }
}
