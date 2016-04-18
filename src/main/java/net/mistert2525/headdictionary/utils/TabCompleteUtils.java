package net.mistert2525.headdictionary.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author misterT2525
 */
public final class TabCompleteUtils {

    private TabCompleteUtils() {
        throw new RuntimeException("Non instantiable class");
    }

    public static List<String> complete(String base, Collection<String> completions) {
        List<String> list = new ArrayList<>();
        base = base.toLowerCase();

        for (String completion : completions) {
            if (completion.toLowerCase().startsWith(base)) {
                list.add(completion);
            }
        }

        return list;
    }
}
