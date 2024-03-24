package base;

import java.util.Objects;

public class Thoroughbred {
    private final String name_;
    private final Ancestors ancestors_;

    public Thoroughbred(
        String name, Ancestors ancestors
    ) throws NullPointerException {
        name_      = Objects.requireNonNull(name);
        ancestors_ = Objects.requireNonNull(ancestors);
    }

    public String getName() {
        return name_;
    }

    public Ancestors getAncestors() {
        return ancestors_;
    }
}
