package com.cloud.mesh.gateway.route;

/**
 * The type  Listener Interface
 *
 * @author willlu.zheng
 * @date 2020-07-02
 */
public interface Listener {

    /**
     * the listener method impl
     *
     * @param dynamicRouteService
     */
    void handlerListener(IDynamicRouteService dynamicRouteService);

}
