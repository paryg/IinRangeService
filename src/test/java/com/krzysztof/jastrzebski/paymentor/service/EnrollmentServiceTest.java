package com.krzysztof.jastrzebski.paymentor.service;

import com.krzysztof.jastrzebski.paymentor.model.Bank;
import com.krzysztof.jastrzebski.paymentor.model.Card;
import com.krzysztof.jastrzebski.paymentor.model.Response;
import com.krzysztof.jastrzebski.paymentor.model.ResponseType;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(JUnitParamsRunner.class)
public class EnrollmentServiceTest {

    private EnrollmentService enrollmentService;

    private static final String CARD_NUMBER_10000 = "10000";
    private static final String CARD_NUMBER_20000 = "20000";
    private static final String CARD_NUMBER_25000 = "25000";
    private static final String CARD_NUMBER_30000 = "30000";
    private static final String BANK1_NAME = "bank1";
    private static final String BANK2_NAME = "bank2";
    private static final String BANK1_RANGE = "10,30";
    private static final String BANK2_RANGE = "20,30";

    @Mock
    private BankLifecycleService bankLifecycleService;
    @Mock
    private BankRepository bankRepository;

    @Before
    public void setUp() {
        initMocks(this);
        enrollmentService = new EnrollmentService();
        enrollmentService.setBankRepository(bankRepository);
        enrollmentService.setBankLifecycleService(bankLifecycleService);
        doReturn(getBanksData()).when(bankRepository).getBankSet();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenEnrollingNullCard() {
        enrollmentService.enrollCard(null);
    }

    @Test
    public void shouldReturnRejectResponseWhenEnrollingEmptyCard() {
        Card emptyCard = new Card("");

        Response result = enrollmentService.enrollCard(emptyCard);

        assertEquals(ResponseType.REJECT, result.getType());
    }

    private Object[] enrollCardParameters() {
        return new Object[] {
                new Object[] {CARD_NUMBER_10000, false, false, ResponseType.APPROVE, 1},
                new Object[] {CARD_NUMBER_10000, true, false, ResponseType.REJECT, 0},
                new Object[] {CARD_NUMBER_25000, false, false, ResponseType.REJECT, 0},
                new Object[] {CARD_NUMBER_20000, false, false, ResponseType.APPROVE, 1},
                new Object[] {CARD_NUMBER_30000, false, false, ResponseType.AMBIGUOUS_IIN, 2},
                new Object[] {CARD_NUMBER_30000, true, false, ResponseType.APPROVE, 1},
        };
    }

    @Test
    @Parameters(method = "enrollCardParameters")
    public void sShouldMatchGivenResponseTypeWhenEnrollingCard(String cardNumber, boolean isBank1Suspended, boolean isBank2Suspended, ResponseType responseType, int correspondingBankSize) throws TechnicalException, NoSuchBankException {
        Card inputCard = new Card(cardNumber);
        doReturn(isBank1Suspended).when(bankLifecycleService).isBankSuspended(BANK1_NAME);
        doReturn(isBank2Suspended).when(bankLifecycleService).isBankSuspended(BANK2_NAME);

        Response result = enrollmentService.enrollCard(inputCard);

        assertEquals(responseType, result.getType());
        assertEquals(correspondingBankSize, result.getEligibleBanks().size());
    }

    private Object[] enrollCardExceptionParameters() {
        return new Object[] {
                new Object[] {CARD_NUMBER_10000, ResponseType.EXCEPTION, new TechnicalException()},
                new Object[] {CARD_NUMBER_10000, ResponseType.REJECT, new NoSuchBankException()},
        };
    }

    @Test
    @Parameters(method = "enrollCardExceptionParameters")
    public void shouldMatchGivenResponseTypeWhenThrownGivenExceptionWhileEnrollingCard(String cardNumber, ResponseType responseType, Exception thrownException) throws TechnicalException, NoSuchBankException {
        Card inputCard = new Card(cardNumber);
        doThrow(thrownException).when(bankLifecycleService).isBankSuspended(BANK1_NAME);

        Response result = enrollmentService.enrollCard(inputCard);

        assertEquals(responseType, result.getType());
    }

    private Object[] enrollMultipleCardsParameters() {
        return new Object[] {
                new Object[] {Arrays.asList(CARD_NUMBER_10000, CARD_NUMBER_25000), Arrays.asList(ResponseType.APPROVE, ResponseType.REJECT)},
                new Object[] {Collections.EMPTY_LIST, Collections.EMPTY_LIST},
                new Object[] {Arrays.asList(CARD_NUMBER_30000), Arrays.asList(ResponseType.AMBIGUOUS_IIN)},
        };
    }

    @Test
    @Parameters(method = "enrollMultipleCardsParameters")
    public void shouldMatchGivenResponseTypesWhenEnrollingMultipleCards(List<String> cardNumberList, List<ResponseType> responses) throws TechnicalException, NoSuchBankException {
        List<Card> inputList = cardNumberList.stream()
                .map(Card::new)
                .collect(Collectors.toList());
        doReturn(false).when(bankLifecycleService).isBankSuspended(BANK1_NAME);
        doReturn(false).when(bankLifecycleService).isBankSuspended(BANK2_NAME);

        List<Response> result = enrollmentService.enrollCards(inputList);

        assertEquals(responses.size(), result.size());
        for (int i = 0; i < responses.size(); i++) {
            assertEquals(responses.get(i), result.get(i).getType());
        }
    }

    private Set<Bank> getBanksData() {
        Set<Bank> result = new HashSet<>();
        result.add(new Bank(BANK1_NAME,BANK1_RANGE));
        result.add(new Bank(BANK2_NAME,BANK2_RANGE));
        return result;
    }
}