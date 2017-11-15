package model;

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
        return "("  + min + (max != null ? (", " + max) : "") + ')';
    }

}
