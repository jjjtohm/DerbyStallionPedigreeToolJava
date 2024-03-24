package base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Database {
    /**
     * stallionデータベースからMapを作成する。
     * 
     * @param jsonFileName stallionデータベースのファイル名
     * @return stallion名をkey, StallionをvalueとするMap
     */
    public static Map<String, Stallion> makeStallionSet(
        String jsonFileName
    ) throws IllegalArgumentException, IOException, JsonProcessingException {
        Map<String, Stallion> stallionMap = new HashMap<String, Stallion>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree(Paths.get(jsonFileName).toFile());
        for (int stallionX=0; stallionX < json.size(); stallionX++) {
            JsonNode info = json.get(stallionX);
            String name = info.get("name").asText();
            Ancestors ancestors = PropertyTool.getAncestors(
                info.get("ancestors").get(0).asText(),
                info.get("ancestors").get(1).asText(),
                info.get("ancestors").get(2).asText(),
                info.get("ancestors").get(3).asText()
            );
            Blood blood = PropertyTool.getBlood(info.get("blood").asInt());
            List<String> effectList = new ArrayList<String>();
            for (int i=0; i < info.get("effects").size(); i++) {
                effectList.add(info.get("effects").get(i).asText());
            }
            EffectSet effectSet = PropertyTool.getEffectSet(effectList);
            stallionMap.put(name, new Stallion(name, ancestors, blood, effectSet));
        }

        return stallionMap;
    }

    /**
     * 凝った配合になるペアを作成する。
     * 
     * @param elaboratedFileName
     * @param sireToDam trueならkeyが父方に出現する種牡馬名、valueが対応する種牡馬名の集合。falseならkeyが母方に出現する種牡馬名。
     * @return
     * @throws IllegalArgumentException
     * @throws IOException
     * @throws JsonProcessingException
     */
    public static Map<String, Set<String>> makeElaborationMap(
        String elaboratedFileName, boolean sireToDam
    ) throws IllegalArgumentException, IOException, JsonProcessingException {
        HashMap<String, Set<String>> elaborationMap = new HashMap<String, Set<String>>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree(Paths.get(elaboratedFileName).toFile());
        for (int i = 0; i < json.size(); i++) {
            JsonNode info = json.get(i);
            if (info.size() != 2) {
                throw new IOException(elaboratedFileName + " is invalid format.");
            }

            String key;
            String value;
            if (sireToDam) {
                key = info.get(0).asText();
                value = info.get(1).asText();
            } else {
                key = info.get(1).asText();
                value = info.get(0).asText();
            }

            if (elaborationMap.containsKey(key)) {
                Set<String> elaboSet = elaborationMap.get(key);
                elaboSet.add(value);
            } else {
                Set<String> elaboSet = new HashSet<String>();
                elaboSet.add(value);
                elaborationMap.put(key, elaboSet);
            }
        }

        return elaborationMap;
    }

    /**
     * デフォルト種牡馬のマップを作成する。
     * 
     * @param defaultStallionFile
     * @return
     * @throws IllegalArgumentException
     * @throws IOException
     * @throws JsonProcessingException
     */
    public static Map<String, DefaultStallion> makeDefaltStallionMap(
        String defaultStallionFile
    ) throws IllegalArgumentException, IOException, JsonProcessingException {
        HashMap<String, DefaultStallion> defaultStallions = new HashMap<String, DefaultStallion>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree(Paths.get(defaultStallionFile).toFile());

        for (int stallionX=0; stallionX < json.size(); stallionX++) {
            JsonNode info     = json.get(stallionX);
            String name       = info.get("name").asText();
            String system     = info.get("system").asText();
            int fee           = info.get("fee").asInt();
            int min           = info.get("min").asInt();
            int max           = info.get("max").asInt();
            Dirt dirt         = PropertyTool.getDirt(info.get("dirt").asText());
            Growth growth     = PropertyTool.getGrowth(info.get("growth").asText());
            Grade health      = PropertyTool.getGrade(info.get("health").asText());
            Grade temper      = PropertyTool.getGrade(info.get("temper").asText());
            Grade achievement = PropertyTool.getGrade(info.get("achievement").asText());
            Grade spirit      = PropertyTool.getGrade(info.get("spirit").asText());
            Grade stable      = PropertyTool.getGrade(info.get("stable").asText());
            Ancestors ancestors = PropertyTool.getAncestors(
                info.get("ancestors").get(0).asText(),
                info.get("ancestors").get(1).asText(),
                info.get("ancestors").get(2).asText(),
                info.get("ancestors").get(3).asText()
            );
            ArrayList<Integer> omoshiro = new ArrayList<Integer>(4);
            omoshiro.add(info.get("indices").get(0).asInt());
            omoshiro.add(info.get("indices").get(2).asInt());
            omoshiro.add(info.get("indices").get(4).asInt());
            omoshiro.add(info.get("indices").get(6).asInt());
            ArrayList<Integer> migoto = new ArrayList<Integer>(4);
            migoto.add(info.get("indices").get(1).asInt());
            migoto.add(info.get("indices").get(3).asInt());
            migoto.add(info.get("indices").get(5).asInt());
            migoto.add(info.get("indices").get(7).asInt());
            defaultStallions.put(
                name,
                new DefaultStallion(
                    name, system, fee, min, max, dirt, growth, health, temper,
                    achievement, spirit, stable, ancestors, omoshiro, migoto
                )
            );
        }

        return defaultStallions;
    }

    /**
     * デフォルト繁殖牝馬のマップを作成する
     * 
     * @param defaultBroodmareFile
     * @return
     * @throws IllegalArgumentException
     * @throws IOException
     * @throws JsonProcessingException
     */
    public static Map<String, DefaultBroodmare> makeDefaultBroodmareMap(
        String defaultBroodmareFile
    ) throws IllegalArgumentException, IOException, JsonProcessingException {
        HashMap<String, DefaultBroodmare> defaultBroodmares = new HashMap<String, DefaultBroodmare>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree(Paths.get(defaultBroodmareFile).toFile());

        for (int stallionX=0; stallionX < json.size(); stallionX++) {
            JsonNode info     = json.get(stallionX);
            String name       = info.get("name").asText();
            String system     = info.get("system").asText();
            int fee           = info.get("fee").asInt();
            int speed         = info.get("speed").asInt();
            int stamina       = info.get("stamina").asInt();
            int power         = info.get("power").asInt();
            Dirt dirt         = PropertyTool.getDirt(info.get("dirt").asText());
            Ancestors ancestors = PropertyTool.getAncestors(
                info.get("ancestors").get(0).asText(),
                info.get("ancestors").get(1).asText(),
                info.get("ancestors").get(2).asText(),
                info.get("ancestors").get(3).asText()
            );
            ArrayList<Integer> omoshiro = new ArrayList<Integer>(4);
            omoshiro.add(info.get("indices").get(0).asInt());
            omoshiro.add(info.get("indices").get(1).asInt());
            omoshiro.add(info.get("indices").get(2).asInt());
            omoshiro.add(info.get("indices").get(3).asInt());

            defaultBroodmares.put(
                name,
                new DefaultBroodmare(
                    name, system, fee, speed, stamina, power,
                    dirt, ancestors, omoshiro
                )
            );
        }

        return defaultBroodmares;
    }
}
