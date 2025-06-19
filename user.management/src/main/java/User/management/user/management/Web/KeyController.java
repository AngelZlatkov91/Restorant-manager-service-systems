package User.management.user.management.Web;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class KeyController {

    private  final RSAKey rsaKey;

    public KeyController(RSAKey rsaKey) {
        this.rsaKey = rsaKey;
    }

    @GetMapping("/.well-known/jwks.json")
    public Map<String,Object> keys() {
        return new JWKSet(rsaKey).toJSONObject();
    }
}
