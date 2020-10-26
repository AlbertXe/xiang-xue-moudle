package ch8_tomcat_ngix;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类加载 xml
 * 86150
 * ProjectLoad
 * 2020/3/14 23:35
 */
public class ProjectLoader {
    static Logger log = LoggerFactory.getLogger(ProjectLoader.class);

    public static Map<String, Object> load() throws Exception {
        Map<String, Object> webapp = new HashMap<>();

        Map<String, Servlet> servletMap = new HashMap<>();
        Map<String, String> servletMapping = new HashMap<>();

        String webpath = "D:\\work\\xiangxue\\src\\main\\java\\ch8_tomcat_ngix\\";

        URL url = new URL("file:" + webpath);
        URLClassLoader classLoader = new URLClassLoader(new URL[]{url});

        //dom4j
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(webpath + "web.xml"));
        Element root = document.getRootElement();
        List<Element> elements = root.elements();
        for (Element element : elements) {
            if ("servlet".equals(element.getName())) {
                Element servletName = element.element("servlet-name");
                Element servletClass = element.element("servlet-class");
                log.info("servletName:[{}],servletClass:[{}]", servletName, servletClass);

                Servlet servlet = (Servlet) classLoader.loadClass(servletClass.getText()).newInstance();
                servletMap.put(servletName.getText(), servlet);

            }
        }

        return null;
    }

    public static void main(String[] args) throws Exception {
        load();
    }
}
