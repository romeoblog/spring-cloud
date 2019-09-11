package com.cloud.mesh.core.solver;

import com.cloud.mesh.common.utils.JacksonUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Default Solver
 *
 * @author willlu.zheng
 * @date 2019-09-02
 */
@Component
public class DefaultSolver extends AbstractInspectionSolver<Map<String, Object>> {

    @Override
    public void solve(Map<String, Object> params) {
        System.out.println(JacksonUtils.toJson(params) + ":Default");
    }

    @Override
    public String[] supports() {
        return new String[]{"Default:Key"};
    }

}