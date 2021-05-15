package org.springframework.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.annotation.Transactional;
import org.springframework.tx.TransactionManager;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.List;

//动态代理
public class CglibProxy {

    //要代理的目标类
    Class<?> targetClass;

    //代理的方法
    List<String> transacionList;

    public CglibProxy(Class<?> targetClass, List<String> transacionList) {
        this.targetClass = targetClass;
        this.transacionList = transacionList;
    }

    public Object getProxyInstance(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback(new MethodInterceptor() {

            public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

                Object result = null;

                if(transacionList.contains(method.getName())){
                    System.out.println("关闭事务自动提交");
                    Connection threadConnection = TransactionManager.getThreadConnection();
                    threadConnection.setAutoCommit(false);
                    try {
                        //调用目标方法
                        result = methodProxy.invokeSuper(o,args);
                        System.out.println("手动提交事务。。。。");
                        threadConnection.commit();
                    }catch (Exception e){
                        System.out.println("事务回滚");
                        threadConnection.rollback();
                    }finally {

                    }
                }else {
                    result = methodProxy.invokeSuper(o,args);
                }
                return result;
            }
        });
        return enhancer.create();
    }
}
