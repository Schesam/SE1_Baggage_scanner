package library.aes;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

public class Application {
    private SecretKeySpec secretKey;
    private byte[] key;

    public static void main(String... args) {
        Application application = new Application();

        final String secretKey = "dhbw$20^20_";

        String plainMessage = "***I***0614***";
        String encryptedString = application.encrypt(plainMessage, secretKey);
        String decryptedString = application.decrypt(encryptedString, secretKey);

        System.out.println(plainMessage);
        System.out.println(encryptedString);
        System.out.println(decryptedString);
    }

    public String decrypt(String encryptedMessage, String key) {
        try {
            setKey(key);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedMessage)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void setKey(String inputKey) {
        MessageDigest sha;

        try {
            key = inputKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String encrypt(String plainMessage, String key) {
        try {
            setKey(key);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(plainMessage.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}