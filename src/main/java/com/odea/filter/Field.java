package com.odea.filter;

import java.io.Serializable;

/**
 * User: pbergonzi
 * Date: 05/07/12
 * Time: 14:33
 */

public class Field implements Serializable, Cloneable {
    final String name;
    final Type type;
    final String columnName;

    @Override
    protected Field clone() throws CloneNotSupportedException {
        return (Field) super.clone();
    }

    public Field(String name, Type type, String columnName) {
        this.name = name;
        this.type = type;
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public static enum Type {TEXT, NUMERIC}
}
