package jp.smartcompany.framework.relation;

import jp.smartcompany.job.modules.core.util.Designation;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Xiao Wenpeng
 */
public class PsDesignationCache extends LinkedHashMap<String, List<Designation>> {

    private static final long serialVersionUID = 1L;
    private int gnMax = 0;
    public PsDesignationCache(int nMax) {
        super(16, 0.75F, true);
        this.gnMax = nMax;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<String, List<Designation>> eldest) {
        return size() > this.gnMax;
    }

}
