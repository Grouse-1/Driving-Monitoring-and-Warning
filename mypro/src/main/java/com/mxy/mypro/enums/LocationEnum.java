package com.mxy.mypro.enums;

public enum LocationEnum {
    MARKET(1, "菜市场"),
    PARK(2, "公园");

    private int value;
    private String name;

    LocationEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static LocationEnum fromValue(int value) {
        for (LocationEnum type : LocationEnum.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid PlaceType value: " + value);
    }

    public static LocationEnum fromName(String name) {
        for (LocationEnum type : LocationEnum.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid PlaceType name: " + name);
    }
}
