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
package com.dg.mall.platform.exception;

/**
 * Error Sign Exception
 *
 * @author Benji
 * @date 2019-05-14
 */
public class ErrorSignException extends RuntimeException {

    private static final long serialVersionUID = 8247610319171014183L;

    public ErrorSignException(Throwable e) {
        super(e.getMessage(), e);
    }

    public ErrorSignException(String message) {
        super(message);
    }

}
