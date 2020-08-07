package jp.smartcompany.job.modules.core.util;

import org.springframework.stereotype.Service;

import java.util.Vector;

@Service
public class AjaxBean {

    private boolean isSelect(String sSQL) {
        return sSQL.trim().substring(0, 6).toLowerCase().equals("select");
    }

    public PsResult exeSQLs(Vector<String> vecQuery,PsDBBean psDBBean) {
        PsResult psResult = new PsResult();
        try {
            int SELECT = 0;
            int UPDATE = 1;
            String sSQL = vecQuery.elementAt(0);
            int currentMode;
            if(isSelect(sSQL)) {
                currentMode = SELECT;
            } else {
                currentMode = UPDATE;
            }
            Vector vecSQL = new Vector();
            Vector vecResult = new Vector();
            vecSQL.add(sSQL);
            for(int index = 1; index < vecQuery.size(); index++) {
                sSQL = vecQuery.elementAt(index);
                int mode;
                if(isSelect(sSQL)) {
                    mode = SELECT;
                } else {
                    mode = UPDATE;
                }
                if(mode != currentMode) {
                    if(currentMode == SELECT) {
                        PsResult res = psDBBean.getValuesforMultiquery(vecSQL, "AjaxBean");
                        vecResult.addAll(res.getResult());
                        currentMode = UPDATE;
                    } else {
                        psDBBean.setInsertValues(vecSQL, "AjaxBean");
                        currentMode = SELECT;
                    }
                    vecSQL.clear();
                }
                vecSQL.add(sSQL);
            }

            if(currentMode == SELECT) {
                PsResult res = psDBBean.getValuesforMultiquery(vecSQL, "AjaxBean");
                Vector vec = res.getResult();
                for(int i = 0; i < vec.size(); i++) {
                    vecResult.add(vec.elementAt(i));
                }
            } else {
                psDBBean.setInsertValues(vecSQL, "AjaxBean");
            }
            if(vecResult.size() > 0) {
                psResult.setResult(vecResult);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return psResult;
    }

}
