package com.krzysztof.jastrzebski.paymentor.model;

import org.apache.commons.lang3.Validate;

/**
 * Representation of a card with minimal validation.
 * Either PAN or IIN can be passed as a constructor parameter.
 *
 * No reason to split representation of PAN or IIN into separate fields
 * as only the shortest part of the PAN/IIN is used to determine the corresponding bank.
 */
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
