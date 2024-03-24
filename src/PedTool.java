import java.util.HashSet;
import java.util.Set;

import search.*;

public class PedTool {
    public static void main(String[] args) throws Exception {
        BruteForceSearch search = new BruteForceSearch(
            "database/stallions.json",
            "database/elaborated.json",
            "database/default_stallions.json",
            "database/default_broodmares.json"
        );

        
        if (args.length == 2) {
            Set<String> sires = null;
            if (!args[0].equals("all")) {
                sires = new HashSet<String>();
                sires.add(args[0]);
            }

            Set<String> dams = null;
            if (!args[1].equals("all")) {
                dams = new HashSet<String>();
                dams.add(args[1]);
            }

            search.oneGenerationSearch(sires, dams);
        } else if (args.length == 3) {
            Set<String> sires = null;
            if (!args[0].equals("all")) {
                sires = new HashSet<String>();
                sires.add(args[0]);
            }

            Set<String> sires2 = null;
            if (!args[1].equals("all")) {
                sires2 = new HashSet<String>();
                sires2.add(args[1]);
            }

            Set<String> dams = null;
            if (!args[2].equals("all")) {
                dams = new HashSet<String>();
                dams.add(args[2]);
            }
            search.twoGenerationSearch(sires, sires2, dams);
        }
    }
}
