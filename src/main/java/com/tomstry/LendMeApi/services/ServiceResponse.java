package com.tomstry.LendMeApi.services;

import java.util.List;

public class ServiceResponse {

    public ServiceResponse(boolean successful) {
        this.successful = successful;
    }

    private List<String> messages;
    private boolean successful;

    public List<String> getMessages() {
        return messages;
    }

    public boolean isSuccessful() {
        return successful;
    }
}
