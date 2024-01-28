package edu.bbte.idde.gvim2021.utilities;

public final class Credentials {
    private static final String USERNAME = "me";
    private static final String PASSWORD = "pw";

    private Credentials() {
    }

    public static String getUsername() {
        return USERNAME;
    }

    public static String getPassword() {
        return PASSWORD;
    }
}
