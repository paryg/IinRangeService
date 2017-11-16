package com.krzysztof.jastrzebski.paymentor.model;

public enum ResponseType {
    APPROVE,
    REJECT,
    AMBIGUOUS_IIN, //For cases when card's IIN/PAN matches two or more banks (e.g. 5358 for OMG and RolBank)
    EXCEPTION
}
