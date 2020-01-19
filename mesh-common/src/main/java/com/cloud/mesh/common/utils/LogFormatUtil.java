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
package com.cloud.mesh.common.utils;

/**
 * The type log format util
 *
 * @author willlu.zheng
 * @date 2019-09-26
 */
public class LogFormatUtil {

    private static final String PLACE_HOLDER = "{}";

    private static final String METHOD = "Method:";

    private static final String PARAMS = "Param:";

    private static final String RESULT = "Result:";

    private static final String ERROR = "Error:";

    private static final String SEPARATOR = " , ";

    private static final String BEGIN = "==== ";

    private static final String END = " ====";

    private static String defaultInputFormat = BEGIN + METHOD + PLACE_HOLDER + SEPARATOR + PARAMS + PLACE_HOLDER + END;

    private static String defaultOutputFormat = BEGIN + METHOD + PLACE_HOLDER + SEPARATOR + RESULT + PLACE_HOLDER + END;

    private static String defaultErrorFormat = BEGIN + METHOD + PLACE_HOLDER + SEPARATOR + ERROR + PLACE_HOLDER + END;


    public static String getDefaultInputFormat() {
        return getDefaultInputFormat(1);
    }

    public static String getDefaultOutputFormat() {
        return defaultOutputFormat;
    }

    public static String getDefaultErrorFormat() {
        return defaultErrorFormat;
    }

    public static String getDefaultInputFormat(int count) {

        if (count < 1) {
            return defaultInputFormat;
        }

        StringBuffer sb = new StringBuffer();

        sb.append(BEGIN).append(METHOD).append(PLACE_HOLDER).append(SEPARATOR).append(PARAMS);

        for (int i = 0; i < count; i++) {
            if (i != count - 1) {
                sb.append(PLACE_HOLDER).append(SEPARATOR);
            } else {
                sb.append(PLACE_HOLDER);
            }
        }

        sb.append(END);

        return sb.toString();
    }

    public static void main(String... args) {

        System.out.println(getDefaultInputFormat(0));

        System.out.println(getDefaultOutputFormat());

        System.out.println(getDefaultErrorFormat());
    }
}
