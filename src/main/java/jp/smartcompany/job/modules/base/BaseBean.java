package jp.smartcompany.job.modules.base;

/**
 * @author Xiao Wenpeng
 */
public interface BaseBean {

    public interface Controller {

        String PLUGGABLE = "pluggableController";

    }

    public interface Service {
        String PLUGGABLE = "pluggableService";
    }

}
