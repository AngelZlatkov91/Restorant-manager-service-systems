package User.management.user.management.Web;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class KeyControllerTest {
    private RSAKey rsaKey;
    private KeyController keyController;

    @BeforeEach
    void setUp() throws Exception {
        rsaKey = new RSAKeyGenerator(2048).keyID("test-key").generate();
        keyController = new KeyController(rsaKey);
    }

    @Test
    void testKeys_ReturnsJwksJson() {
        Map<String, Object> result = keyController.keys();

        assertNotNull(result);
        assertTrue(result.containsKey("keys"));

        Object keysObj = result.get("keys");
        assertInstanceOf(List.class, keysObj);

        @SuppressWarnings("unchecked")
        Map<String, Object> keyMap = ((java.util.List<Map<String, Object>>) keysObj).get(0);

        assertEquals(rsaKey.getKeyID(), keyMap.get("kid"));
        assertEquals("RSA", keyMap.get("kty"));
    }
}