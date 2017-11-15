package com.krzysztof.jastrzebski.paymentor.model;

import java.util.Collections;
import java.util.List;

public class Response {
    private String cardNumber;
    private List<Bank> eligibleBanks;
    private ResponseType type;

    public Response(String cardNumber, ResponseType type, List<Bank> eligibleBanks) {
        this.cardNumber = cardNumber;
        this.eligibleBanks = eligibleBanks;
        this.type = type;
    }

    public Response(String cardNumber, ResponseType type) {
        this.cardNumber = cardNumber;
        eligibleBanks = Collections.EMPTY_LIST;
        this.type = type;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public List<Bank> getEligibleBanks() {
        return eligibleBanks;
    }

    public ResponseType getType() {
        return type;
    }
}