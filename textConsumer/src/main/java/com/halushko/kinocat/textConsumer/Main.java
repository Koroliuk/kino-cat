package com.halushko.kinocat.textConsumer;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world! I'm textConsumer!");
        org.apache.log4j.BasicConfigurator.configure();
        new UserMessageHandler().run();
    }
}