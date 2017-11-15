package com.krzysztof.jastrzebski.paymentor.model;

import org.apache.commons.lang3.Validate;

public class Card {
    private final String cardNumber;

    public Card(String cardNumber) {
        Validate.isTrue(cardNumber != null, "Cannot create card with null number!");
        this.cardNumber = cardNumber.replaceAll("[^0-9]", "");
    }

    public String getCardNumber() {
        return cardNumber;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardNumber='" + cardNumber + '\'' +
                '}';
    }


}
