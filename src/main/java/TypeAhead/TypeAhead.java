package TypeAhead;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

public class TypeAhead {

    public static String run(String line){
        Map<String, Double> probability = new LinkedHashMap<>();
        String search = findSearch(line);

        findAndCountNext(probability, search, numOfWordsToPredict(line));
        calculateProbability(probability, countTotal(probability));

        return printResults(sortByValue(probability));
    }

    private static Integer numOfWordsToPredict(String input) {
        String[] array = input.split(", ");

        Integer gramLength = Integer.valueOf(array[0]);

        return gramLength - (array[1].split(" ").length);
    }

    private static String findSearch(String input) {
        String search = input.split(", ")[1].toLowerCase();

        if (Arrays.asList(maryText()).contains(search)) {
            return search;
        } else {
            throw new InvalidSearchTermException();
        }
    }

    private static String[] maryText() {
        String text = "Mary had a little lamb its fleece was white as snow; " +
                "And everywhere that Mary went, the lamb was sure to go. " +
                "It followed her to school one day, which was against the rule; " +
                "It made the children laugh and play, to see a lamb at school. " +
                "And so the teacher turned it out, but still it lingered near, " +
                "And waited patiently about till Mary did appear. " +
                "\"Why does the lamb love Mary so?\" the eager children cry; " +
                "\"Why, Mary loves the lamb, you know\" the teacher did reply.";

        text = text.replaceAll("[^a-zA-Z ]", "").toLowerCase();

        return text.split(" ");
    }

    private static void findAndCountNext(Map<String, Double> input, String search, Integer numOfWordsToPredict) {
        String[] text = maryText();

        for (int i = 0; i < text.length; i++) {
            if (text[i].equals(search)) {
                String next = "";

                for (int j = 1; j <= numOfWordsToPredict; j++) {
                    next += text[i + j] + " ";
                }

                if (!input.keySet().contains(next.trim())) {
                    input.put(next.trim(), 1.0);
                } else {
                    input.put(next.trim(), input.get(next.trim()) + 1.0);
                }
            }
        }
    }

    private static Double countTotal(Map<String, Double> input) {
        Double totalCount = 0.0;

        for (String string : input.keySet()) {
            totalCount += input.get(string);
        }

        return totalCount;
    }

    private static void calculateProbability(Map<String, Double> input, Double totalCount) {
        for (String key : input.keySet()) {
            input.put(key, (input.get(key) / totalCount));
        }
    }

    private static Map<String, Double> sortByValue(Map<String, Double> input) {
        Map<String, Double> sortedMap = new LinkedHashMap<>();

        input.entrySet().stream()
                .sorted(Map.Entry.<String, Double> comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry::getKey, String.CASE_INSENSITIVE_ORDER))
                .forEachOrdered(e -> sortedMap.put(e.getKey(), e.getValue()));

        return sortedMap;
    }

    private static String printResults(Map<String, Double> input) {
        String result = "";

        for (String string : input.keySet()) {
            result += String.format(string + ",%.3f;", input.get(string));
        }

        return result.substring(0, result.length() - 1);
    }
}

//Mary had a little lamb its fleece was white as snow;
//And everywhere that Mary went, the lamb was sure to go.
//It followed her to school one day, which was against the rule;
//It made the children laugh and play, to see a lamb at school.
//And so the teacher turned it out, but still it lingered near,
//And waited patiently about till Mary did appear.
//"Why does the lamb love Mary so?" the eager children cry;
//"Why, Mary loves the lamb, you know" the teacher did reply."
