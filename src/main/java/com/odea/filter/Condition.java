package com.odea.filter;

import java.io.Serializable;

/**
 * User: pbergonzi
 * Date: 04/07/12
 * Time: 18:00
 */

public class Condition implements Serializable, Cloneable {
    private Operator operator;
    private Field field;
    private String value;

    public Condition() {
    }

    public Condition(Operator operator, Field field, String value) {
        this.operator = operator;
        this.field = field;
        this.value = value;
    }

    @Override
    protected Condition clone() {
        try {
            Condition clone = (Condition) super.clone();
            clone.setField(this.field.clone());
            return clone;
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String getCondition() {
        /*if (this.field.getType().equals(Field.Type.TEXT)) {
            this.value = "'" + this.value + "'";
        } */
        return this.field.getColumnName() + this.operator.exp(this.value);
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
