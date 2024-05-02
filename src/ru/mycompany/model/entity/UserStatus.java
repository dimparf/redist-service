package ru.mycompany.model.entity;

import lombok.Getter;

@Getter
public enum UserStatus {
    ONLINE("Онлайн"),
    OFFLINE("Офлайн");

    private final String name;

    UserStatus(String name) {
        this.name = name;
    }
}
