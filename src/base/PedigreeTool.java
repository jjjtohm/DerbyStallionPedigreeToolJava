package base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PedigreeTool {
    /**
     * 自家製種牡馬の作成
     * 
     * @param sire 父
     * @param dam 母
     * @param name 自家製種牡馬の馬名
     * @return 自家製種牡馬
     * @throws IllegalArgumentException
     * @throws NullPointerException
     */
    public static BreedableStallion giveBirthToStallion(
        BreedableStallion sire, BreedableBroodmare dam, String name
    ) throws IllegalArgumentException, NullPointerException {
        Ancestors damAncestors = dam.getAncestors();
        Ancestors ancestors = PropertyTool.getAncestors(
            sire.getName(),
            damAncestors.getSire1(),
            damAncestors.getSire2(),
            damAncestors.getSire3()
        );
        ArrayList<Integer> omoshiro = new ArrayList<Integer>(4);
        omoshiro.add(sire.getOmoshiroSireLine(0));
        omoshiro.add(sire.getOmoshiroSireLine(2));
        omoshiro.add(dam.getOmoshiroSireLine(0));
        omoshiro.add(dam.getOmoshiroSireLine(2));

        ArrayList<Integer> migoto = new ArrayList<Integer>(4);
        migoto.add(sire.getOmoshiroSireLine(1));
        migoto.add(sire.getOmoshiroSireLine(3));
        omoshiro.add(dam.getOmoshiroSireLine(1));
        omoshiro.add(dam.getOmoshiroSireLine(3));

        return new BreedableStallion(name, ancestors, omoshiro, migoto);
    }

    /**
     * 自家製繁殖牝馬の作成
     * 
     * @param sire 父
     * @param dam 母
     * @param name 自家製繁殖牝馬の名前
     * @return 自家製繁殖牝馬
     * @throws IllegalArgumentException
     * @throws NullPointerException
     */
    public static BreedableBroodmare giveBirthToBroodmare(
        BreedableStallion sire, BreedableBroodmare dam, String name
    ) throws IllegalArgumentException, NullPointerException {
        Ancestors damAncestors = dam.getAncestors();
        Ancestors ancestors = PropertyTool.getAncestors(
            sire.getName(),
            damAncestors.getSire1(),
            damAncestors.getSire2(),
            damAncestors.getSire3()
        );
        ArrayList<Integer> omoshiro = new ArrayList<Integer>(4);
        omoshiro.add(sire.getOmoshiroSireLine(0));
        omoshiro.add(sire.getOmoshiroSireLine(2));
        omoshiro.add(dam.getOmoshiroSireLine(0));
        omoshiro.add(dam.getOmoshiroSireLine(2));

        return new BreedableBroodmare(name, ancestors, omoshiro);
    }

    /**
     * 馬名のリストを得る。
     * @param horse
     * @param stallionSet
     * @param includeMyself 自分自身をリストに含める
     * @return
     * @throws IllegalArgumentException
     * @throws NullPointerException
     */
    public static List<String> expandAncestors(
        Thoroughbred horse, Map<String, Stallion> stallionSet, boolean includeMyself
    ) throws IllegalArgumentException, NullPointerException {
        int size = includeMyself ? 16 : 15;
        ArrayList<String> ancestors = new ArrayList<String>(size);

        String s    = horse.getAncestors().getSire1();                // 父
        String ds   = horse.getAncestors().getSire2();                // 母父
        String dds  = horse.getAncestors().getSire3();                // 母母父
        String ddds = horse.getAncestors().getSire4();                // 母母母父
        String ss   = stallionSet.get(s).getAncestors().getSire1();   // 父父
        String sds  = stallionSet.get(s).getAncestors().getSire2();   // 父母父
        String sdds = stallionSet.get(s).getAncestors().getSire3();   // 父母母父
        String sss  = stallionSet.get(ss).getAncestors().getSire1();  // 父父父
        String ssds = stallionSet.get(ss).getAncestors().getSire2();  // 父父母父
        String ssss = stallionSet.get(sss).getAncestors().getSire1(); // 父父父父
        String sdss = stallionSet.get(sds).getAncestors().getSire1(); // 父母父父
        String dss  = stallionSet.get(ds).getAncestors().getSire1();  // 母父父
        String dsds = stallionSet.get(ds).getAncestors().getSire2();  // 母父母父
        String dsss = stallionSet.get(dss).getAncestors().getSire1(); // 母父父父
        String ddss = stallionSet.get(dds).getAncestors().getSire1(); // 母母父父

        if (includeMyself) {
            ancestors.add(horse.getName());
        }
        ancestors.add(s);
        ancestors.add(ss);
        ancestors.add(ds);
        ancestors.add(sss);
        ancestors.add(sds);
        ancestors.add(dss);
        ancestors.add(dds);
        ancestors.add(ssss);
        ancestors.add(ssds);
        ancestors.add(sdss);
        ancestors.add(sdds);
        ancestors.add(dsss);
        ancestors.add(dsds);
        ancestors.add(ddss);
        ancestors.add(ddds);

        return ancestors;
    }

    HashSet<Integer> sireCrossSkip      = new HashSet<Integer>();
    HashMap<Integer, Integer> crossSkip = new HashMap<Integer, Integer>();
    private static void addSkipCrossIndex(
        int sireX, int damX, HashSet<Integer> sireCrossSkip, HashMap<Integer, Set<Integer>> crossSkip
    ) {
        sireCrossSkip.add(sireX);
        if (crossSkip.containsKey(sireX)) {
            crossSkip.get(sireX).add(damX);
        } else {
            HashSet<Integer> tmp = new HashSet<Integer>();
            tmp.add(damX);
            crossSkip.put(sireX, tmp);
        }
        
        ArrayList<Integer> sireFollowings = new ArrayList<Integer>(8);
        if (sireX == 0) {
            sireFollowings.add(1);
            sireFollowings.add(2);
            sireFollowings.add(4);
            sireFollowings.add(5);
            sireFollowings.add(8);
            sireFollowings.add(9);
            sireFollowings.add(10);
            sireFollowings.add(11);
        } else if (sireX == 1) {
            sireFollowings.add(2);
            sireFollowings.add(4);
            sireFollowings.add(5);
            sireFollowings.add(8);
            sireFollowings.add(9);
            sireFollowings.add(10);
            sireFollowings.add(11);
        } else if (sireX == 2) {
            sireFollowings.add(4);
            sireFollowings.add(8);
            sireFollowings.add(9);
        } else if (sireX == 3) {
            sireFollowings.add(6);
            sireFollowings.add(12);
            sireFollowings.add(13);
        } else if (sireX == 4) {
            sireFollowings.add(8);
        } else if (sireX == 5) {
            sireFollowings.add(10);
        } else if (sireX == 6) {
            sireFollowings.add(12);
        } else if (sireX == 7) {
            sireFollowings.add(14);
        }

        ArrayList<Integer> damFollowings = new ArrayList<Integer>(7);
        if (damX == 0) {
            damFollowings.add(1);
            damFollowings.add(3);
            damFollowings.add(4);
            damFollowings.add(7);
            damFollowings.add(8);
            damFollowings.add(9);
            damFollowings.add(10);
        } else if (damX == 1) {
            damFollowings.add(3);
            damFollowings.add(7);
            damFollowings.add(8);
        } else if (damX == 2) {
            damFollowings.add(5);
            damFollowings.add(11);
            damFollowings.add(12);
        } else if (damX == 3) {
            damFollowings.add(7);
        } else if (damX == 4) {
            damFollowings.add(9);
        } else if (damX == 5) {
            damFollowings.add(11);
        } else if (damX == 6) {
            damFollowings.add(13);
        }

        for (int i = 0; i < Math.min(sireFollowings.size(), damFollowings.size()); i++) {
            int nextSireIndex = sireFollowings.get(i);
            int nextDamIndex  = damFollowings.get(i);
            sireCrossSkip.add(sireFollowings.get(i));
            if (crossSkip.containsKey(nextSireIndex)) {
                crossSkip.get(nextSireIndex).add(nextDamIndex);
            } else {
                HashSet<Integer> tmp = new HashSet<Integer>();
                tmp.add(nextDamIndex);
                crossSkip.put(nextSireIndex, tmp);
            }
        }
    }

    private static boolean isCrossSkip(
        int sireX, int damX, Set<Integer> sireCrossSkip, Map<Integer, Set<Integer>> crossSkip
    ) {
        if (sireCrossSkip.contains(sireX)) {
            if (crossSkip.get(sireX).contains(damX)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 短距離 speed +2 stamina -1
     * 速力 speed +1
     * 長距離 stamina +2
     * 底力 stamina +1
     * パワー power +1
     * @param result
     * @param effectSet
     */
    private static void addNitro(AnalyzeResult result, EffectSet effectSet) {
        Iterator<Effect> effectIt = effectSet.iterator();
        while (effectIt.hasNext()) {
            Effect effect = effectIt.next();
            if (effect == Effect.Sprint) {
                result.addSpeedNitro(2);
                result.addStaminaNitro(-1);
            } else if (effect == Effect.Speed) {
                result.addSpeedNitro(1);
            } else if (effect == Effect.Stayer) {
                result.addStaminaNitro(2);
            } else if (effect == Effect.Spirit) {
                result.addStaminaNitro(1);
            } else if (effect == Effect.Power) {
                result.addPowerNitro(1);
            }
        }
    }

    public static AnalyzeResult analyze(
        BreedableStallion sire, BreedableBroodmare dam, Map<String, Stallion> stallionSet,
        Map<String, Set<String>> sireToDamElaboMap, Map<String, Set<String>> damToSireElaboMap
    ) throws NullPointerException {
        AnalyzeResult result = new AnalyzeResult(sire.getName(), dam.getName());

        Set<Integer> sireOmoshiroSet = sire.getOmoshiroSireLineSet();
        Set<Integer> sireMigotoSet   = sire.getMigotoSireLineSet();
        Set<Integer> damOmoshiroSet  = dam.getOmoshiroSireLineSet();

        // 見事な配合
        if (sireMigotoSet.equals(damOmoshiroSet)) {
            result.setExcellent(true);
        }

        // 面白い配合
        sireOmoshiroSet.addAll(damOmoshiroSet);
        if (sireOmoshiroSet.size() >= 7) {
            result.setInteresting(true);
        }

        List<String> sirePedigree                = expandAncestors(sire, stallionSet, true);
        List<String> damPedigree                 = expandAncestors(dam, stallionSet, false);
        HashSet<Integer> sireCrossSkip           = new HashSet<Integer>();
        HashMap<Integer, Set<Integer>> crossSkip = new HashMap<Integer, Set<Integer>>();
        HashSet<String> nitroSet                 = new HashSet<String>();
        HashSet<String> sireToDamElaboSet        = new HashSet<String>();
        HashSet<String> damToSireElaboSet        = new HashSet<String>();
        int numCross                             = 0;
        boolean isElaboration                    = false;
        for (int sireX = 0; sireX < 16; sireX++) {
            String sireStallion = sirePedigree.get(sireX);

            // 凝った配合
            if (
                !isElaboration && sireX <= 7
                && damToSireElaboSet.contains(sireStallion)
            ) {
                System.out.println("sire:" + sireStallion);
                result.setElaboration(true);
                isElaboration = true;
            }
            if (
                !isElaboration && sireX <= 7
                && sireToDamElaboMap.keySet().contains(sireStallion)
            ) {
                sireToDamElaboSet.addAll(sireToDamElaboMap.get(sireStallion));
            }

            // ニトロ数え上げ
            EffectSet effectSet = stallionSet.get(sireStallion).getEffectSet();
            if (effectSet.size() != 0 && !nitroSet.contains(sireStallion)) {
                nitroSet.add(sireStallion);
                addNitro(result, effectSet);
            }

            for (int damX = 0; damX < 15; damX++) {
                String damStallion = damPedigree.get(damX);

                // 凝った配合
                if (
                    !isElaboration && sireX <= 7 && damX <= 6
                    &&sireToDamElaboSet.contains(damStallion)
                ) {
                    System.out.println("dam:" + damStallion);
                    result.setElaboration(true);
                    isElaboration = true;
                }
                if (
                    !isElaboration && damX <= 6
                    && damToSireElaboMap.keySet().contains(damStallion)
                ) {
                    damToSireElaboSet.addAll(damToSireElaboMap.get(damStallion));
                }

                // ニトロ数え上げ
                effectSet = stallionSet.get(damStallion).getEffectSet();
                if (effectSet.size() != 0 && !nitroSet.contains(damStallion)) {
                    nitroSet.add(damStallion);
                    addNitro(result, effectSet);
                }

                // クロス
                if (
                    !isCrossSkip(sireX, damX, sireCrossSkip, crossSkip)
                    && damStallion.equals(sireStallion)
                ) {
                    // 血量が50%以上になるクロスが存在したら危険な配合
                    if (sireX == 0 || (sireX == 1 && damX == 1)) {
                        result.setDanger(true);
                    }
                    addSkipCrossIndex(sireX, damX, sireCrossSkip, crossSkip);
                    if (effectSet.size() != 0) {
                        numCross += 1;
                        Iterator<Effect> effectIt = effectSet.iterator();
                        while (effectIt.hasNext()) {
                            Effect effect = effectIt.next();
                            if (effect == Effect.Sprint) {
                                result.incrementSprint();
                            } else if (effect == Effect.Speed) {
                                result.incrementSpeed();
                            } else if (effect == Effect.Power) {
                                result.incrementPower();
                            } else if (effect == Effect.Spirit) {
                                result.incrementSpirit();
                            } else if (effect == Effect.Stayer) {
                                result.incrementStayer();
                            } else if (effect == Effect.Dirt) {
                                result.incrementDirt();
                            } else if (effect == Effect.Health) {
                                result.incrementHealth();
                            } else if (effect == Effect.Precocious) {
                                result.incrementPrecociout();
                            } else if (effect == Effect.Altricity) {
                                result.incrementAltricity();
                            } else if (effect == Effect.Stability) {
                                result.incrementStability();
                            } else if (effect == Effect.HotTemper) {
                                result.incrementHotTemper();
                            }
                        }
                    }
                }
            } // damX
        } // sireX

        // クロスが7本以上なら危険な配合
        if (numCross >= 7) {
            result.setDanger(true);
        }

        return result;
    }
}
