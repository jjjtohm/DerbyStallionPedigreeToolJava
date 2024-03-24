package base;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 種付け可能な種牡馬
 */
public class BreedableStallion extends Stallion {
    private final ArrayList<Integer> omoshiro_;
    private final ArrayList<Integer> migoto_;

    public BreedableStallion(
        String name, Ancestors ancestors, List<Integer> omoshiro, List<Integer> migoto
    ) throws IllegalArgumentException, NullPointerException {
        super(name, ancestors, PropertyTool.getBlood(omoshiro.get(0)), new EffectSet());
        if (Objects.isNull(migoto)) {
            throw new NullPointerException("Null is not allowed for migoto.");
        }
        if (omoshiro.size() != 4) {
            throw new IllegalArgumentException("omoshiro list must has 4 elements.");
        }
        if (migoto.size() != 4) {
            throw new IllegalArgumentException("migoto list must has 4 elements.");
        }

        omoshiro_ = new ArrayList<Integer>(omoshiro);
        migoto_   = new ArrayList<Integer>(migoto);
    }

    public int getOmoshiroSireLine(int index) throws ArrayIndexOutOfBoundsException {
        if (index < 0 || index > 3) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return omoshiro_.get(index);
    }

    public int getMigotoSireLine(int index) throws ArrayIndexOutOfBoundsException {
        if (index < 0 || index > 3) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return migoto_.get(index);
    }

    public List<Integer> getOmoshiroSireLine() {
        return new ArrayList<Integer>(omoshiro_);
    }

    public List<Integer> getMigotoSireLine() {
        return new ArrayList<Integer>(migoto_);
    }

    public Set<Integer> getOmoshiroSireLineSet() {
        return new HashSet<Integer>(omoshiro_);
    }

    public Set<Integer> getMigotoSireLineSet() {
        return new HashSet<Integer>(migoto_);
    }
}
