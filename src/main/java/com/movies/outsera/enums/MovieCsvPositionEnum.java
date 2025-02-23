package com.movies.outsera.enums;

import lombok.Getter;

@Getter
public enum MovieCsvPositionEnum {
    YEAR(0),
    TITLE(1),
    STUDIOS(2),
    PRODUCERS(3),
    WINNER(4);

    private final int value;

    MovieCsvPositionEnum(int value) {
        this.value = value;
    }
}
