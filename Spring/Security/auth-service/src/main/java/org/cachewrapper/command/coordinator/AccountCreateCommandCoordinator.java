package org.cachewrapper.command.coordinator;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.aggregate.repository.UserCredentialsAggregateRepository;
import org.cachewrapper.command.domain.AccountCreateCommand;
import org.cachewrapper.command.handler.AccountCreateCommandHandler;
import org.cachewrapper.exception.EmailAlreadyExistsException;
import org.cachewrapper.exception.UsernameAlreadyExistsException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountCreateCommandCoordinator implements CommandCoordinator<AccountCreateCommand> {

    private final UserCredentialsAggregateRepository credentialsAggregateRepository;
    private final AccountCreateCommandHandler accountCreateCommandHandler;

    @Override
    public void coordinate(AccountCreateCommand command) {
        var email = command.email();
        var username = command.username();

        var isEmailAlreadyPresent = credentialsAggregateRepository
                .findUserCredentialsAggregateByEmail(email)
                .isPresent();
        if (isEmailAlreadyPresent) {
            throw new EmailAlreadyExistsException(email);
        }

        var isUsernameAlreadyPresent = credentialsAggregateRepository
                .findUserCredentialsAggregateByUsername(username)
                .isPresent();
        if (isUsernameAlreadyPresent) {
            throw new UsernameAlreadyExistsException(username);
        }

        accountCreateCommandHandler.handleCommand(command);
    }
}