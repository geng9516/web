package jp.smartcompany.boot.configuration.security.dto;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.job.modules.core.pojo.bo.LoginAccountBO;
import jp.smartcompany.job.modules.core.pojo.bo.LoginGroupBO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor
@ToString
@Getter
@Setter
public class SmartUserDetails implements UserDetails {

    private LoginAccountBO user;

    private String hdCcustomeridCk; //HD_CCUSTOMERID_CK
    private String hdCcompanyidCk; //HD_CCOMPANYID_CK
    private String macClayeredcompanyid; //MAC_CLAYEREDCOMPANYID
    private Integer macNseq; //MAC_NSEQ
    private String macCcompanyname; //MAC_CCOMPANYNAME
    private String hdCemployeeidCk; //HD_CEMPLOYEEID_CK
    private String hdCuserid; //HD_CUSERID
    private String meCemployeename; //ME_CEMPLOYEENAME
    private String meCkananame; //ME_CKANANAME
    private String hdCsectionidFk; //HD_CSECTIONID_FK
    private String moClayeredsectionid; //MO_CLAYEREDSECTIONID
    private Integer moNseq; //MO_NSEQ
    private String moCsectionname; //MO_CSECTIONNAME
    private String hdCpostidFk; //HD_CPOSTID_FK
    private Integer mapNweightage; //MAP_NWEIGHTAGE
    private String mapCpostname; //MAP_CPOSTNAME
    private String hdCifkeyoradditionalrole; //HD_CIFKEYORADDITIONALROLE
    private Date hdDstartdateCk; //HD_DSTARTDATE_CK
    private String hdCbossornot; //HD_CBOSSORNOT
    private String workTypeName;

    private String password;
    private boolean locked;
    private boolean passwordExpired;
    private Map<String, List<LoginGroupBO>> loginGroups;

    public SmartUserDetails(LoginAccountBO user, Map<String, List<LoginGroupBO>> loginGroups,String encodePassword,boolean locked,boolean passwordExpired) {
        if (user != null) {
            this.user = user;
            this.loginGroups = loginGroups;
            this.password = encodePassword;
            this.locked = locked;
            this.passwordExpired = passwordExpired;
            this.hdCcustomeridCk = user.getHdCcustomeridCk();
            this.hdCcompanyidCk = user.getHdCcompanyidCk();
            this.macClayeredcompanyid = user.getMacClayeredcompanyid();
            this.macNseq = user.getMacNseq();
            this.macCcompanyname = user.getMacCcompanyname();
            this.hdCemployeeidCk = user.getHdCemployeeidCk();
            this.hdCuserid = user.getHdCuserid();
            this.meCemployeename = user.getMeCemployeename();
            this.meCkananame = user.getMeCkananame();
            this.hdCsectionidFk = user.getHdCsectionidFk();
            this.moClayeredsectionid = user.getMoClayeredsectionid();
            this.moNseq = user.getMoNseq();
            this.hdCpostidFk = user.getHdCpostidFk();
            this.mapNweightage = user.getMapNweightage();
            this.mapCpostname = user.getMapCpostname();
            this.hdCifkeyoradditionalrole = user.getHdCifkeyoradditionalrole();
            this.hdDstartdateCk = user.getHdDstartdateCk();
            this.hdCbossornot = user.getHdCbossornot();
            this.moCsectionname = user.getMoCsectionname();
            this.workTypeName = user.getWorkTypeName();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = CollUtil.newArrayList();
        List<LoginGroupBO> groupList = CollUtil.newArrayList();

        // 默认为单法人
        loginGroups.forEach((key,value)-> {
            if (StrUtil.equals("01",key)) {
                CollUtil.addAllIfNotContains(groupList,value);
            }
        });
        List<String> groupCodes =groupList.stream().map(LoginGroupBO::getGroupCode).collect(Collectors.toList());
        groupCodes.forEach(role -> {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+role.toUpperCase());
            authorities.add(authority);
        });
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return user.getHdCuserid();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return passwordExpired;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
