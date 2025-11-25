package menu_service.menu_service.Config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {

        String scope = jwt.getClaimAsString("scope");

        List<SimpleGrantedAuthority> authorities =
                (scope == null || scope.isBlank())
                        ? List.of()
                        : Arrays.stream(scope.split(" "))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .map(role -> {
                            if (!role.startsWith("ROLE_")) {
                                return "ROLE_" + role;
                            }
                            return role;
                        })
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new JwtAuthenticationToken(jwt, authorities, jwt.getSubject());
    }
}

