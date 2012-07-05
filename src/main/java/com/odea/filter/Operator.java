package com.odea.filter;

public enum Operator {
    MAYOR(">"),
    MENOR("<"),
    IGUAL("="),
    LIKE (" like "),
    DIFERENTE("!=");

    private final String symbol;

    Operator(String symbol) {
        this.symbol = symbol;
    }

    public String symbol() {
        return this.symbol;
    }

    public String exp(String value) {
        return this.symbol + value;
    }
}