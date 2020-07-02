package com.cloud.mesh.gateway.route;

import com.cloud.mesh.gateway.route.impl.DynamicRouteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;

/**
 * Run the Listener application.
 *
 * @author willlu.zheng
 * @date 2020/6/15
 */
public abstract class AbstractDynamicRouteListener implements Listener, CommandLineRunner {

    @Autowired
    @Qualifier("dynamicRouteServiceImpl")
    private DynamicRouteServiceImpl dynamicRouteService;

    /**
     * Run the Listener application, creating and refreshing a new listener.
     *
     * @param args the args.
     */
    @Override
    public void run(String... args) {
        this.handlerListener(dynamicRouteService);
    }

    /**
     * Run the Listener application, creating and refreshing a new listener.
     *
     * @param dynamicRouteService the dynamic Route service.
     */
    @Override
    public abstract void handlerListener(IDynamicRouteService dynamicRouteService);
}
