package com.krzysztof.jastrzebski.paymentor.model;

public class Range {
    private String min;
    private String max;

    public Range(String[] ranges) {
        if (ranges.length == 1) {
            min = ranges[0];
        } else if (ranges.length == 2) {
            min = ranges[0];
            max = ranges[1];
        } else {

        }
    }

    public Boolean contains(String cardNumber) {
        if (cardNumber.length() < min.length()) {
            return false;
        } else if (max != null && cardNumber.length() < max.length()) {
            return false;
        }

        String minIin = cardNumber.substring(0, min.length());
        if (max == null && !min.equals(minIin)) {
            return false;
        } else if (max != null) {
            Long minIinValue = Long.parseLong(minIin);
            Long maxIinValue = Long.parseLong(cardNumber.substring(0, max.length()));

            if (minIinValue < Long.parseLong(min) || maxIinValue > Long.parseLong(max)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return max != null ? "("  + min + ", " + max + ")" : min;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Range range = (Range) o;

        if (!min.equals(range.min)) return false;
        return max != null ? max.equals(range.max) : range.max == null;
    }

    @Override
    public int hashCode() {
        int result = min.hashCode();
        result = 31 * result + (max != null ? max.hashCode() : 0);
        return result;
    }
}
