package authentification;

import library.aes.Application;
import utility.ProfileType;

public class MagnetStripe {
    private final String secretKey = "dhbw$20^20_1253402%SE1";
    private ProfileType profileType;
    private String encPin;

    public MagnetStripe(ProfileType type, int pin) {
        this.profileType = type;
        this.encPin = new Application().encrypt(String.valueOf(pin), secretKey);
    }

    public ProfileType getProfileType() {
        return profileType;
    }

    public boolean checkPIN(int pin) {
        return new Application().decrypt(encPin, secretKey).equals(String.valueOf(pin));
    }
}
