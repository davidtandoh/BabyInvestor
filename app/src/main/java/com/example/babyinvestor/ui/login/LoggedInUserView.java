package com.example.babyinvestor.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayName;
    private String id;//added this
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName,String id) {
        this.displayName = displayName;
        this.id = id;
    }


    String getDisplayName() {
        return displayName;
    }

    String getUserId() {
        return id;
    }
}