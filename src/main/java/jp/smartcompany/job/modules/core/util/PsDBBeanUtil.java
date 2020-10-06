package jp.smartcompany.job.modules.core.util;

import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.compatible.business.Version3CompatibleLogic;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PsDBBeanUtil {

    private final ScCacheUtil scCacheUtil;
    @Resource
    private  Version3CompatibleLogic version3CompatibleLogic;

    public Version3CompatibleLogic getV3Logic() {
        return version3CompatibleLogic;
    }
    /**
     * システムプロパティ情報を取得します<br>
     * @return プロパティ値
     */
    public String getSystemProperty(String sSysPropertyKey) {
        return scCacheUtil.getSystemProperty(sSysPropertyKey);
    }

    public int setInsertValues(Vector vecQuery, String beandesc,PsDBBean bean) {
        try {
            return version3CompatibleLogic.setInsertValues(vecQuery, bean.getUserCode(),
                    beandesc, bean.getCompCode(), bean.getCustID(), bean.getSystemCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * １つ以上のSELECT文をセキュリティ判定なしで実行します。
     * @param vecQuery	   SELECT文のVector
     * @param strBeanDesc ログ出力用Bean識別子
     * @return PsResult   SQLの実行結果
     * @throws Exception  システム例外
     */
    public PsResult getValuesforMultiquery(Vector vecQuery, String strBeanDesc,PsDBBean bean)
            throws Exception {
        try {
            if (vecQuery != null && strBeanDesc != null) {
                return version3CompatibleLogic.executeMultiQuery(
                        vecQuery, bean.getCustID(),
                        bean.getCompCode(), bean.getUserCode(),
                        bean.getGroupID(), 	strBeanDesc,
                        bean.getSystemCode(), bean.getStrGUID(),
                        false);
            } else {
                throw new Exception(
                        SysUtil.getpropertyvalue(
                                bean.getLanguage(), "ErrorCode_25"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Object valueAtColumnRow(Vector result,int column,int row ) throws ArrayIndexOutOfBoundsException
    {
        int vnest = 0;
        Object odummy = result;
        while(true){
            if(odummy instanceof Vector == true){
                vnest++;
                if(((Vector)odummy).size() > 0){
                    odummy = ((Vector)odummy).get(0);
                }else{
                    break;
                }
            }else{
                break;
            }
        }
        if(vnest == 3){
            //result = (Vector)result.get(0);
        }
        int realRow = row + 1;
        if (result == null) {
            throw new ArrayIndexOutOfBoundsException ("Result set is empty.");
        }

        if (realRow > result.size()) {
            throw new ArrayIndexOutOfBoundsException ("Row is out of bounds.");
        }
        try
        {
            return ((Vector)result.get(row)).get(column);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }//end of valueAtColumnRow()................

    public String valueAtColumnRow(PsResult psResult,int queryindex,int column,int row ) {
        Vector retvec = psResult.getException();
        try
        {
            if (retvec.size() != 0 && !retvec.get(queryindex).equals(""))
            {
                String errmsg = (String)retvec.get(queryindex);
                throw new Exception(errmsg);
            }
            retvec = psResult.getResult();
            Object val = ((Vector)((Vector)retvec.get(queryindex)).get(row)).get(column);
            return val+"";
        }
        catch(Exception e)
        {
        }
        return null;
    }

    public int getCount(PsResult psResult,int queryindex) throws Exception {
        Vector retvec = psResult.getResult();
        Vector errvec = psResult.getException();
        if (errvec != null) {
            if ((errvec.size() != 0) && (queryindex < errvec.size())
                    && (!errvec.get(queryindex).equals(""))) {
                String errmsg = (String) errvec.get(queryindex);
                throw new Exception(errmsg);
            }
        }
        if ((retvec != null) && (queryindex < retvec.size())) {
            return ((Vector) retvec.get(queryindex)).size();
        }
        return 0;
    }

    public void setSysControl(Hashtable<String,Object> reqHash, PsDBBean bean){
        bean.setRequestHash(reqHash);
        Map<String,Object> requestHash = bean.getRequestHash();
        bean.setCompCode((String)requestHash.get("CompCode"));
        bean.setGroupID((String)requestHash.get("GroupCode"));
        bean.setUserCode((String)requestHash.get("UserCode"));
        bean.setCustID((String)requestHash.get("CustID"));
        if (StrUtil.equals(TmgUtil.Cs_SITE_ID_TMG_INP,bean.getSiteId())) {
            bean.setTargetUser((String)requestHash.get("EmployeeCode"));
        }
        bean.setSystemCode((String)requestHash.get("SystemCode"));

//        setPostID((Vector) this.requestHash.get("PostCode"));
//        setDept((Vector) this.requestHash.get("Dept"));

        if (requestHash.get("CreterialDate") != null) {
            bean.setCreterialDate1((String) requestHash.get("CreterialDate"));
            bean.setCreterialDate2((String) requestHash.get("CreterialDate"));
        }

//        setQueriesObject(this.requestHash.get("Queries"));

//        if (this.requestHash.containsKey("ReplacedCompCode")) {
//            setReplacedCompCode((String)this.requestHash
//                    .get("ReplacedCompCode"));
//        } else {
//            setReplacedCompCode("");
//        }
//        if (this.requestHash.containsKey("ReplacedUserCode")) {
//            setReplacedUserCode((String)this.requestHash
//                    .get("ReplacedUserCode"));
//        } else {
//            setReplacedUserCode("");
//        }

        if (requestHash.containsKey("EmployeeCode") && requestHash.get("EmployeeCode") != null) {
            bean.setEmployeeCode((String)requestHash.get("EmployeeCode"));
        } else {
            bean.setEmployeeCode((String)requestHash.get("UserCode"));
        }

        bean.setLanguage((String)requestHash.get("Language"));

        if (requestHash.get("targetComp") != null) {
            bean.setTargetComp((String)requestHash.get("targetComp"));
        }
        if (requestHash.get("sectionid") != null) {
            bean.setTargetDept((String)requestHash.get("sectionid"));
        }
        if (requestHash.get("compid") != null) {
            bean.setTargetComp((String)requestHash.get("compid"));
        }
        if (requestHash.get("custid") != null) {
            bean.setTargetCust((String)requestHash.get("custid"));
        }
        if (bean.getTargetComp() == null) {
            bean.setTargetComp("");
            bean.setTargetDept("");
            bean.setTargetCust("");
        } else if (StrUtil.isBlank(bean.getTargetComp())) {
            bean.setTargetComp("");
            bean.setTargetDept("");
            bean.setTargetCust("");
        }
//        Hashtable siteurls = (Hashtable) this.requestHash.get("SitePermission");
//        setDomObject((Document) siteurls.get(getCustID() + "_" + getCompCode()
//                + "_" + getSystemCode() + "_" + getGroupID()));
//
//        this.setSysFlg = true;
//        if (this.requestHash.get("PageName") != null) {
//            setStrBeanName((String) this.requestHash.get("PageName"));
//        }
    }
}
