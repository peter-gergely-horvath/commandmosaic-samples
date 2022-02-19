package org.commandmosaic.samples.springbootjpa.commands.auth;

import org.commandmosaic.api.CommandContext;
import org.commandmosaic.api.CommandDispatcher;
import org.commandmosaic.core.server.context.DefaultCommandContext;
import org.commandmosaic.samples.springbootjpa.dto.LoginDTO;
import org.commandmosaic.security.AuthenticationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class LoginCommandTest {

    @Autowired
    private CommandDispatcher commandDispatcher;


    @Test
    public void loginCommandWorks() {

        HashMap<String, Object> auth = new HashMap<>();
        auth.put("user", "john");
        auth.put("password", "john1234");

        CommandContext context = new DefaultCommandContext(auth);

        LoginDTO loginDTO = commandDispatcher.dispatchCommand(LoginCommand.class, null, context);

        assertNotNull(loginDTO);

        String loginId = loginDTO.getLoginId();
        assertEquals("john", loginId);

        String token = loginDTO.getToken();
        assertNotNull(token);

        Set<String> roles = loginDTO.getRoles();
        assertEquals(Collections.singleton("USER"), roles);
    }

    @Test
    public void loginCommandDoesNotAuthenticateWithInvalidUserName() {

        HashMap<String, Object> auth = new HashMap<>();
        auth.put("user", "invalid-username");
        auth.put("password", "john1234");

        CommandContext context = new DefaultCommandContext(auth);

        Assertions.assertThrows(AuthenticationException.class, () ->
                commandDispatcher.dispatchCommand(LoginCommand.class, null, context));
    }

    @Test
    public void loginCommandDoesNotAuthenticateWithInvalidPassword() {

        HashMap<String, Object> auth = new HashMap<>();
        auth.put("user", "john");
        auth.put("password", "invalid-password");

        CommandContext context = new DefaultCommandContext(auth);

        Assertions.assertThrows(AuthenticationException.class, () ->
                commandDispatcher.dispatchCommand(LoginCommand.class, null, context));
    }

}
