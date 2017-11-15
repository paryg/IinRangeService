package service;

import model.Bank;
import model.Card;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {

    @Autowired
    private BankLifecycleService bankLifecycleService;

    @Autowired
    private BankRepository bankRepository;

    public void enrollCard(Card card) {
        List<Bank> correspondingBanks = getCorrespondingBanks(card);
        Validate.isTrue(correspondingBanks.size() != 0, String.format("Card %s is not eligible for any supported bank!", card));
        Validate.isTrue(correspondingBanks.size() == 1, String.format("Card %s is eligible for multiple supported banks! (%s)", card, correspondingBanks));

        //correspondingBanks.forEach(bank -> bankLifecycleService.isBankSuspended(bank.getName()));
    }

    private List<Bank> getCorrespondingBanks(Card card) {
        return bankRepository.getBankSet().stream()
                .filter(bank -> bank.inRange(card))
                .collect(Collectors.toList());
    }

    public void enrollCards(Collection<Card> cardCollection) {

    }
}
