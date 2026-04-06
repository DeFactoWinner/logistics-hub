package com.winner.client.userservice.user.application.dto.command;

public record LoginCommand(
    String userName,
    String password) {

}
