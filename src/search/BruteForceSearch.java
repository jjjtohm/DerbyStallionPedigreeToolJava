package search;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;

import base.*;

public class BruteForceSearch {
    private final Map<String, Stallion> stallionMap_;
    private final Map<String, Set<String>> sireToDamElaboMap_;
    private final Map<String, Set<String>> damToSireElaboMap_;
    private final Map<String, DefaultStallion> defaultStallions_;
    private final Map<String, DefaultBroodmare> defaultBroodmares_;

    public BruteForceSearch(
        String stallionsFile, String elaborationFile,
        String defaultStallionFile, String defaultBroodmareFile
    ) throws IllegalArgumentException, IOException, JsonProcessingException {
        stallionMap_       = Database.makeStallionSet(stallionsFile);
        sireToDamElaboMap_ = Database.makeElaborationMap(elaborationFile, true);
        damToSireElaboMap_ = Database.makeElaborationMap(elaborationFile, false);
        defaultStallions_  = Database.makeDefaltStallionMap(defaultStallionFile);
        defaultBroodmares_ = Database.makeDefaultBroodmareMap(defaultBroodmareFile);
    }

    public void oneGenerationSearch(
        Set<String> sires, Set<String> dams
    ) throws Exception {
        Iterator<String> damIt;
        if (Objects.isNull(dams)) {
            damIt = defaultBroodmares_.keySet().iterator();
        } else {
            damIt = dams.iterator();
        }

        System.out.println(AnalyzeResult.getCsvColumn());
        while (damIt.hasNext()) {
            DefaultBroodmare dam = defaultBroodmares_.get(damIt.next());
            Iterator<String> sireIt;
            if (Objects.isNull(sires)) {
                sireIt = defaultStallions_.keySet().iterator();
            } else {
                sireIt = sires.iterator();
            }
            while (sireIt.hasNext()) {
                DefaultStallion sire = defaultStallions_.get(sireIt.next());
                AnalyzeResult result = PedigreeTool.analyze(
                    sire, dam, stallionMap_, sireToDamElaboMap_, damToSireElaboMap_ 
                );
                System.out.println(result.toCsvFormat());
            }
        }
    }

    public void twoGenerationSearch(
        Set<String> sires, Set<String> sires2, Set<String> dams
    ) throws Exception {
        Iterator<String> damIt;
        if (Objects.isNull(dams)) {
            damIt = defaultBroodmares_.keySet().iterator();
        } else {
            damIt = dams.iterator();
        }

        System.out.println(AnalyzeResult.getCsvColumn());
        while (damIt.hasNext()) {
            DefaultBroodmare dam = defaultBroodmares_.get(damIt.next());
            Iterator<String> sire2It;
            if (Objects.isNull(sires2)) {
                sire2It = defaultStallions_.keySet().iterator();
            } else {
                sire2It = sires2.iterator();
            }
            while (sire2It.hasNext()) {
                String sire2Name = sire2It.next();
                BreedableBroodmare genOneBroodmare = PedigreeTool.giveBirthToBroodmare(
                    defaultStallions_.get(sire2Name),
                    dam,
                    sire2Name + " x " + dam.getName()
                );

                Iterator<String> sireIt;
                if (Objects.isNull(sires)) {
                    sireIt = defaultStallions_.keySet().iterator();
                } else {
                    sireIt = sires.iterator();
                }
                while (sireIt.hasNext()) {
                    DefaultStallion sire = defaultStallions_.get(sireIt.next());
                    AnalyzeResult result = PedigreeTool.analyze(
                        sire, genOneBroodmare, stallionMap_, sireToDamElaboMap_, damToSireElaboMap_ 
                    );
                    System.out.println(result.toCsvFormat());
                }
            }
        }
    }
}
