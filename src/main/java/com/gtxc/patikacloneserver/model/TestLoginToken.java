package com.gtxc.patikacloneserver.model;

/*
    Created by gt at 1:23 PM on Monday, March 07, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.model.
*/

import java.io.Serializable;

public class TestLoginToken implements Serializable {

    private String token;

    public TestLoginToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
