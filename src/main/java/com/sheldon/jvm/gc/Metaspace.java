package com.sheldon.jvm.gc;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * @author fangxiaodong
 * @date 2021/10/08
 */
public class Metaspace {

    public static void main(String[] args){
        while(true){
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(Garbage.class);
            enhancer.setUseCache(false);
            enhancer.setCallback((MethodInterceptor)(obj, method, args1, proxy) ->
                {
                    if(method.getName().equalsIgnoreCase("printShit")){
//                        System.out.println("printing shit");
                    }
                    return proxy.invokeSuper(obj, args1);
                });

            Garbage garbage = (Garbage)enhancer.create();
            garbage.printShit();
        }
    }

}
