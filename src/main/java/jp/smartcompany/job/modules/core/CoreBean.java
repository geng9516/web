package jp.smartcompany.job.modules.core;

/**
 * @author Xiao Wenpeng
 */
public interface CoreBean {

  interface Service {
    String ACCESS_AUDIT = "accessAuditService";
    String OPERATION_AUDIT = "operationAuditService";
    String LOGIN_AUDIT = "loginAuditService";
    String ERROR_AUDIT = "errorAuditService";
  }

  interface Business {
    String LOG = "logBusiness";
    String AUTH_BUSINESS = "authBusiness";
  }

  interface Controller {
    String LOG = "logController";
    String INDEX = "indexController";
    String AUTH = "authController";
  }

}