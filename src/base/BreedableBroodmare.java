package base;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 種付け可能な繁殖牝馬
 */
public class BreedableBroodmare extends Thoroughbred {
    private final ArrayList<Integer> omoshiro_;

    public BreedableBroodmare(
        String name, Ancestors ancestors, List<Integer> omoshiro
    ) throws IllegalArgumentException, NullPointerException {
        super(name, ancestors);
        if (omoshiro.size() != 4) {
            throw new IllegalArgumentException("omoshiro list must has 4 elements.");
        }

        omoshiro_ = new ArrayList<Integer>(omoshiro);
    }

    public int getOmoshiroSireLine(int index) throws ArrayIndexOutOfBoundsException {
        if (index < 0 || index > 3) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return omoshiro_.get(index);
    }

    public List<Integer> getOmoshiroSireLine() {
        return new ArrayList<Integer>(omoshiro_);
    }

    public Set<Integer> getOmoshiroSireLineSet() {
        return new HashSet<Integer>(omoshiro_);
    }
}
