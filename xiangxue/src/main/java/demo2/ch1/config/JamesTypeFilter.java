package demo2.ch1.config;

import org.springframework.core.io.Resource;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

public class JamesTypeFilter implements TypeFilter {

    public boolean match(MetadataReader reader, MetadataReaderFactory factory) throws IOException {
        // 注解  类信息  资源
        reader.getAnnotationMetadata();
        ClassMetadata classMetadata = reader.getClassMetadata();
        Resource resource = reader.getResource();

        String className = classMetadata.getClassName();
        System.out.println("------>" + className);

        if (className.contains("er")) return true;

        return false;
    }

}
