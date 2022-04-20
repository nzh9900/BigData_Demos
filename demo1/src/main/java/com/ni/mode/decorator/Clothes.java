package com.ni.mode.decorator;

import java.util.Optional;

public class Clothes extends Person {
    protected Person person;

    public Clothes(String name) {
        super(name);
    }

    public Clothes() {
    }

    public void component(Person person) {
        this.person = person;
    }

    @Override
    public void show() {
        this.person.show();
    }
}
