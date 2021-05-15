package org.springframework.container;

import org.springframework.annotation.*;
import org.springframework.proxy.CglibProxy;
import org.springframework.proxy.JdkProxy;
import org.springframework.xml.SpringConfigPaser;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 容器类
 *
 * @author Zeng zhiqiang
 * @version V1.0 创建时间: 2021/5/8 18:06
 * Copyright 2021 by WiteMedia
 */
public class ClassPathXmlApplicationContext {

    //applicationContext
    private String springConfig;

    //spring的名字容器作为key
    private ConcurrentHashMap<String,Object> iocNameContainer = new ConcurrentHashMap<String, Object>();

    //spring ioc 的class key 作为容器
    private Map<Class<?>,Object> iocClassContainer = new ConcurrentHashMap<Class<?>, Object>();

    //spring 的ioc容器 对象实现的接口作为key，接口的实现类作为value
    private Map<Class<?>,List<Object>> iocInterfaceContainer = new ConcurrentHashMap<Class<?>,List<Object>>();

    //存储切面类集合
    private Set<Class<?>> aopClassContainer = new CopyOnWriteArraySet<>();


    //路径下的class文件
    private List<String> classPaths = new ArrayList<String>();
    public ClassPathXmlApplicationContext(String springConfig){
        this.springConfig = springConfig;
        init();
    }

    /**
     * 初始化方法
     *
     * @return void
     * @author Zeng zhiqiang 2021/5/10 16:01
     */
    private void init(){
        String beanPackage = SpringConfigPaser.getBeanPackage(springConfig);
        System.out.println("spring的要扫描基础包" + beanPackage);

        //加载所有的类
        this.loadClass(beanPackage);
        //类的实例化，扫描包下的class反射创建对象
        initInstances();
        System.out.println(iocNameContainer);

        //执行Aop切面

        try {
            doAop();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //实现依赖注入
        this.di();
    }

    //执行切面
    private void doAop() throws ClassNotFoundException {
        //循环集合
        if(aopClassContainer.size() > 0){
            for (Class<?> c : aopClassContainer) {
                //获取切面类中的方法
                Method[] declaredMethods = c.getDeclaredMethods();
                if(declaredMethods != null){
                    for (Method declaredMethod : declaredMethods) {
                        boolean annotationPresent = declaredMethod.isAnnotationPresent(Around.class);
                        if(Boolean.TRUE.equals(annotationPresent)){
                            Around aroundAnnotation = declaredMethod.getAnnotation(Around.class);
                            //切入的目标类方法
                            String execution = aroundAnnotation.execution();
                            //要代理的目标全名称
                            String fullClass = execution.substring(0, execution.lastIndexOf("."));
                            //代理的方法
                            String methodName = execution.substring(execution.lastIndexOf(".") + 1);
                            //要代理的类
                            Class<?> targetClass = Class.forName(fullClass);
                            //要被代理的类对象
                            Object targetObject = iocClassContainer.get(targetClass);
                            //targetObject 中的methodName 进行代理
                            JdkProxy<Object> jdkProxy = new JdkProxy<>(targetClass,c,targetObject,methodName,
                                    declaredMethod);
                            //代理类的对象 com.sun.proxy.$Proxy6
                            //问题：如果遇到无法注入的问题，考虑代理先di(),在后期循环di（）注入的时候，排除代理
                            Object proxyInstance = jdkProxy.getProxyInstance();
                            System.out.println(proxyInstance.getClass().getName());
                            //把原始集合中的对象替换 为代理对象
                            iocClassContainer.put(targetClass,proxyInstance);
                            //2.替换iocNameContainer
                            String simpleName = targetClass.getSimpleName();
                            String className =
                                    String.valueOf(simpleName.charAt(0)).toLowerCase() + simpleName.substring(1);
                            Service service = targetClass.getAnnotation(Service.class);
                            Controller controller = targetClass.getAnnotation(Controller.class);
                            Repository repository = targetClass.getAnnotation(Repository.class);
                            if(service!= null){
                                String value = service.value();
                                if(!value.isEmpty()){
                                    className = value;
                                }
                            }
                            if(controller!= null){
                                String value = controller.value();
                                if(!value.isEmpty()){
                                    className = value;
                                }
                            }
                            if(repository!= null){
                                String value = repository.value();
                                if(!value.isEmpty()){
                                    className = value;
                                }
                            }
                            iocNameContainer.put(className,proxyInstance);
                            //3.替换接口集合
                            Class<?>[] interfaces = targetClass.getInterfaces();
                            if(interfaces!= null ){
                                for (Class<?> anInterface : interfaces) {
                                    List<Object> objects = iocInterfaceContainer.get(anInterface);
                                    if(objects!=null){
                                        for (int i = 0; i < objects.size(); i++) {
                                            Object o = objects.get(i);
                                            if(o.getClass()==targetClass){
                                                objects.set(i,proxyInstance);
                                                break;
                                            }
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }

    }

    //实现依赖注入
    private void di(){
        Set<Class<?>> classes = iocClassContainer.keySet();
        if(classes != null){
            for (Class<?> aClass : classes) {
                Field[] declaredFields = aClass.getDeclaredFields();
                if(null != declaredFields){
                    for (Field declaredField : declaredFields) {
                        boolean annotationPresent = declaredField.isAnnotationPresent(Autowired.class);
                        Object bean = null;
                        if(annotationPresent){
                            //需要依赖注入
                            Autowired autowired = declaredField.getAnnotation(Autowired.class);
                            String value = autowired.value();
                            if(!"".equals(value)){
                                //value上的名字
                                bean = getBean(value);
                                if(null == bean){
                                    throw new RuntimeException("not find class named" + value);
                                }
                            }else {
                                //注解上没有名字，根据类型去匹配
                                Class<?> type = declaredField.getType();
                                bean = getBean(type);
                                if(bean == null){
                                    //根据接口再次匹配
                                    bean = getBeanByInterface(type);
                                    if(bean == null){
                                        throw new RuntimeException("not find bean : " + type);
                                    }
                                }
                            }
                            //反射注入对象
                            declaredField.setAccessible(true);
                            try {
                                declaredField.set(iocClassContainer.get(aClass),bean);
                            } catch (IllegalAccessException e) {
                                System.out.println("error....");
                                e.printStackTrace();
                            }

                        }
                    }
                }
            }
        }
    }


    //根据对象名字获取实例bean
    public Object getBean(String name){
        return iocNameContainer.get(name);
    }

    public Object getBean(Class<?> c){
        return iocClassContainer.get(c);
    }

    public Object getBeanByInterface(Class<?> c){
        List<Object> objects = iocInterfaceContainer.get(c);
        if(objects == null){
            return null;
        }
        if(objects.size() > 1){
            throw new RuntimeException("find more than one bean : " + c);
        }
        return objects.get(0);
    }
    /**
     * 将ioc容器中的类进行实例化
     *
     * @return void
     * @author Zeng zhiqiang 2021/5/11 18:39
     */
     private void initInstances(){
         try {
             for(String classPath : classPaths){
                 Class<?> c = Class.forName(classPath);

                 //定义那些实现了aspect
                 if(c.isAnnotationPresent(Aspect.class)){
                     //将切面类存储到集合中
                     aopClassContainer.add(c);
                     continue;
                 }
                 if(c.isAnnotationPresent(Controller.class) || c.isAnnotationPresent(Service.class) ||c.isAnnotationPresent(Repository.class)){
                     //对象集合
                     Object o = c.newInstance();
                     //判断是否有transactional修饰，支持事务
                     Method[] declaredMethods = c.getDeclaredMethods();
                     //存储事务方法名称
                     List<String> transactionList = new ArrayList<>();
                     if(null != declaredMethods){
                         for (Method declaredMethod : declaredMethods) {
                             boolean annotationPresent = declaredMethod.isAnnotationPresent(Transactional.class);
                             if(Boolean.TRUE.equals(annotationPresent)){
                                 transactionList.add(declaredMethod.getName());
                             }
                         }

                         if(transactionList.size() > 0){
                             //为类中的方法添加事务
                             CglibProxy cglibProxy = new CglibProxy(c, transactionList);
                             o = cglibProxy.getProxyInstance();
                         }
                     }
                     iocClassContainer.put(c,o);
                     //接口对象集合
                     Class<?>[] interfaces = c.getInterfaces();
                     if(null != interfaces){
                         for (Class<?> anInterface : interfaces) {
                             List<Object> objects = iocInterfaceContainer.get(anInterface);
                             if(objects == null){
                                 ArrayList<Object> objs = new ArrayList<Object>();
                                 objs.add(o);
                                 iocInterfaceContainer.put(anInterface,objs);
                             }else {
                                 objects.add(o);
                             }
                         }
                     }
                     Controller controllerAnnotation = c.getAnnotation(Controller.class);
                     Service serviceAnnotation = c.getAnnotation(Service.class);
                     Repository repositoryAnnotation = c.getAnnotation(Repository.class);
                     if(controllerAnnotation != null){
                         //获取注解value属性值
                         String value = controllerAnnotation.value();
                         addClass(c, value,o);
                     }
                     if(serviceAnnotation != null){
                         //获取注解value属性值
                         String value = serviceAnnotation.value();
                         addClass(c, value,o);
                     }
                     if(repositoryAnnotation != null){
                         //获取注解value属性值
                         String value = repositoryAnnotation.value();
                         addClass(c, value,o);
                     }

                 }
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
     }

    private void addClass(Class<?> c, String value,Object o) throws Exception {
        String objectName = "";
        if(value.equals("")){
            String className = c.getSimpleName();
            objectName = String.valueOf(className.charAt(0)).toLowerCase() + className.substring(1);
        }else {
            objectName = value;
        }
        if(iocNameContainer.containsKey(objectName)){
            throw new Exception("spring ioc container  is already exists the bean " + objectName);
        }
        iocNameContainer.put(objectName,o);
    }

    /**
     * 加载基础包下所有的class
     *
     * @param basePackage
     * @return void
     * @author Zeng zhiqiang 2021/5/10 16:03
     */
    private void loadClass(String basePackage){
        URL resource = ClassPathXmlApplicationContext.class.getClassLoader().getResource("");
        System.out.println(resource);
        basePackage = basePackage.replace(".", File.separator);
        System.out.println(basePackage);
        File file = new File(resource.toString().replace("file:/",""),basePackage);
        String path = file.toString();
        if(path.contains("test-classes")){
            path = path.replace("test-classes","classes");
        }
        this.findAllClasses(new File(path));
    }

    /**
     * 遍历所有的classes
     *  
     * @return void
     * @author Zeng zhiqiang 2021/5/10 17:13
     */
    private void findAllClasses(File f){
        File[] files = f.listFiles();
        for (File file:files){
            if (!file.isDirectory()){
                String path = file.getPath();
                System.out.println(path);
                path = this.handlePath(path);
                classPaths.add(path);
            }else {
                this.findAllClasses(file);
            }
        }
    }

    private String handlePath(String path){
        int i = path.indexOf("classes\\");
        path = path.substring(i + 8, path.length() - 6);
        path = path.replace(File.separator,".");
        System.out.println(path);
        return path;
    }

}
