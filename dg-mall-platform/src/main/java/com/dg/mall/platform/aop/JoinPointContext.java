/*
 *  Copyright 2015-2019 dg-mall.com Group.
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
package com.dg.mall.platform.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;

import java.lang.reflect.Method;

/**
 * join point context
 *
 * @author Benji
 * @date 2019-04-16
 */
public class JoinPointContext {

    private final JoinPoint joinPoint;

    private final Class<?> targetClass;

    private final Method method;

    private final Object[] args;

    public JoinPointContext(JoinPoint joinPoint) {
        this.joinPoint = joinPoint;
        targetClass = joinPoint.getTarget().getClass();
        method = MethodSignature.class.cast(joinPoint.getSignature()).getMethod();
        args = joinPoint.getArgs();
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArgs() {
        return args;
    }

}
