package com.winner.client.userservice.user.application.command;

public record LoginCommand(
    String userName,
    String password) {

}
