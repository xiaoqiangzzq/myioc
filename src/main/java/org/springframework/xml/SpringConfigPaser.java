package org.springframework.xml;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

/**
 * spring配置文件解析
 *
 * @author Zeng zhiqiang
 * @version V1.0 创建时间: 2021/5/8 18:35
 */
public class SpringConfigPaser {

    public static String getBeanPackage(String springConfig){
        SAXReader reader = new SAXReader();
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(springConfig);
        String text = "";
        try {
            Document document = reader.read(inputStream);
            Element rootElement = document.getRootElement();
            Element element = rootElement.element("component-scan");
            Attribute attribute = element.attribute("base-package");
             text = attribute.getText();

        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return text;
    }
}
