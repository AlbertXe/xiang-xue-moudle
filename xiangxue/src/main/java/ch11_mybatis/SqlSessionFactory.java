package ch11_mybatis;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;

/**
 * 86150
 * SqlSessionFactory
 * 2020/4/10 19:44
 */
public class SqlSessionFactory {
    private final Configuration conf = new Configuration();

    public SqlSessionFactory(Configuration conf) {
        loadDbInfo();
        loadMappersInfo();
    }

    public SqlSession openSession() {
        return new DefaultSqlSession(conf);
    }

    //加载指定文件夹下所有的mapper.xml
    private void loadMappersInfo() {
        URL resource = SqlSessionFactory.class.getResource("/mapper");
        File file = new File(resource.getFile());
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                loadFIle(file1);
            }
        }
    }

    private void loadFIle(File file) {
        SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read(file);
            Element root = doc.getRootElement();
            String namespace = root.attribute("namespace").getData().toString();

            List<Element> selects = root.elements("select");
            for (Element element : selects) {
                String id = element.attribute("id").getData().toString();
                String resultType = element.attribute("resultType").getData().toString();
                String sql = element.getData().toString();
                String sourceId = namespace + "." + id;

                MappedStatement ms = new MappedStatement();
                ms.setNamespace(namespace);
                ms.setResultType(resultType);
                ms.setSql(sql);
                ms.setSourceId(sourceId);
                conf.getMappedStatementMap().put(sourceId, ms);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    private void loadDbInfo() {
        InputStream is = SqlSessionFactory.class.getClassLoader().getResourceAsStream("db.properties");
        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        conf.setJdbcDriver(properties.getProperty("jdbc.driver"));
        conf.setJdbcPassword(properties.getProperty("jdbc.password"));
        conf.setJdbcUrl(properties.getProperty("jdbc.url"));
        conf.setJdbcUsername(properties.getProperty("jdbc.username"));
    }
}
