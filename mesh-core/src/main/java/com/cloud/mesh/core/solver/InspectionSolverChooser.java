package com.cloud.mesh.core.solver;

import com.google.common.collect.Maps;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * The inspection solver Chooser implements.
 *
 * @author willlu.zheng
 * @date 2019-09-02
 */
@Component
public class InspectionSolverChooser implements ApplicationContextAware {

    private Map<String, AbstractInspectionSolver> chooseMap = Maps.newHashMap();

    public AbstractInspectionSolver choose(String key) {
        return chooseMap.get(key);
    }

    @PostConstruct
    public void register() {
        Map<String, AbstractInspectionSolver> solverMap = context.getBeansOfType(AbstractInspectionSolver.class);
        for (AbstractInspectionSolver solver : solverMap.values()) {
            for (String support : solver.supports()) {
                chooseMap.put(support, solver);
            }
        }
    }

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

}