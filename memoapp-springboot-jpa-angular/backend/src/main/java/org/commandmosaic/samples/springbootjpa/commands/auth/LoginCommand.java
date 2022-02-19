package org.commandmosaic.samples.springbootjpa.commands.auth;

import org.commandmosaic.samples.springbootjpa.dto.LoginDTO;
import org.commandmosaic.api.CommandContext;
import org.commandmosaic.security.core.Identity;
import org.commandmosaic.security.jwt.command.JwtTokenLoginCommand;
import org.commandmosaic.security.jwt.core.TokenProvider;

public class LoginCommand extends JwtTokenLoginCommand<LoginDTO> {

    public LoginCommand(TokenProvider tokenProvider) {
        super(tokenProvider);
    }

    protected LoginDTO getLoginResponse(String jwtToken, Identity identity, CommandContext context) {

        return new LoginDTO(jwtToken, identity.getName(), identity.getAuthorities());
    }

}
