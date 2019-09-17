/*
 *  Copyright 2019 https://github.com/romeoblog/spring-cloud.git Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
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