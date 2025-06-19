package User.management.user.management.Service.Interfaces;
import org.springframework.security.core.Authentication;


public interface TokenService {
    public String generateToken(Authentication authentication);

}
