package com.krzysztof.jastrzebski.paymentor.model;

import org.apache.commons.lang3.Validate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Bank {
    private String name;
    private List<Range> rangeList;

    public Bank(String name, String range) {
        this.name = name;
        rangeList = process(range);
    }

    private List<Range> process(String range) {
        String[] ranges = range.split("\\s*,\\s*");
        Validate.notEmpty(ranges, String.format("No valid range provided for input '%s'!", range));
        return Arrays.stream(ranges)
                .map(this::convertToRange)
                .collect(Collectors.toList());
    }

    private Range convertToRange(String rawRange) {
        String[] borderRanges = rawRange.split("\\s*-\\s*");
        return new Range(borderRanges);
    }

    public Boolean inRange(Card card) {
        return rangeList.stream()
                .anyMatch(range -> range.contains(card.getCardNumber()));
    }

    @Override
    public String toString() {
        return "Bank{" +
                "name='" + name + '\'' +
                ", rangeList=" + rangeList +
                '}';
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bank bank = (Bank) o;

        if (!name.equals(bank.name)) return false;
        return rangeList.equals(bank.rangeList);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
