package org.mock;


import java.util.Optional;


public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        Optional<Member> member = main.getMember();
        member.ifPresent(item -> System.out.println(item.getName()));
        System.out.println("2:" + member.map(Member::getName).orElse("asd"));
    }

    private Optional<Member> getMember() {

        return Optional.ofNullable(null);
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