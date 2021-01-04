package com.dao.venus;

import com.ctrip.framework.apollo.ConfigFile;
import com.ctrip.framework.apollo.ConfigFileChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.core.enums.ConfigFileFormat;
import com.ctrip.framework.apollo.model.ConfigFileChangeEvent;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import javax.sql.DataSource;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-31 16:33
 */
@Slf4j
public abstract class AbstractDataSourceBuilder {

    public DataSource build(String namespace){
        Map<String, String> map = initMap();
        ConfigFile configFile = ConfigService.getConfigFile(namespace, ConfigFileFormat.YAML);
        // 监听apollo配置更新
        this.addListener(configFile, namespace);
        map.put("DSConfig", this.desensitize(configFile.getContent()));
        DataSource dataSource = build(configFile.getContent().getBytes(StandardCharsets.UTF_8));
        return dataSource;
    }

    public DataSource build(String yaml, boolean isLocal) {
        if (!isLocal) {
           return build(yaml);
        }else {
            Map<String, String> map = initMap();
            byte[] bytes = readFiletoBytes(yaml);
            DataSource dataSource = build(bytes);
            map.put("DSConfig", desensitize(new String(bytes, StandardCharsets.UTF_8)));
            return dataSource;
        }
    }

    private byte[] readFiletoBytes(String yaml) {
        try (
                InputStream is = this.getClass().getResourceAsStream(yaml);
                BufferedInputStream bis = new BufferedInputStream(is);
        ) {
            byte[] b = new byte[bis.available()];
            bis.read(b);
            String content = new String(b, StandardCharsets.UTF_8);
            log.info("yaml content is {}" ,content);
            return b;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addListener(ConfigFile configFile, String namespace){
        configFile.addChangeListener(new ConfigFileChangeListener() {
            @Override
            public void onChange(ConfigFileChangeEvent changeEvent) {
                Map<String, String> map = AbstractDataSourceBuilder.this.initMap();

                String newValue = changeEvent.getNewValue();
                Preconditions.checkNotNull(newValue, "apollo namespace content can not be null");
                if (AbstractDataSourceBuilder.this.needReload(newValue)) {
                    DataSource ds = null;
                    try {
                        ds = AbstractDataSourceBuilder.this.build(newValue.getBytes(StandardCharsets.UTF_8));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    map.put("DSConfig", AbstractDataSourceBuilder.this.desensitize(newValue));
                    // 时间发布
                    AbstractDataSourceBuilder.this.refresh(ds, namespace);
                    return;
                }

            }
        });

    }

    protected abstract void refresh(DataSource ds, String namespace);

    // 敏感信息
    private String desensitize(String newValue) {
        return newValue;
    }

    protected abstract DataSource build(byte[] bytes);

    protected Map<String,String> initMap(){
        Map<String, String> map = new LinkedHashMap<>();
        map.put("AppId", null);
        map.put("Version", null);
        map.put("JDK", null);
        return map;
    }

    private boolean needReload(String yaml) {
        Yaml y = new Yaml();
        List<String>  list = y.load(yaml);
        return list.stream().anyMatch(t -> t.contains("vernusDataSourceReload: false"));
    }
}
