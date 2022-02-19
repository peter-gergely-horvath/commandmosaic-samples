package org.commandmosaic.samples.springbootjpa;

public final class ApplicationRole {

    private ApplicationRole() {
        throw new AssertionError("static constants class: no instances allowed");
    }

    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";

}
