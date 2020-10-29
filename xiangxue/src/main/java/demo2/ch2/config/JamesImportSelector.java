package demo2.ch2.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class JamesImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"demo2.ch2.bean.Tiger", "demo2.ch2.bean.Fish"};
    }

}
