package base;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

enum Blood {
    None(0),
    Ec(1),
    Ph(2),
    Ns(3),
    Ro(4),
    Ne(5),
    Na(6),
    Fa(7),
    To(8),
    Te(9),
    Sw(10),
    Ha(11),
    Hi(12),
    St(13),
    Ma(14),
    He(15);

    private final int bloodId_;
    private final String bloodName_;

    private Blood(int bloodId) {
        bloodId_ = bloodId;
        switch (bloodId_) {
            case 1:
                bloodName_ = "Ec";
                break;
            case 2:
                bloodName_ = "Ph";
                break;
            case 3:
                bloodName_ = "Ns";
                break;
            case 4:
                bloodName_ = "Ro";
                break;
            case 5:
                bloodName_ = "Ne";
                break;
            case 6:
                bloodName_ = "Na";
                break;
            case 7:
                bloodName_ = "Fa";
                break;
            case 8:
                bloodName_ = "To";
                break;
            case 9:
                bloodName_ = "Te";
                break;
            case 10:
                bloodName_ = "Sw";
                break;
            case 11:
                bloodName_ = "Ha";
                break;
            case 12:
                bloodName_ = "Hi";
                break;
            case 13:
                bloodName_ = "St";
                break;
            case 14:
                bloodName_ = "Ma";
                break;
            case 15:
                bloodName_ = "He";
                break;
            default:
                bloodName_ = "";
        }
    }

    public int getBloodId() {
        return bloodId_;
    }

    public String getBloodName() {
        return bloodName_;
    }
}

enum Effect {
    Sprint("短距離"),
    Speed("速力"),
    Power("パワー"),
    Spirit("底力"),
    Stayer("長距離"),
    Dirt("ダート"),
    Health("丈夫"),
    Precocious("早熟"),
    Altricity("晩成"),
    Stability("堅実"),
    HotTemper("気性難");

    private final String effect_;

    private Effect(String effect) {
        effect_ = effect;
    }

    public String getEffectName() {
        return effect_;
    }
}

class EffectSet {
    private HashSet<Effect> effectSet_;

    EffectSet() {
        effectSet_ = new HashSet<Effect>();
    }

    EffectSet(Collection<Effect> effects) {
        effectSet_ = new HashSet<Effect>();
        Iterator<Effect> it = effects.iterator();
        while (it.hasNext()) {
            effectSet_.add(it.next());
        }
    }

    public int size() {
        return effectSet_.size();
    }

    public boolean has(Effect effect) {
        return effectSet_.contains(effect);
    }

    public void add(Effect effect) {
        effectSet_.add(effect);
    }

    public Iterator<Effect> iterator() {
        return effectSet_.iterator();
    }
}

enum Grade {
    A("A"),
    B("B"),
    C("C");

    private final String grade_;

    private Grade(String grade) {
        grade_ = grade;
    }

    public String toString() {
        return grade_;
    }
}

enum Dirt {
    A("◎"),
    B("○"),
    C("△"),
    Unknown("?");

    private final String dirt_;

    private Dirt(String dirt) {
        dirt_ = dirt;
    }

    public String toString() {
        return dirt_;
    }
}

enum Growth {
    Precocious("早熟"),
    Normal("普通"),
    Persistence("持続"),
    Altricity("晩成");

    private final String growth_;

    private Growth(String growth) {
        growth_ = growth;
    }

    public String toString() {
        return growth_;
    }
}

class Ancestors {
    private final String name1_; // 父
    private final String name2_; // 母父
    private final String name3_; // 母母父
    private final String name4_; // 母母母父

    Ancestors(
        String name1, String name2, String name3, String name4
    ) throws NullPointerException {
        name1_ = Objects.requireNonNull(name1);
        name2_ = Objects.requireNonNull(name2);
        name3_ = Objects.requireNonNull(name3);
        name4_ = Objects.requireNonNull(name4);
    }

    Ancestors(String[] names) throws IllegalArgumentException, NullPointerException {
        if (names.length != 4) {
            throw new IllegalArgumentException("length of input array must be 4.");
        }
        if (
            Objects.isNull(names[0])
            || Objects.isNull(names[1])
            || Objects.isNull(names[2])
            || Objects.isNull(names[3])
        ) {
            throw new NullPointerException("Null String is not allowed.");
        }
        name1_ = names[0];
        name2_ = names[1];
        name3_ = names[2];
        name4_ = names[3];
    }

    Ancestors(List<String> names) throws IllegalArgumentException, NullPointerException {
        if (names.size() != 4) {
            throw new IllegalArgumentException("length of input list must be 4.");
        }
        if (
            Objects.isNull(names.get(0))
            || Objects.isNull(names.get(1))
            || Objects.isNull(names.get(2))
            || Objects.isNull(names.get(3))
        ) {
            throw new NullPointerException("Null String is not allowed.");
        }
        name1_ = names.get(0);
        name2_ = names.get(1);
        name3_ = names.get(2);
        name4_ = names.get(3);
    }

    public String getSire1() {
        return name1_;
    }

    public String getSire2() {
        return name2_;
    }

    public String getSire3() {
        return name3_;
    }

    public String getSire4() {
        return name4_;
    }
}

public class PropertyTool {
    public static Blood getBlood(int bloodId) throws IllegalArgumentException {
        switch (bloodId) {
            case 0:
                return Blood.None;
            case 1:
                return Blood.Ec;
            case 2:
                return Blood.Ph;
            case 3:
                return Blood.Ns;
            case 4:
                return Blood.Ro;
            case 5:
                return Blood.Ne;
            case 6:
                return Blood.Na;
            case 7:
                return Blood.Fa;
            case 8:
                return Blood.To;
            case 9:
                return Blood.Te;
            case 10:
                return Blood.Sw;
            case 11:
                return Blood.Ha;
            case 12:
                return Blood.Hi;
            case 13:
                return Blood.St;
            case 14:
                return Blood.Ma;
            case 15:
                return Blood.He;
            default:
                throw new IllegalArgumentException("bloodId must be between 0 and 15.");
        }
    }

    public static Effect getEffect(String effect) throws IllegalArgumentException {
        switch (effect) {
            case "短距離":
                return Effect.Sprint;
            case "速力":
                return Effect.Speed;
            case "パワー":
                return Effect.Power;
            case "底力":
                return Effect.Spirit;
            case "長距離":
                return Effect.Stayer;
            case "ダート":
                return Effect.Dirt;
            case "丈夫":
                return Effect.Health;
            case "早熟":
                return Effect.Precocious;
            case "晩成":
                return Effect.Altricity;
            case "堅実":
                return Effect.Stability;
            case "気性難":
                return Effect.HotTemper;
            default:
                throw new IllegalArgumentException(effect + " is unknown effect.");
        }
    }

    public static EffectSet getEffectSet(Collection<String> effects) throws IllegalArgumentException {
        if (Objects.isNull(effects)) {
            throw new IllegalArgumentException();
        }

        EffectSet effectSet = new EffectSet();
        Iterator<String> it = effects.iterator();
        while (it.hasNext()) {
            effectSet.add(getEffect(it.next()));
        }
        return effectSet;
    }

    public static Ancestors getAncestors(
        String name1, String name2, String name3, String name4
    ) throws NullPointerException {
        return new Ancestors(name1, name2, name3, name4);
    }

    public static Ancestors getAncestors(
        List<String> ancestors
    ) throws IllegalArgumentException, NullPointerException {
        return new Ancestors(ancestors);
    }

    public static Dirt getDirt(String dirt) throws IllegalArgumentException, NullPointerException {
        if (Objects.isNull(dirt)) {
            throw new NullPointerException();
        }

        if (dirt.equals("◎")) {
            return Dirt.A;
        } else if (dirt.equals("○")) {
            return Dirt.B;
        } else if (dirt.equals("△")) {
            return Dirt.C;
        } else if (dirt.equals("?")) {
            return Dirt.Unknown;
        } else {
            throw new IllegalArgumentException(dirt + " is unknown dirt property.");
        }
    }

    public static Grade getGrade(String grade) throws IllegalArgumentException, NullPointerException {
        if (Objects.isNull(grade)) {
            throw new NullPointerException();
        }

        if (grade.equals("A")) {
            return Grade.A;
        } else if (grade.equals("B")) {
            return Grade.B;
        } else if (grade.equals("C")) {
            return Grade.C;
        } else {
            throw new IllegalArgumentException(grade + " is unknown grade property.");
        }
    }

    public static Growth getGrowth(String growth) throws IllegalArgumentException, NullPointerException {
        if (Objects.isNull(growth)) {
            throw new NullPointerException();
        }

        if (growth.equals("早熟")) {
            return Growth.Precocious;
        } else if (growth.equals("普通")) {
            return Growth.Normal;
        } else if (growth.equals("持続")) {
            return Growth.Persistence;
        } else if (growth.equals("晩成")) {
            return Growth.Altricity;
        } else {
            throw new IllegalArgumentException(growth + " is unknown growth property.");
        }
    }
}
