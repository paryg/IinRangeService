package com.krzysztof.jastrzebski.paymentor;

import com.krzysztof.jastrzebski.paymentor.model.Card;
import com.krzysztof.jastrzebski.paymentor.model.Response;
import com.krzysztof.jastrzebski.paymentor.service.BankRepository;
import com.krzysztof.jastrzebski.paymentor.service.BankDeterminerService;
import com.krzysztof.jastrzebski.paymentor.service.NoSuchBankException;
import com.krzysztof.jastrzebski.paymentor.service.TechnicalException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Application {

    private static final String ROLBANK_CARD = "6012345678901234";
    private static final String AMBIGUOUS_CARD = "53581234";
    private static final String UNSUPPORTED_CARD = "1111111111";

    public static void main(String[] args) throws TechnicalException, NoSuchBankException {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"SpringBeans.xml"});
        BankRepository bankRepository = context.getBean(BankRepository.class);
        BankDeterminerService bankDeterminerService = context.getBean(BankDeterminerService.class);
        System.out.println("---------------");
        System.out.println("Available banks: " + bankRepository.getBankSet());
        System.out.println("---------------");
        if (args.length == 0) {
            System.out.println("Provide either a single card number or a list of card numbers, otherwise you'll see this demo:");
            System.out.println("---------------");

            System.out.println("Determining eligible banks for RolBank card...");
            Card rolBankCard = new Card(ROLBANK_CARD);
            Response rolBankResult = bankDeterminerService.determineEligibleBank(rolBankCard);
            System.out.println("Card " + rolBankCard + " was processed with the following result: " + rolBankResult.getType() + " for bank " + rolBankResult.getEligibleBanks());
            System.out.println("---------------");

            System.out.println("Determining eligible banks for an ambiguous card...");
            Card ambiguousCard = new Card(AMBIGUOUS_CARD);
            Response ambiguousResult = bankDeterminerService.determineEligibleBank(ambiguousCard);
            System.out.println("Card " + ambiguousCard + " was processed with the following result: " + ambiguousResult.getType()+ " for bank " + ambiguousResult.getEligibleBanks());
            System.out.println("---------------");

            System.out.println("Determining eligible banks for an unsupported card...");
            Card unsupportedCard = new Card(UNSUPPORTED_CARD);
            Response unsupportedResult = bankDeterminerService.determineEligibleBank(unsupportedCard);
            System.out.println("Card " + unsupportedCard + " was processed with the following result: " + unsupportedResult.getType());
            System.out.println("---------------");

            System.out.println("Determining eligible banks for all three Cards as a List...");
            List<Card> cardList = Arrays.asList(rolBankCard, ambiguousCard, unsupportedCard);
            List<Response> resultList = bankDeterminerService.determineEligibleBankForCardList(cardList);
            System.out.println("Cards " + cardList + " were processed with the following result: " + resultList);
            System.out.println("---------------");
        } else if (args.length == 1) {
            System.out.println("Determining eligible banks for a single card...");
            Card singleCard = new Card(args[0]);
            Response rolBankResult = bankDeterminerService.determineEligibleBank(singleCard);
            System.out.println("Card " + singleCard + " was processed with the following result: " + rolBankResult.getType() + (!rolBankResult.getEligibleBanks().isEmpty() ? " for bank " + rolBankResult.getEligibleBanks() : ""));
            System.out.println("---------------");
        } else {
            System.out.println("Determining eligible banks for a list of cards...");
            List<Card> cardList = Arrays.stream(args).map(Card::new).collect(Collectors.toList());
            List<Response> resultList = bankDeterminerService.determineEligibleBankForCardList(cardList);
            System.out.println("Cards " + cardList + " were processed with the following result: " + resultList);
            System.out.println("---------------");
        }
    }
}
