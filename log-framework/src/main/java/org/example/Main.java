package org.example;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private final static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("info---");
        log.debug("debug---");
        log.error("error---");
        log.warn("warn---");
        log.trace("trace---");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                log.info("sadasdasd");
            }
        };
        Thread aa = new Thread(runnable,"AA");
        aa.run();

    }
}