package com.krzysztof.jastrzebski.paymentor.model;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class RangeTest {

    private static final String CARD_NUMBER_0 = "0";
    private static final String CARD_NUMBER_10 = "10";
    private static final String CARD_NUMBER_09000 = "09000";
    private static final String CARD_NUMBER_10000 = "10000";
    private static final String CARD_NUMBER_15000 = "15000";
    private static final String CARD_NUMBER_25000 = "25000";
    private static final String RANGE_10 = "10";
    private static final String RANGE_150 = "150";
    private static final String RANGE_200 = "200";

    private Object[] wideRangeParameters() {
        return new Object[] {
                new Object[] { CARD_NUMBER_10000, RANGE_10, RANGE_200, true },
                new Object[] { CARD_NUMBER_10000, RANGE_10, RANGE_10, true },
                new Object[] { CARD_NUMBER_10, RANGE_10, RANGE_10, true },
                new Object[] { CARD_NUMBER_25000, RANGE_10, RANGE_200, false },
                new Object[] { CARD_NUMBER_15000, RANGE_10, RANGE_200, true },
                new Object[] { CARD_NUMBER_15000, RANGE_10, RANGE_150, true },
                new Object[] { CARD_NUMBER_09000, RANGE_10, RANGE_200, false },
                new Object[] { CARD_NUMBER_0, RANGE_10, RANGE_200, false },
                new Object[] { CARD_NUMBER_10, RANGE_10, RANGE_200, false },
        };
    }

    @Test
    @Parameters(method = "wideRangeParameters")
    public void shouldWideRangeContainGivenCardNumber(String cardNumber, String rangeMin, String rangeMax, boolean isContained) throws Exception {
        Range range = new Range(new String[]{rangeMin, rangeMax});

        Boolean result = range.contains(cardNumber);

        assertEquals(isContained, result);
    }

    private Object[] narrowRangeParameters() {
        return new Object[] {
                new Object[] { CARD_NUMBER_10000, RANGE_10, true },
                new Object[] { CARD_NUMBER_09000, RANGE_10, false },
                new Object[] { CARD_NUMBER_15000, RANGE_10, false },
                new Object[] { CARD_NUMBER_0, RANGE_10, false },
                new Object[] { CARD_NUMBER_10, RANGE_10, true },
        };
    }

    @Test
    @Parameters(method = "narrowRangeParameters")
    public void shouldNarrowRangeContainGivenCardNumber(String cardNumber, String rangeMin, boolean isContained) throws Exception {
        Range range = new Range(new String[]{rangeMin});

        Boolean result = range.contains(cardNumber);

        assertEquals(isContained, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenIncorrectRangePassedToConstructor() {
        new Range(new String[]{RANGE_10, RANGE_150, RANGE_200});
    }
}