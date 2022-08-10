package com.lcq.wre.utils;

import com.lcq.wre.dao.pojo.User;

//前端发起的每个请求都对应一个线程，在这个线程里定义一个共享变量来存储当前登录信息，这样就可以方便的再任何地方取得当前登录信息了。
//ThreadLocal类，本地线程，其实它的对象是本地线程的局部变量。该变量为其所属线程所有，各个线程互不影响，可以将需要的数据保存到该对象中。
//但要注意线程结束要调用remove释放该对象中的数据，不然会出现内存泄露的问题。
public class UserThreadLocal {

    private UserThreadLocal(){}
    //线程变量隔离
    //每个线程存储特定信息，互不干扰
    private static final ThreadLocal<User> LOCAL = new ThreadLocal<>();

    public static void put(User sysUser){
        LOCAL.set(sysUser);
    }
    public static User get(){
        return LOCAL.get();
    }
    public static void remove(){
        LOCAL.remove();
    }
}