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
package com.cloud.mesh.service.mybatis;

import com.cloud.mesh.core.solver.AbstractInspectionSolver;
import com.cloud.mesh.core.solver.InspectionSolverChooser;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class InspectionTest {

    @Autowired
    private InspectionSolverChooser chooser;

    @Test
    public void test() {
        String key = "Default:Key";
        AbstractInspectionSolver solver = chooser.choose(key);
        if (solver == null) {
            throw new RuntimeException("Not Fount Key.");
        }
        Map<String,Object> p = Maps.newHashMap();
        p.put("myKey1","myValue1");
        p.put("myKey2","myValue2");
        solver.solve(p);
    }

}