package base;

import java.util.List;
import java.util.Objects;

public class DefaultStallion extends BreedableStallion {
    private String system_;
    private int fee_;
    private int min_;
    private int max_;
    private Dirt dirt_;
    private Growth growth_;
    private Grade health_;
    private Grade temper_;
    private Grade achievement_;
    private Grade spirit_;
    private Grade stable_;

    public DefaultStallion(
        String name, String system, int fee, int min, int max, Dirt dirt,
        Growth growth, Grade health, Grade temper, Grade achievement, Grade spirit,
        Grade stable, Ancestors ancestors, List<Integer> omoshiro, List<Integer> migoto
    ) throws IllegalArgumentException, NullPointerException {
        super(name, ancestors, omoshiro, migoto);
        system_      = Objects.requireNonNull(system);
        fee_         = Objects.requireNonNull(fee);
        min_         = Objects.requireNonNull(min);
        max_         = Objects.requireNonNull(max);
        dirt_        = Objects.requireNonNull(dirt);
        growth_      = Objects.requireNonNull(growth);
        health_      = Objects.requireNonNull(health);
        temper_      = Objects.requireNonNull(temper);
        achievement_ = Objects.requireNonNull(achievement);
        spirit_      = Objects.requireNonNull(spirit);
        stable_      = Objects.requireNonNull(stable);
    }

    public String getSystem() {
        return system_;
    }

    public int getFee() {
        return fee_;
    }

    public int getMin() {
        return min_;
    }

    public int getMax() {
        return max_;
    }

    public Dirt getDirt() {
        return dirt_;
    }

    public Growth getGrowth() {
        return growth_;
    }

    public Grade getHealth() {
        return health_;
    }

    public Grade getTemper() {
        return temper_;
    }

    public Grade getAchievement() {
        return achievement_;
    }

    public Grade getSpirit() {
        return spirit_;
    }

    public Grade getStable() {
        return stable_;
    }
}
