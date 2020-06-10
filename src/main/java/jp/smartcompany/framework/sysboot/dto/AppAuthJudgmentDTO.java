package jp.smartcompany.framework.sysboot.dto;


import jp.smartcompany.framework.auth.entity.AppAuthJudgmentEntity;
import lombok.*;

import java.util.Date;

/**
 * @author Xiao Wenpeng
 */
@Setter
@ToString
@NoArgsConstructor
public class AppAuthJudgmentDTO extends AppAuthJudgmentEntity {

    private AppAuthJudgmentEntity appAuthJudgmentEntity;

    public AppAuthJudgmentDTO(AppAuthJudgmentEntity appAuthJudgmentEntity) {
        this.appAuthJudgmentEntity = appAuthJudgmentEntity;
    }

    @Override
    public Long getMtrId(){
        return appAuthJudgmentEntity.getMtrId();
    }
    @Override
    public String getMtrCobjectid(){
        return appAuthJudgmentEntity.getMtrCobjectid();
    }
    @Override
    public String getMtrCsiteid(){
        return appAuthJudgmentEntity.getMtrCsiteid();
    }
    @Override
    public String getMtrCappid(){
        return appAuthJudgmentEntity.getMtrCappid();
    }
    @Override
    public String getMtrCsubappid(){
        return appAuthJudgmentEntity.getMtrCsubappid();
    }
    @Override
    public String getMtrCscreenid(){
        return appAuthJudgmentEntity.getMtrCscreenid();
    }
    @Override
    public String getMtrCbuttonid(){
        return appAuthJudgmentEntity.getMtrCbuttonid();
    }
    @Override
    public String getMtrCobjname(){
        return appAuthJudgmentEntity.getMtrCobjname();
    }
    @Override
    public String getMtrCobjnameja(){
        return appAuthJudgmentEntity.getMtrCobjnameja();
    }
    @Override
    public String getMtrCobjnameen(){
        return appAuthJudgmentEntity.getMtrCobjnameen();
    }
    @Override
    public String getMtrCobjnamech(){
        return appAuthJudgmentEntity.getMtrCobjnamech();
    }
    @Override
    public String getMtrCobjname01(){
        return appAuthJudgmentEntity.getMtrCobjname01();
    }
    @Override
    public String getMtrCobjname02(){
        return appAuthJudgmentEntity.getMtrCobjname02();
    }
    @Override
    public String getMtrCtype(){
        return appAuthJudgmentEntity.getMtrCtype();
    }
    @Override
    public String getMtrCtemplateid(){
        return appAuthJudgmentEntity.getMtrCtemplateid();
    }
    @Override
    public String getMtrCurl(){
        return appAuthJudgmentEntity.getMtrCurl();
    }
    @Override
    public String getMtrCimageurl(){
        return appAuthJudgmentEntity.getMtrCimageurl();
    }
    @Override
    public String getMtrCsitecaption(){
        return appAuthJudgmentEntity.getMtrCsitecaption();
    }
    @Override
    public String getMtrCsitecaptionja(){
        return appAuthJudgmentEntity.getMtrCsitecaptionja();
    }
    @Override
    public String getMtrCsitecaptionen(){
        return appAuthJudgmentEntity.getMtrCsitecaptionen();
    }
    @Override
    public String getMtrCsitecaptionch(){
        return appAuthJudgmentEntity.getMtrCsitecaptionch();
    }
    @Override
    public String getMtrCsitecaption01(){
        return appAuthJudgmentEntity.getMtrCsitecaption01();
    }
    @Override
    public String getMtrCsitecaption02(){
        return appAuthJudgmentEntity.getMtrCsitecaption02();
    }
    @Override
    public Long getMtrNseq(){
        return appAuthJudgmentEntity.getMtrNseq();
    }
    @Override
    public String getMtrCversion(){
        return appAuthJudgmentEntity.getMtrCversion();
    }
    @Override
    public String getMtrCsystemid(){
        return appAuthJudgmentEntity.getMtrCsystemid();
    }
    @Override
    public String getMtrCdefaulttargetuser(){
        return appAuthJudgmentEntity.getMtrCdefaulttargetuser();
    }
    @Override
    public String getMtrCcriterialdatetype(){
        return appAuthJudgmentEntity.getMtrCcriterialdatetype();
    }
    @Override
    public String getMtrCdatapermissiontype(){
        return appAuthJudgmentEntity.getMtrCdatapermissiontype();
    }
    @Override
    public String getMtrCappautoload(){
        return appAuthJudgmentEntity.getMtrCappautoload();
    }
    @Override
    public String getMtrConlinehelpurl(){
        return appAuthJudgmentEntity.getMtrConlinehelpurl();
    }
    @Override
    public String getMtrConlinehelpattr(){
        return appAuthJudgmentEntity.getMtrConlinehelpattr();
    }
    @Override
    public String getMtrCdomainid(){
        return appAuthJudgmentEntity.getMtrCdomainid();
    }
    @Override
    public String getMtrCmodifieruserid(){
        return appAuthJudgmentEntity.getMtrCmodifieruserid();
    }
    @Override
    public Date getMtrDmodifieddate(){
        return appAuthJudgmentEntity.getMtrDmodifieddate();
    }
    @Override
    public Long getVersionno(){
        return appAuthJudgmentEntity.getVersionno();
    }
    @Override
    public String getMtrCiframeflag() {
        return appAuthJudgmentEntity.getMtrCiframeflag();
    }
    @Override
    public String getMgpCsystemid() {
        return appAuthJudgmentEntity.getMgpCsystemid();
    }
    @Override
    public String getMgpCgroupid() {
        return appAuthJudgmentEntity.getMgpCgroupid();
    }
}
