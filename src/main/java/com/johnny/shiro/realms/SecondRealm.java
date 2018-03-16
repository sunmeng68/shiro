package com.johnny.shiro.realms;

import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

public class SecondRealm extends AuthenticatingRealm
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
            credentials = "9c1de23a6e94f017a0d32d40e777fb94223a6fe3";
        }
        else if (username.equals("johnny"))
        {
            credentials = "d7178756125a633c1d6106a32fefcd97420ac03f";
        }
        // 用盐值加密, 利用 唯一的标识 生成盐值 这里用username
        ByteSource salt = ByteSource.Util.bytes(username);

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo("haha",
                credentials, salt, getName());
        // SimpleAuthenticationInfo info = new
        // SimpleAuthenticationInfo(principal,credentials, getName());
        System.out.println("doGetAuthenticationInfo:" + authenticationToken);
        return info;
    }

    public static void main(String[] args)
    {
        String algorithmName = "SHA1";
        String credentials = "12345678";
        Object salt = null;
        salt = ByteSource.Util.bytes("johnny");
        System.out.println(salt);
        int hashIterations = 1;
        Object result = new SimpleHash(algorithmName, credentials, salt,
                hashIterations);
        System.out.println(result);
    }
}
