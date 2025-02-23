package com.movies.outsera.enums;

import lombok.Getter;

@Getter
public enum TypeInterval {
    MAX("MAX"),
    MIN("MIN");

    private final String value;

    TypeInterval(String value) {
        this.value = value;
    }
}
