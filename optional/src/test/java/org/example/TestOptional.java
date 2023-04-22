package org.example;

import org.junit.Test;

import java.util.Optional;


public class TestOptional {
    @Test
    public void testA() {

    }

    private Optional<Member> getMember() {

        return Optional.of(new Member("my name"));
    }

}