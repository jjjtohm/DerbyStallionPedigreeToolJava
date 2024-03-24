package base;

import java.util.ArrayList;
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

    private static void addSkipCrossIndex(HashSet<Integer> skipSet, int index) {
        skipSet.add(index);
        switch (index) {
            case 0:
                skipSet.add(1);
                skipSet.add(3);
                skipSet.add(4);
                skipSet.add(7);
                skipSet.add(8);
                skipSet.add(9);
                skipSet.add(10);
                break;
            case 1:
                skipSet.add(3);
                skipSet.add(7);
                skipSet.add(8);
                break;
            case 2:
                skipSet.add(5);
                skipSet.add(11);
                skipSet.add(12);
                break;
            case 3:
                skipSet.add(7);
                break;
            case 4:
                skipSet.add(9);
                break;
            case 5:
                skipSet.add(11);
                break;
            case 6:
                skipSet.add(13);
                break;
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

        List<String> sirePedigree         = expandAncestors(sire, stallionSet, true);
        List<String> damPedigree          = expandAncestors(dam, stallionSet, false);
        HashSet<Integer> sireCrossSkip    = new HashSet<Integer>();
        HashSet<Integer> damCrossSkip     = new HashSet<Integer>();
        HashSet<String> nitroSet          = new HashSet<String>();
        HashSet<String> sireToDamElaboSet = new HashSet<String>();
        HashSet<String> damToSireElaboSet = new HashSet<String>();
        int numCross                      = 0;
        boolean isElaboration             = false;
        for (int sireX = 0; sireX < 16; sireX++) {
            String sireStallion = sirePedigree.get(sireX);

            // 凝った配合
            if (!isElaboration && damToSireElaboSet.contains(sireStallion)) {
                result.setElaboration(true);
                isElaboration = true;
            }
            if (!isElaboration && sireToDamElaboMap.keySet().contains(sireStallion)) {
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
                if (!isElaboration && sireToDamElaboSet.contains(damStallion)) {
                    result.setElaboration(true);
                    isElaboration = true;
                }
                if (!isElaboration && damToSireElaboMap.keySet().contains(damStallion)) {
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
                    !sireCrossSkip.contains(sireX)
                    && !damCrossSkip.contains(damX)
                    && damStallion.equals(sireStallion)
                ) {
                    // 血量が50%以上になるクロスが存在したら危険な配合
                    if (sireX == 0 || (sireX == 1 && damX == 1)) {
                        result.setDanger(true);
                    }
                    addSkipCrossIndex(sireCrossSkip, sireX);
                    addSkipCrossIndex(damCrossSkip, damX);
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
