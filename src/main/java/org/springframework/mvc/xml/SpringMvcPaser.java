package org.springframework.mvc.xml;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 类说明
 *
 * @author Zeng zhiqiang
 * @version V1.0 创建时间: 2021/5/19 18:16
 * Copyright 2021 by WiteMedia
 */
public class SpringMvcPaser {

    /**
     * 解析springmvc配置文件，获取静态文件
     *
     * @param springConfig
     * @return java.util.List<java.lang.String>
     * @author Zeng zhiqiang 2021/5/19 18:25
     */
    public static List<String> getStaticResource(String springConfig){
        SAXReader reader = new SAXReader();
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(springConfig);
        List<String> resourceList = new ArrayList<>();
        try {
            Document document = reader.read(inputStream);
            Element rootElement = document.getRootElement();
            List<Element> elements = rootElement.elements("resources");
            if(null != elements){
                for (Element element : elements) {
                    resourceList.add(element.attribute("location").getText());
                }
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return resourceList;
    }
}
