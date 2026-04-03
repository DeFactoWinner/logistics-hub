package com.winner.client.userservice.user.application.command;

public record LoginCommand(
    String username,
    String password) {

}
