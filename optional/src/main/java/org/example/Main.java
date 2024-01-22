package org.mock;


import java.util.Optional;


public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        Optional<Member> member = main.getMember();
        member.ifPresent(item -> System.out.println(item.getName()));
    }

    private Optional<Member> getMember() {

        return Optional.of(new Member("my name"));
    }


    class Member {
        private String name;

        public String getName() {
            return name;
        }

        public Member(String name) {
            this.name = name;
        }
    }
}