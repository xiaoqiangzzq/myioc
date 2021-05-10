package org.springframework.container;

import org.springframework.xml.SpringConfigPaser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
        System.out.println("spring的基础包" + beanPackage);

        //加载所有的类
        this.loadClass(beanPackage);
        //类的实例化

    }

     private void initInstances(){

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
