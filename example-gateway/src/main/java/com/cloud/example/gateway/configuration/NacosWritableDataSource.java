package com.cloud.example.gateway.configuration;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.WritableDataSource;
import com.alibaba.csp.sentinel.log.RecordLog;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;

import java.util.Properties;

/**
 * A writable {@code DataSource} with Nacos backend.
 *
 * @author Benji
 * @date 2019-05-30
 */
public class NacosWritableDataSource<T> implements WritableDataSource<T> {

    /**
     * The type group ID
     */
    private final String groupId;
    /**
     * The type data ID
     */
    private final String dataId;
    /**
     * The type Converter
     */
    private final Converter<T, String> parser;
    /**
     * Note: The Nacos config might be null if its initialization failed.
     */
    private ConfigService configService = null;

    /**
     * Constructs an writable DataSource with Nacos backend.
     *
     * @param serverAddr server address of Nacos, cannot be empty
     * @param groupId    group ID, cannot be empty
     * @param dataId     data ID, cannot be empty
     * @param parser     customized data parser, cannot be empty
     */
    public NacosWritableDataSource(final String serverAddr, final String groupId, final String dataId,
                                   Converter<T, String> parser) {
        this(NacosWritableDataSource.buildProperties(serverAddr), groupId, dataId, parser);
    }

    /**
     * Constructs an writable DataSource with Nacos backend.
     *
     * @param properties properties for construct {@link ConfigService} using {@link NacosFactory#createConfigService(Properties)}
     * @param groupId    group ID, cannot be empty
     * @param dataId     data ID, cannot be empty
     * @param parser     customized data parser, cannot be empty
     */
    public NacosWritableDataSource(final Properties properties, final String groupId, final String dataId,
                                   Converter<T, String> parser) {
        if (StringUtil.isBlank(groupId) || StringUtil.isBlank(dataId)) {
            throw new IllegalArgumentException(String.format("Bad argument: groupId=[%s], dataId=[%s]",
                    groupId, dataId));
        }
        AssertUtil.notNull(properties, "Nacos properties must not be null, you could put some keys from PropertyKeyConst");
        this.groupId = groupId;
        this.dataId = dataId;
        this.parser = parser;

        initNacosConfigService(properties);
    }

    /**
     * init Nacos config server
     *
     * @param properties the properties
     */
    private void initNacosConfigService(final Properties properties) {
        try {
            this.configService = NacosFactory.createConfigService(properties);
        } catch (Exception e) {
            RecordLog.warn("[NacosDataSource] Error occurred when initializing Nacos data source", e);
            e.printStackTrace();
        }
    }

    @Override
    public void write(T value) throws Exception {
        if (value == null) {
            return;
        }
        configService.publishConfig(dataId, groupId, this.parser.convert(value));
    }

    @Override
    public void close() {
    }

    private static Properties buildProperties(final String serverAddr) {
        if (StringUtil.isBlank(serverAddr)) {
            throw new IllegalArgumentException(String.format("Bad argument: serverAddr=[%s]", serverAddr));
        }
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, serverAddr);
        return properties;
    }
}
