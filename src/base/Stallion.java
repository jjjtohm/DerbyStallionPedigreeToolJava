package base;

import java.util.Objects;

public class Stallion extends Thoroughbred {
    private final Blood blood_;
    private final EffectSet effectSet_;

    public Stallion(
        String name, Ancestors ancestors, Blood blood, EffectSet effectSet
    ) throws NullPointerException {
        super(name, ancestors);
        blood_     = Objects.requireNonNull(blood);
        effectSet_ = Objects.requireNonNull(effectSet);
    }

    public Blood getBlood() {
        return blood_;
    }

    public EffectSet getEffectSet() {
        return effectSet_;
    }
}
