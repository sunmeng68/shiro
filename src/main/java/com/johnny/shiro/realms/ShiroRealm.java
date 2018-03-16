package com.johnny.shiro.realms;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

public class ShiroRealm extends AuthorizingRealm
{
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authenticationToken)
            throws AuthenticationException
    {

        // 1.把 AuthenticationToken 转化为UsernamePasswordToken
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        token.getPassword();
        // 2.从UsernamePasswordToken 中获取username
        String username = token.getUsername();

        // 3.调用数据库的方法，从数据库中查询username对应的用户记录

        System.out.println("从数据库中获取username：" + username + "所对应的用户信息");

        // 4.若用户不存在，则可以抛出UnknownAccountException异常

        if ("unknown".equals(username))
        {
            throw new UnknownAccountException("用户不存在");
        }
        if ("monster".equals(username))
        {
            throw new LockedAccountException("用户被锁定");
        }

        // 6.根据用户情况，来构建AuthenticationInfo 对象并返回，通常使用的实体类
        // SimpleAuthenticationInfo
        // 1. principal：认证的实体信息，可以是username,也可以是数据表对应的实体类对象
        Object principal = username;

        // 2.credentials：密码 假设当前登录的用户的密码是 加密后的密码
        Object credentials = null;
        if (username.equals("admin"))
        {
            credentials = "3a4ebf16a4795ad258e5408bae7be341";
        }
        else if (username.equals("johnny"))
        {
            credentials = "4684416e2df9588e09eccc8b93938ecf";
        }
        // 用盐值加密, 利用 唯一的标识 生成盐值 这里用username
        ByteSource salt = ByteSource.Util.bytes(username);

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal,
                credentials, salt, getName());
        // SimpleAuthenticationInfo info = new
        // SimpleAuthenticationInfo(principal,credentials, getName());
        System.out.println("doGetAuthenticationInfo:" + authenticationToken);
        return info;
    }

    public static void main(String[] args)
    {
        String algorithmName = "MD5";
        String credentials = "12345678";
        Object salt = null;
        salt = ByteSource.Util.bytes("admin");
        System.out.println(salt);
        int hashIterations = 1;
        Object result = new SimpleHash(algorithmName, credentials, salt,
                hashIterations);
        System.out.println(result);
    }

    /**
     * 授权  shiro要调用的方法
     * @param principalCollection
     * @return
     */
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principalCollection)
    {

        // 1.从 principalCollection中获取登陆的用户信息
        Object principal = principalCollection.getPrimaryPrincipal();

        // 2.利用登陆的用户信息来判断 当前用户的角色和权限（可能要查询数据库）
        Set<String> roles = new HashSet<String>();
        roles.add("user");

        if (principal.equals("admin"))
        {
            roles.add("admin");
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
        return info;
    }
}
