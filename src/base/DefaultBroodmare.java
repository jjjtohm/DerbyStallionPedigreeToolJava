package base;

import java.util.List;
import java.util.Objects;

public class DefaultBroodmare extends BreedableBroodmare {
    private String system_;
    private int fee_;
    private int speed_;
    private int stamina_;
    private int power_;
    private Dirt dirt_;

    public DefaultBroodmare(
        String name, String system, int fee, int speed, int stamina, int power,
        Dirt dirt, Ancestors ancestors, List<Integer> omoshiro
    ) throws IllegalArgumentException, NullPointerException {
        super(name, ancestors, omoshiro);
        system_  = Objects.requireNonNull(system);
        fee_     = Objects.requireNonNull(fee);
        speed_   = Objects.requireNonNull(speed);
        stamina_ = Objects.requireNonNull(stamina);
        power_   = Objects.requireNonNull(power);
        dirt_    = Objects.requireNonNull(dirt);
    }

    public String getSyetem() {
        return system_;
    }

    public int getFee() {
        return fee_;
    }

    public int getSpeed() {
        return speed_;
    }

    public int getStamina() {
        return stamina_;
    }

    public int getPower() {
        return power_;
    }

    public Dirt getDirt() {
        return dirt_;
    }
}
