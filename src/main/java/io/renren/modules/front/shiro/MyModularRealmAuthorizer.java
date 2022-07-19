package io.renren.modules.front.shiro;

import ch.qos.logback.core.pattern.ConverterUtil;
import io.renren.common.utils.Constant;
import io.renren.modules.sys.oauth2.OAuth2Realm;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

//多realm授权管理器
public class MyModularRealmAuthorizer extends ModularRealmAuthorizer {

    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        assertRealmsConfigured();
        Collection<Realm> realms = getRealms();
        HashMap<String, Realm> realmHashMap = new HashMap<>(realms.size());
        for (Realm realm : realms) {
            if (realm.getName().contains(Constant.GENERAL_USER)) {
                realmHashMap.put(Constant.GENERAL_USER, realm);
            } else if (realm.getName().contains(Constant.BUSINESS_USER)) {
                realmHashMap.put(Constant.BUSINESS_USER, realm);
            } else {
                realmHashMap.put(Constant.SYS_USER, realm);
            }
        }
        Set<String> realmNames = principals.getRealmNames();
        if (realmNames != null && realmNames.size() > 0) {
            for (String realmName : realmNames) {
                if (realmName.contains(Constant.GENERAL_USER)) {
                    GeneralUserRealm generalUserRealm = (GeneralUserRealm) realmHashMap.get(Constant.GENERAL_USER);
                    return generalUserRealm.isPermitted(principals, permission);
                } else if (realmName.contains(Constant.BUSINESS_USER)) {
                    BusinessUserRealm businessUserRealm = (BusinessUserRealm) realmHashMap.get(Constant.BUSINESS_USER);
                    return businessUserRealm.isPermitted(principals, permission);
                } else if (realmName.contains(Constant.SYS_USER)) {
                    OAuth2Realm sysUserRealm = (OAuth2Realm) realmHashMap.get(Constant.SYS_USER);
                    return sysUserRealm.isPermitted(principals, permission);
                }
            }
        }

        return false;
    }
}
