package com.krzysztof.jastrzebski.paymentor.model;

public class Card {
    private final String cardNumber;

    public Card(String cardNumber) {//Optional<String>
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
