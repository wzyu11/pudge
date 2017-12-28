package io.transwarp.pudge.client;

import com.nirvana.common.utils.StringUtils;
import io.transwarp.pudge.core.PudgeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注册、管理pudge service
 * Created by zado on 2017/12/22.
 */
public class PudgeServiceManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(PudgeServiceManager.class);

    private static Map<String, PudgeServiceAddress> SERVICE_SOURCES = new ConcurrentHashMap<>();

    /**
     * 注册pudge service
     * 添加serviceName和pudge service的地址等信息
     */
    public synchronized static void register(String serviceName, String serviceUrl) {
        if (StringUtils.isEmpty(serviceName) || StringUtils.isEmpty(serviceUrl)) {
            LOGGER.warn("Empty pudge service or url cannot register");
            return;
        }

        PudgeServiceAddress serviceSource = SERVICE_SOURCES.get(serviceName);
        if (serviceSource == null) {
            serviceSource = genServiceSource(serviceUrl);
            SERVICE_SOURCES.put(serviceName, serviceSource);
            LOGGER.info("Register PudgeService[{}] on serviceUrl[{}] success", serviceName, serviceUrl);
        } else {
            if (!serviceSource.equals(genServiceSource(serviceUrl))) {
                throw new PudgeException("One PudgeService[" + serviceName + "] cannot register different service urls");
            }
        }
    }

    /**
     * 获得pudge service的信息
     */
    public synchronized static PudgeServiceAddress getServiceSource(String serviceName) {
        return SERVICE_SOURCES.get(serviceName);
    }

    public static List<String> listServices() {
        return new ArrayList<>(SERVICE_SOURCES.keySet());
    }

    private static PudgeServiceAddress genServiceSource(String serviceUrl) {
        String[] serviceUrlInfo = serviceUrl.split(":");
        PudgeServiceAddress serviceSource = new PudgeServiceAddress();
        serviceSource.setHost(serviceUrlInfo[0]);
        serviceSource.setPort(Integer.parseInt(serviceUrlInfo[1]));

        return serviceSource;
    }
}
