package self.anderson.ballclock.model;

import java.util.UUID;

public class Ball {

    public Ball() {
        uuid = UUID.randomUUID();
    }

    UUID uuid;

    public UUID getUuid() {
        return uuid;
    }

}
