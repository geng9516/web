package jp.smartcompany.job.modules.core;

/**
 * @author Xiao Wenpeng
 */
public interface CoreBean {

    interface Manager {
      String ACCESS_AUDIT = "accessAuditManager";
      String OPERATION_AUDIT = "operationAuditManager";
      String LOGIN_AUDIT = "loginAuditManager";
      String ERROR_AUDIT = "errorAuditManager";
    }

    interface Service {
      String LOG = "logService";

    }

    interface Controller {
      String LOG = "logController";
    }

}
