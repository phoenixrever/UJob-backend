package io.renren.modules.front.shiro;

import io.renren.common.utils.Constant;
import io.renren.modules.sys.oauth2.OAuth2Token;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.ArrayList;
import java.util.Collection;


/**
 * 多realm认证器
 */
@Slf4j
public class MyModularRealmAuthenticator extends ModularRealmAuthenticator {
    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        super.assertRealmsConfigured();
        OAuth2Token oAuth2Token = (OAuth2Token) authenticationToken;
        String userType = oAuth2Token.getUserType();

        //获取所有的realm
        Collection<Realm> realms = getRealms();


        //这一步 所有的realm的都加进来 好像没必要 因为我们只有一个realm参与比对 暂且先这样
        ArrayList<Realm> realmList = new ArrayList<>();
        for (Realm realm : realms) {
            System.out.println(realm.getName());
            //io.renren.modules.front.shiro.GeneralUserRealm_1
            if (realm.getName().contains(userType)) {
                realmList.add(realm);
            }
        }

        //这个 userType 对应判断单realm 还是多realm
          if (realmList.size() == 1) {
              log.info("单realm");
                return doSingleRealmAuthentication(realmList.get(0), oAuth2Token);
            } else {
                log.info("多realm");
                return doMultiRealmAuthentication(realmList, oAuth2Token);
            }
    }
}
