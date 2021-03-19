package com.blog.demo.config;

import org.apache.wss4j.common.ext.WSPasswordCallback;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;

public class SimplePasswordCallback implements CallbackHandler {
    public void handle(Callback[] callbacks) {

        WSPasswordCallback callback = (WSPasswordCallback) callbacks[0];


        // you won't be able to retrieve the password using callback.getPassword().
        // to authenticate a user, you'll need to set the password tied to the user.
        // user credentials are typically retrieved from DB or your own authentication source.
        // if the password set here is the same as the password passed by caller, authentication is successful.
        callback.setPassword("wspassword");

    }
}