package com.krzysztof.jastrzebski.paymentor.model;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class CardTest {

    private static final String CARD_NUMBER = "123456";
    private static final String DASH_SEPARATED_CARD_NUMBER = "12-34-56";
    private static final String SPACE_SEPARATED_CARD_NUMBER = "12 34 56";

    private Object[] wideRangeParameters() {
        return new Object[] {
                new Object[] { CARD_NUMBER, CARD_NUMBER },
                new Object[] { DASH_SEPARATED_CARD_NUMBER, CARD_NUMBER},
                new Object[] { SPACE_SEPARATED_CARD_NUMBER, CARD_NUMBER},
        };
    }

    @Test
    @Parameters(method = "wideRangeParameters")
    public void shouldMatchGivenResultWithCreatedCardNumber(String inputCardNumber, String result) {
        assertEquals(result, new Card(inputCardNumber).getCardNumber());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenCreatingCardWithNullNumber() {
        new Card(null);
    }
}