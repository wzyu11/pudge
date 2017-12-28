package io.transwarp.pudge.spring;

import com.nirvana.common.utils.scanner.AnnotationClassFilter;
import com.nirvana.common.utils.scanner.PackageScanner;
import io.transwarp.pudge.core.annotation.PudgeService;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Nirvana on 2017/12/13.
 */
public class PudgeServiceScanner {

    private String backPackage;

    private Collection<Class<?>> serviceInterfaces;

    public PudgeServiceScanner() {
        this("");
    }

    public PudgeServiceScanner(String backPackage) {
        this.backPackage = backPackage != null ? backPackage : "";
    }

    public Collection<Class<?>> scan(String serviceName) {
        if (serviceInterfaces == null) {
            PackageScanner scanner = new PackageScanner(backPackage, new AnnotationClassFilter(PudgeService.class));
            serviceInterfaces = scanner.scan();
        }
        Collection<Class<?>> result = new ArrayList<>();
        for (Class<?> clazz : serviceInterfaces) {
            if (clazz.isInterface()) {
                PudgeService pudgeService = clazz.getAnnotation(PudgeService.class);
                if (pudgeService != null) {
                    if (pudgeService.name().equals(serviceName)) {
                        result.add(clazz);
                    }
                }
            }
        }
        return result;
    }

    public String getBackPackage() {
        return backPackage;
    }
}
