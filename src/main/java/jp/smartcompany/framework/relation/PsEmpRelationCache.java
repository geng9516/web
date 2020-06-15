package jp.smartcompany.framework.relation;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Xiao Wenpeng
 */
public class PsEmpRelationCache extends LinkedHashMap<String, Integer> {
    private int gnMax = 0;

    public PsEmpRelationCache(int nMax) {
        super(16, 0.75F, true);
        this.gnMax = nMax;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<String,Integer> eldest) {
        return size() > this.gnMax;
    }
}

