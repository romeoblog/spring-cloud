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
package com.cloud.mesh.core.rest.error;

import com.cloud.mesh.common.enums.ResultCode;
import com.cloud.mesh.common.model.ResultMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * The Rest Error Controller
 *
 * @author Benji
 * @date 2019-05-14
 */
@RestController
public class RestErrorController implements ErrorController {

    private final Logger logger = LoggerFactory.getLogger(RestErrorController.class);

    private static final String PATH = "/error";

    @Autowired
    private ErrorAttributes errorAttributes;


    @RequestMapping(value = PATH)
    @ResponseStatus(HttpStatus.OK)
    public ResultMsg noHandlerFoundException(HttpServletRequest request, Exception ex) {
        Map<String, Object> errors = this.getErrorAttributes(request, false);
        logger.error("RestErrorControllerError, ErrorMsg={}", errors);
//        if (StringUtils.isNotEmpty(RootContext.getXID())) {
//            throw new CommonException("The current is Global Transactional,The Error Exception has been thrown up.");
//        }
        return ResultMsg.error(ResultCode.NOT_FOUND_EXP);
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        WebRequest webRequest = new ServletWebRequest(request);
        return errorAttributes.getErrorAttributes(webRequest, includeStackTrace);
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

}
