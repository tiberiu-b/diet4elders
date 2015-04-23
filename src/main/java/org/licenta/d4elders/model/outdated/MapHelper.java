package org.licenta.d4elders.model.outdated;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cristiprg on 02.03.2015.
 */
public class MapHelper {
    public static Map<String, Double> addNutrientsMaps(Map<String, Double> map1, Map<String, Double> map2)
    {
        Map<String, Double> resultMap = new HashMap<String, Double>();
        for(String key : map1.keySet())
        {
            if (!map2.containsKey(key))
            {
                System.err.println("ERROR: nutrients inconsistency!");
                System.exit(1);
            }

            Double result = map1.get(key) + map2.get(key);
            resultMap.put(key, result);
        }

        return resultMap;
    }

    public static Map<String, Double> addNutrientsMaps(Map<String, Double> map1,
                                                          Map<String, Double> map2,
                                                          Map<String, Double> map3)
    {
        return addNutrientsMaps( addNutrientsMaps(map1, map2), map3 );
    }

    protected static Map<String, Double> addNutrientsMaps(Map<String, Double> map1,
                                                          Map<String, Double> map2,
                                                          Map<String, Double> map3,
                                                          Map<String, Double> map4)
    {
        return addNutrientsMaps( addNutrientsMaps(map1, map2, map3 ), map4);
    }

    public static Map<String, Double> addNutrientsMaps(Map<String, Double> map1,
                                                          Map<String, Double> map2,
                                                          Map<String, Double> map3,
                                                          Map<String, Double> map4,
                                                          Map<String, Double> map5)
    {
        return addNutrientsMaps( addNutrientsMaps(map1, map2, map3, map4), map5);
    }

    public static String mapToString(Map<String, Double> map)
    {
        /*return "energyKcal = " + energyKcal + "\n" +
                "fatG = " + fatG + "\n" +
                "carbG = " + carbG + "\n" +
                "satFatAcidG = " + satFatAcidG + "\n" +
                "transFatAcidG = " + transFatAcidG + "\n" +
                "proteinG = " + proteinG + "\n" +
                "potassiumG = " + potassiumG + "\n" +
                "calciumG = " + calciumG + "\n" +
                "sodiumG = " + sodiumG + "\n" +
                "vitaminDug = " + vitaminDUg + "\n" +
                "alcoholG = " + alcoholG + "\n" +
                "waterG = " + waterG + "\n";*/
        String s = "";
        for(Map.Entry<String, Double> nutritent : map.entrySet())
        {
            s += nutritent.getKey() + " = " + nutritent.getValue() + "\n";
        }
        return s;
    }
}
