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

import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

/**
 * SQLRunner 封装
 *
 * @author willlu.zheng
 * @date 2019-09-17
 */
@SuppressWarnings("serial")
public class SQLRunner {

    static Logger logger = LoggerFactory.getLogger(SQLRunner.class);

    private final static QueryRunner runner = new QueryRunner();

    private final static RowProcessor camelCaseRowProcessor = new BasicRowProcessor(new GenerousBeanProcessor());

    /* 定义简单对象类型列表 */
    private final static List<Class<?>> PrimitiveClasses = new ArrayList<Class<?>>() {
        {
            add(Long.class);
            add(Double.class);
            add(Integer.class);
            add(String.class);
            add(java.util.Date.class);
            add(java.sql.Date.class);
            add(Timestamp.class);
        }
    };

    /**
     * 判断当前类型是否属于简单对象类型
     *
     * @param cls
     * @return
     */
    private final static boolean _IsPrimitive(Class<?> cls) {
        return cls.isPrimitive() || PrimitiveClasses.contains(cls);
    }


    /**
     * 普通查询封装(查询全部数据)
     * <p>
     * <strong>查询为JavaBean对象，采用为属性赋值方式进行设值(即JavaBean中属性必须包括setXX方法)</strong>
     * </p>
     * ResultSetHandler<T>可以为以下值:
     * <pre>
     * 1、简单类型查询 ScalarHandler<String> ScalarHandler<Integer>  ScalarHandler<Long> MapHandler BeanHandler
     * 2、列表对象查询
     * ScalarHandler(唯一值查询): Converts one ResultSet column into an Object.
     * ArrayHandler(单一Array查询): Converts the first ResultSet row into an Object[].
     * BeanHandler(单一Bean查询): Converts the first ResultSet row into a JavaBean.
     * MapHandler(单一Map查询): Converts the first ResultSet row into a Map.
     * ColumnListHandler(List集合查询): Converts a ResultSet into a List of Object.
     * ArrayListHandler(List集合查询): Converts the ResultSet into a List of Object[].
     * BeanListHandler(List集合查询): Converts a ResultSet into a List of beans.
     * MapListHandler(List集合查询): Converts a ResultSet into a List of Map.
     * BeanMapHandler(Map集合查询): Converts a ResultSet into a Map of beans.
     * KeyedHandler(Map集合查询): Returns a Map of Maps,ResultSet rows are converted into Maps which are then stored in a Map under the given key.
     * </pre>
     *
     * @param conn   连接对象
     * @param sql    查询SQL
     * @param rsh    ResultSet处理句柄
     * @param params 参数数组
     * @return
     * @throws SQLException
     */
    public static <T> T query(Connection conn, String sql, ResultSetHandler<T> rsh, Object... params) throws SQLException {
        logger.debug("RunSQL[{}], Params[{}]", sql, StringUtils.join(params, ","));
        long startTimes = System.currentTimeMillis();
        T results = runner.query(conn, sql, rsh, params);
        logger.debug("RunTime [{} ms]", System.currentTimeMillis() - startTimes);
        return results;
    }

    /**
     * 对象查询,查询惟一对象(属性名称直接转换)
     * <p>
     * <strong>查询为JavaBean对象，采用为属性赋值方式进行设值(即JavaBean中属性必须包括setXX方法)</strong>
     * </p>
     *
     * @param conn      连接对象
     * @param sql       查询SQL
     * @param beanClass 查询对象类型
     * @param params    SQL参数数组
     * @return
     * @throws SQLException
     */
    public static <T> T queryBean(Connection conn, String sql, Class<T> beanClass, Object... params) throws SQLException {
        logger.debug("RunSQL[{}], Params[{}]", sql, StringUtils.join(params, ","));
        long startTimes = System.currentTimeMillis();
        T entity = runner.query(conn, sql, new BeanHandler<T>(beanClass), params);
        logger.debug("RunTime [{} ms]", System.currentTimeMillis() - startTimes);
        return entity;
    }

    /**
     * 对象查询,查询惟一对象(属性名称按照驼峰规则转换)
     * <p>
     * <strong>查询为JavaBean对象，采用为属性赋值方式进行设值(即JavaBean中属性必须包括setXX方法)</strong>
     * </p>
     *
     * @param conn      连接对象
     * @param sql       查询SQL
     * @param beanClass 查询对象类型
     * @param params    SQL参数数组
     * @return
     * @throws SQLException
     */
    public static <T> T queryBeanWithCamelCase(Connection conn, String sql, Class<T> beanClass, Object... params) throws SQLException {
        logger.debug("RunSQL[{}], Params[{}]", sql, StringUtils.join(params, ","));
        long startTimes = System.currentTimeMillis();
        T entity = runner.query(conn, sql, new BeanHandler<T>(beanClass, camelCaseRowProcessor), params);
        logger.debug("RunTime [{} ms]", System.currentTimeMillis() - startTimes);
        return entity;
    }

    /**
     * 列表查询,查询列表对象(返回全部数据)
     * 支持 ColumnListHandler和BeanListHandler两种列表
     *
     * @param conn      连接对象
     * @param sql       查询SQL
     * @param beanClass 查询对象类型
     * @param params    SQL参数数组
     * @return
     * @throws SQLException
     */
    public static <T> List<T> queryBeanList(Connection conn, String sql, Class<T> beanClass, Object... params) throws SQLException {
        logger.debug("RunSQL[{}], Params[{}]", sql, StringUtils.join(params, ","));
        long startTimes = System.currentTimeMillis();
        List<T> results = runner.query(conn, sql, _IsPrimitive(beanClass) ? new ColumnListHandler<T>(beanClass) : new BeanListHandler<T>(beanClass), params);
        logger.debug("RunTime [{} ms]", System.currentTimeMillis() - startTimes);
        return results;
    }

    /**
     * 列表查询,查询列表对象(返回全部数据：采用驼峰规则转换属性名称)
     * 支持 ColumnListHandler和BeanListHandler两种列表
     *
     * @param conn      连接对象
     * @param sql       查询SQL
     * @param beanClass 查询对象类型
     * @param params    SQL参数数组
     * @return
     * @throws SQLException
     */
    public static <T> List<T> queryBeanListWithCamelCase(Connection conn, String sql, Class<T> beanClass, Object... params) throws SQLException {
        logger.debug("RunSQL[{}], Params[{}]", sql, StringUtils.join(params, ","));
        long startTimes = System.currentTimeMillis();
        List<T> results = runner.query(conn, sql, _IsPrimitive(beanClass) ? new ColumnListHandler<T>(beanClass) : new BeanListHandler<T>(beanClass, camelCaseRowProcessor), params);
        logger.debug("RunTime [{} ms]", System.currentTimeMillis() - startTimes);
        return results;
    }

    /**
     * Map列表查询,查询Map列表对象
     * <p>
     * 仅支持 ColumnListHandler和BeanListHandler两种列表
     *
     * @param conn   连接对象
     * @param sql    查询SQL
     * @param params SQL参数数组
     * @return
     * @throws SQLException
     */
    public static List<Map<String, Object>> queryMapList(Connection conn, String sql, Object... params) throws SQLException {
        logger.debug("RunSQL[{}], Params[{}]", sql, StringUtils.join(params, ","));
        long startTimes = System.currentTimeMillis();
        List<Map<String, Object>> results = runner.query(conn, sql, new MapListHandler(), params);
        logger.debug("RunTime [{} ms]", System.currentTimeMillis() - startTimes);
        return results;
    }

    /**
     * Map列表查询,查询Map列表对象(采用驼峰规则进行转换属性名)
     * <p>
     * 仅支持 ColumnListHandler和BeanListHandler两种列表
     *
     * @param conn   连接对象
     * @param sql    查询SQL
     * @param params SQL参数数组
     * @return
     * @throws SQLException
     */
    public static List<Map<String, Object>> queryMapListWithCamelCase(Connection conn, String sql, Object... params) throws SQLException {
        logger.debug("RunSQL[{}], Params[{}]", sql, StringUtils.join(params, ","));
        long startTimes = System.currentTimeMillis();
        List<Map<String, Object>> results = runner.query(conn, sql, new MapListHandler(camelCaseRowProcessor), params);
        logger.debug("RunTime [{} ms]", System.currentTimeMillis() - startTimes);
        return results;
    }

    /**
     * 数组列表查询,查询数组列表对象(支持分页和全部查询)
     *
     * @param conn   连接对象
     * @param sql    查询SQL
     * @param params SQL参数数组
     * @return
     * @throws SQLException
     */
    public static List<Object[]> queryArrayList(Connection conn, String sql, Object... params) throws SQLException {
        logger.debug("RunSQL[{}], Params[{}]", sql, StringUtils.join(params, ","));
        long startTimes = System.currentTimeMillis();
        List<Object[]> results = runner.query(conn, sql, new ArrayListHandler(), params);
        logger.debug("RunTime [{} ms]", System.currentTimeMillis() - startTimes);
        return results;
    }

    /**
     * 返回Map信息(单KEY值)
     *
     * @param conn
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public static <K, V> Map<K, V> queryKVMap(Connection conn, String sql, KVMapHandler<K, V> rsh, Object... params) throws SQLException {
        return SQLRunner.query(conn, sql, rsh, params);
    }


    /**
     * 返回Map信息(多KEY值, 默认以"~"作为多KEY的分隔符)
     *
     * @param conn
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public static <V> Map<String, V> queryMultiKVMap(Connection conn, String sql, MultiKVMapHandler<V> rsh, Object... params) throws SQLException {
        return SQLRunner.query(conn, sql, rsh, params);
    }

    /**
     * 返回Map信息(多KEY值, 以指定符号作为多KEY的分隔符)
     *
     * @param conn
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public static <V> Map<String, V> queryMultiKVMap(Connection conn, String sql, String delimiter, MultiKVMapHandler<V> rsh, Object... params) throws SQLException {
        rsh.setDelimiter(delimiter);
        return SQLRunner.query(conn, sql, rsh, params);
    }


    /**
     * 执行统计查询语句，语句的执行结果必须只返回一个数值
     *
     * @param conn   连接对象
     * @param sql    查询SQL
     * @param params 参数数组
     * @return
     * @throws SQLException
     */
    public static long stat(Connection conn, String sql, Object... params) throws SQLException {
        logger.debug("RunSQL[{}], Params[{}]", sql, StringUtils.join(params, ","));
        long startTimes = System.currentTimeMillis();
        Long result = (Long) runner.query(conn, sql, new ScalarHandler(Long.class), params);
        logger.debug("RunTime [{} ms]", System.currentTimeMillis() - startTimes);
        return (result != null) ? result : -1;
    }

    /**
     * 执行INSERT/UPDATE/DELETE语句
     *
     * @param conn   连接对象
     * @param sql    查询SQL
     * @param params 参数数组
     * @return
     * @throws SQLException
     */
    public static int execute(Connection conn, String sql, Object... params) throws SQLException {
        logger.debug("RunSQL[{}], Params[{}]", sql, StringUtils.join(params, ","));
        long startTimes = System.currentTimeMillis();
        int result = runner.update(conn, sql, params);
        logger.debug("RunTime [{} ms]", System.currentTimeMillis() - startTimes);
        return result;
    }

    /**
     * 批量执行指定的SQL语句,包括INSERT/UPDATE/DELETE
     *
     * @param conn   连接对象
     * @param sql    查询SQL
     * @param params 参数二维数组
     * @return
     * @throws SQLException
     */
    public static int[] executeBatch(Connection conn, String sql, Object[][] params) throws SQLException {
        logger.debug("RunSQL[{}]", sql);
        long startTimes = System.currentTimeMillis();
        int[] results = runner.batch(conn, sql, params);
        logger.debug("RunTime [{} ms]", System.currentTimeMillis() - startTimes);
        return results;
    }

    /**
     * 执行单条插入操作
     *
     * @param conn     连接对象
     * @param table    插入表名
     * @param valueMap 值集合
     * @return
     * @throws SQLException
     */
    public static int insert(Connection conn, String table, Map<String, Object> valueMap) throws SQLException {
        if (valueMap == null || valueMap.size() < 1) {
            throw new SQLException("Null parameters. If parameters aren't need, pass an empty valueMap.");
        }
        Object[] params = new Object[]{};

        for (Iterator<String> it = valueMap.keySet().iterator(); it.hasNext(); ) {
            params = ArrayUtils.add(params, valueMap.get(it.next()));
        }

        String insertSQL = buildInsertSQLByMap(table, valueMap);
        logger.debug("RunSQL[{}]", insertSQL);
        long startTimes = System.currentTimeMillis();
        int result = runner.update(conn, insertSQL, params);
        logger.debug("RunTime [{} ms]", System.currentTimeMillis() - startTimes);

        return result;
    }

    /**
     * 执行批量执行插入操作
     *
     * @param conn         连接对象
     * @param table        插入表名
     * @param valueMapList 值集合列表
     * @return
     * @throws SQLException
     */
    public static int[] insertBatch(Connection conn, String table, List<Map<String, Object>> valueMapList) throws SQLException {
        if (valueMapList == null || valueMapList.size() < 1) {
            throw new SQLException("Null parameters. If parameters aren't need, pass an empty valueMapList.");
        }
        String insertSQL = buildInsertSQLByMap(table, valueMapList.get(0));

        Object[][] params = new Object[valueMapList.size()][];
        int index = 0;
        for (Map<String, Object> valueMap : valueMapList) {
            Object[] thparams = new Object[]{};
            for (Iterator<String> it = valueMap.keySet().iterator(); it.hasNext(); ) {
                thparams = ArrayUtils.add(thparams, valueMap.get(it.next()));
            }
            params[index++] = thparams;
        }

        logger.debug("RunSQL[{}]", insertSQL);
        long startTimes = System.currentTimeMillis();
        int[] results = runner.batch(conn, insertSQL, params);
        logger.debug("RunTime [{} ms]", System.currentTimeMillis() - startTimes);

        return results;
    }


    /**
     * 执行单条删除操作
     *
     * @param conn         连接对象
     * @param table        删除记录表名
     * @param conditionMap 条件集合
     * @return
     * @throws SQLException
     */
    public static int delete(Connection conn, String table, Map<String, Object> conditionMap) throws SQLException {
        if (conditionMap == null || conditionMap.size() < 1) {
            throw new SQLException("Null parameters. If parameters aren't need, pass an empty conditionMap.");
        }
        Object[] params = new Object[]{};
        for (Iterator<String> it = conditionMap.keySet().iterator(); it.hasNext(); ) {
            params = ArrayUtils.add(params, conditionMap.get(it.next()));
        }

        String deleteSQL = buildDeleteSQLByMap(table, conditionMap);

        logger.debug("RunSQL[{}]", deleteSQL);
        long startTimes = System.currentTimeMillis();
        int result = runner.update(conn, deleteSQL, params);
        logger.debug("RunTime [{} ms]", System.currentTimeMillis() - startTimes);

        return result;
    }

    /**
     * 执行批量删除操作(批量删除)
     *
     * @param conn             连接对象
     * @param table            删除表名
     * @param conditionMapList 条件集合列表(集合属性相同)
     * @return
     * @throws SQLException
     */
    public static int[] deleteBatch(Connection conn, String table, List<Map<String, Object>> conditionMapList) throws SQLException {
        if (conditionMapList == null || conditionMapList.size() < 1) {
            throw new SQLException("Null parameters. If parameters aren't need, pass an empty conditionMapList.");
        }
        String deleteSQL = buildDeleteSQLByMap(table, conditionMapList.get(0));

        Object[][] params = new Object[conditionMapList.size()][];
        int index = 0;
        for (Map<String, Object> conditionMap : conditionMapList) {
            Object[] thparams = new Object[]{};
            for (Iterator<String> it = conditionMap.keySet().iterator(); it.hasNext(); ) {
                thparams = ArrayUtils.add(thparams, conditionMap.get(it.next()));
            }
            params[index++] = thparams;
        }

        logger.debug("RunSQL[{}]", deleteSQL);
        long startTimes = System.currentTimeMillis();
        int[] results = runner.batch(conn, deleteSQL, params);
        logger.debug("RunTime [{} ms]", System.currentTimeMillis() - startTimes);

        return results;
    }

    /**
     * 执行单条删除操作
     *
     * @param conn     数据库连接
     * @param table    删除数据表名
     * @param valueMap 属性集合
     * @param keys     按Keys进行删除
     * @return
     * @throws SQLException
     */
    public static int delete(Connection conn, String table, Map<String, Object> valueMap, String... keys) throws SQLException {
        if (valueMap == null || valueMap.size() < 1) {
            throw new SQLException("Null parameters. If parameters aren't need, pass an empty valueMap.");
        }
        if (keys == null || keys.length < 1) {
            throw new SQLException("Null parameters. If parameters aren't need, pass an empty keys.");
        }
        Object[] params = new Object[]{};
        for (String key : keys) {
            params = ArrayUtils.add(params, valueMap.get(key));
        }

        String deleteSQL = buildDeleteSQLByKeys(table, keys);
        logger.debug("RunSQL[{}]", deleteSQL);
        long startTimes = System.currentTimeMillis();
        int result = runner.update(conn, deleteSQL, params);
        logger.debug("RunTime [{} ms]", System.currentTimeMillis() - startTimes);

        return result;
    }

    /**
     * 执行批量删除操作(批量删除)
     *
     * @param conn         数据库连接
     * @param table        删除记录表名
     * @param valueMapList 属性集合列表
     * @param keys         按Keys进行删除
     * @return
     * @throws SQLException
     */
    public static int[] deleteBatch(Connection conn, String table, List<Map<String, Object>> valueMapList, String... keys) throws SQLException {
        if (valueMapList == null || valueMapList.size() < 1) {
            throw new SQLException("Null parameters. If parameters aren't need, pass an empty valueMapList.");
        }
        if (keys == null || keys.length < 1) {
            throw new SQLException("Null parameters. If parameters aren't need, pass an empty keys.");
        }

        Object[][] params = new Object[valueMapList.size()][];
        int index = 0;
        for (Map<String, Object> valueMap : valueMapList) {
            Object[] thparams = new Object[]{};
            for (String key : keys) {
                thparams = ArrayUtils.add(thparams, valueMap.get(key));
            }
            params[index++] = thparams;
        }

        String deleteSQL = buildDeleteSQLByKeys(table, keys);
        logger.debug("RunSQL[{}]", deleteSQL);
        long startTimes = System.currentTimeMillis();
        int[] results = runner.batch(conn, deleteSQL, params);
        logger.debug("RunTime [{} ms]", System.currentTimeMillis() - startTimes);

        return results;
    }

    /**
     * 执行单条更新操作
     *
     * @param conn     连接对象
     * @param table    更新表名
     * @param valueMap 更新属性集合
     * @param keys     按keys字段进行更新
     * @return
     * @throws SQLException
     */
    public static int update(Connection conn, String table, Map<String, Object> valueMap, String... keys) throws SQLException {
        if (valueMap == null || valueMap.size() < 1) {
            throw new SQLException("Null parameters. If parameters aren't need, pass an empty valueMap.");
        }
        if (keys == null || keys.length < 1) {
            throw new SQLException("Null parameters. If parameters aren't need, pass an empty keys.");
        }
        String updataSQL = buildUpdateSQLByKeys(table, valueMap, keys);
        Object[] params = new Object[]{};
        for (Iterator<String> it = valueMap.keySet().iterator(); it.hasNext(); ) {
            params = ArrayUtils.add(params, valueMap.get(it.next()));
        }
        for (String key : keys) {
            params = ArrayUtils.add(params, valueMap.get(key));
        }

        logger.debug("RunSQL[{}]", updataSQL);
        long startTimes = System.currentTimeMillis();
        int result = runner.update(conn, updataSQL, params);
        logger.debug("RunTime [{} ms]", System.currentTimeMillis() - startTimes);

        return result;
    }

    /**
     * 执行批量更新操作
     *
     * @param conn         连接对象
     * @param table        更新表名
     * @param valueMapList 更新属性集合
     * @param keys         按keys字段进行更新
     * @return
     * @throws SQLException
     */
    public static int[] updateBatch(Connection conn, String table, List<Map<String, Object>> valueMapList, String... keys) throws SQLException {
        if (valueMapList == null || valueMapList.size() < 1) {
            throw new SQLException("Null parameters. If parameters aren't need, pass an empty valueMapList.");
        }
        if (keys == null || keys.length < 1) {
            throw new SQLException("Null parameters. If parameters aren't need, pass an empty keys.");
        }

        Map<String, Object> tmpValueMap = valueMapList.get(0);

        String updataSQL = buildUpdateSQLByKeys(table, tmpValueMap, keys);
        String[] cols = new String[]{};
        for (Iterator<String> it = tmpValueMap.keySet().iterator(); it.hasNext(); ) {
            cols = (String[]) ArrayUtils.add(cols, it.next());
        }
        cols = (String[]) ArrayUtils.addAll(cols, keys);

        Object[][] params = new Object[valueMapList.size()][];
        int index = 0;
        for (Map<String, Object> valueMap : valueMapList) {
            Object[] thparams = new Object[]{};
            for (String col : cols) {
                thparams = ArrayUtils.add(thparams, valueMap.get(col));
            }
            params[index++] = thparams;
        }

        logger.debug("RunSQL[{}]", updataSQL);
        long startTimes = System.currentTimeMillis();
        int[] results = runner.batch(conn, updataSQL, params);
        logger.debug("RunTime [{} ms]", System.currentTimeMillis() - startTimes);

        return results;
    }


    /**
     * 执行单条更新操作
     *
     * @param conn       连接对象
     * @param table      更新表名
     * @param valueMap   更新属性集合
     * @param updateCols 准备更新列
     * @param keys       按keys字段进行更新
     * @return
     * @throws SQLException
     */
    public static int update(Connection conn, String table, Map<String, Object> valueMap, String[] updateCols, String... keys) throws SQLException {
        if (valueMap == null || valueMap.size() < 1) {
            throw new SQLException("Null parameters. If parameters aren't need, pass an empty valueMap.");
        }
        if (updateCols == null || updateCols.length < 1) {
            throw new SQLException("Null parameters. If parameters aren't need, pass an empty updateCols.");
        }
        if (keys == null || keys.length < 1) {
            throw new SQLException("Null parameters. If parameters aren't need, pass an empty keys.");
        }
        String updataSQL = buildUpdateSQLByUpdateColsAndKeys(table, updateCols, keys);
        Object[] params = new Object[]{};
        for (String updateCol : updateCols) {
            params = ArrayUtils.add(params, valueMap.get(updateCol));
        }
        for (String key : keys) {
            params = ArrayUtils.add(params, valueMap.get(key));
        }

        logger.debug("RunSQL[{}]", updataSQL);
        long startTimes = System.currentTimeMillis();
        int result = runner.update(conn, updataSQL, params);
        logger.debug("RunTime [{} ms]", System.currentTimeMillis() - startTimes);

        return result;
    }

    /**
     * 执行批量更新操作
     *
     * @param conn         连接对象
     * @param table        更新表名
     * @param valueMapList 更新属性集合
     * @param updateCols   准备更新列
     * @param keys         按keys字段进行更新
     * @return
     * @throws SQLException
     */
    public static int[] updateBatch(Connection conn, String table, List<Map<String, Object>> valueMapList, String[] updateCols, String... keys) throws SQLException {
        if (valueMapList == null || valueMapList.size() < 1) {
            throw new SQLException("Null parameters. If parameters aren't need, pass an empty valueMapList.");
        }
        if (keys == null || keys.length < 1) {
            throw new SQLException("Null parameters. If parameters aren't need, pass an empty keys.");
        }
        String updataSQL = buildUpdateSQLByUpdateColsAndKeys(table, updateCols, keys);

        Object[][] params = new Object[valueMapList.size()][];
        int index = 0;
        for (Map<String, Object> valueMap : valueMapList) {
            Object[] thparams = new Object[]{};
            for (String updateCol : updateCols) {
                thparams = ArrayUtils.add(thparams, valueMap.get(updateCol));
            }
            for (String key : keys) {
                thparams = ArrayUtils.add(thparams, valueMap.get(key));
            }
            params[index++] = thparams;
        }

        logger.debug("RunSQL[{}]", updataSQL);
        long startTimes = System.currentTimeMillis();
        int[] results = runner.batch(conn, updataSQL, params);
        logger.debug("RunTime [{} ms]", System.currentTimeMillis() - startTimes);

        return results;
    }


    /**
     * 构造插入SQL语句
     *
     * @param table    插入表名
     * @param valueMap 按属性集合进行构造插入SQL
     * @return
     */
    private static String buildInsertSQLByMap(String table, Map<String, Object> valueMap) {
        StringBuffer tabColName = new StringBuffer();
        tabColName.append(" INSERT INTO ").append(table).append("(");

        StringBuffer tabColValue = new StringBuffer();
        tabColValue.append(" VALUES (");

        for (Iterator<String> it = valueMap.keySet().iterator(); it.hasNext(); ) {
            String colName = it.next();
            tabColName.append(colName).append(", ");
            tabColValue.append("?, ");
        }
        tabColName.delete(tabColName.length() - 2, tabColName.length());
        tabColName.append(")");
        tabColValue.delete(tabColValue.length() - 2, tabColValue.length());
        tabColValue.append(")");

        return tabColName.toString() + tabColValue.toString();
    }

    /**
     * 构造删除SQL语句
     *
     * @param table        删除记录表名
     * @param conditionMap 按属性集合进行构造删除SQL
     * @return
     */
    private static String buildDeleteSQLByMap(String table, Map<String, Object> conditionMap) {
        StringBuffer deleteSQL = new StringBuffer();
        deleteSQL.append(" DELETE FROM ").append(table);
        deleteSQL.append(" WHERE ");
        for (Iterator<String> it = conditionMap.keySet().iterator(); it.hasNext(); ) {
            String name = it.next();
            deleteSQL.append(name).append(" = ? AND ");
        }
        deleteSQL.delete(deleteSQL.length() - 4, deleteSQL.length());

        return deleteSQL.toString();
    }

    /**
     * 构造删除SQL语句
     *
     * @param table 删除记录表名
     * @param keys  按keys进行构造删除SQL
     * @return
     */
    private static String buildDeleteSQLByKeys(String table, String... keys) {
        StringBuffer deleteSQL = new StringBuffer();
        deleteSQL.append(" DELETE FROM ").append(table);
        deleteSQL.append(" WHERE ");
        for (String key : keys) {
            deleteSQL.append(key).append(" = ? AND ");
        }
        deleteSQL.delete(deleteSQL.length() - 4, deleteSQL.length());
        return deleteSQL.toString();
    }

    /**
     * 构造更新SQL语句
     *
     * @param table    更新表名
     * @param valueMap 更新值集合
     * @param keys     按keys进行更新
     * @return
     */
    private static String buildUpdateSQLByKeys(String table, Map<String, Object> valueMap, String... keys) {
        StringBuffer updateSQL = new StringBuffer();
        updateSQL.append(" UPDATE ");
        updateSQL.append(table);
        updateSQL.append(" SET ");
        for (Iterator<String> it = valueMap.keySet().iterator(); it.hasNext(); ) {
            updateSQL.append(it.next()).append(" = ?, ");
        }
        updateSQL.delete(updateSQL.length() - 2, updateSQL.length());
        updateSQL.append(" WHERE ");
        for (String key : keys) {
            updateSQL.append(key).append(" = ? ").append(" AND ");
        }
        updateSQL.delete(updateSQL.length() - 4, updateSQL.length());
        return updateSQL.toString();
    }

    /**
     * 构造更新SQL语句
     *
     * @param table      更新表名
     * @param updateCols 准备更新列
     * @param keys       按keys进行更新
     * @return
     */
    private static String buildUpdateSQLByUpdateColsAndKeys(String table, String[] updateCols, String... keys) {
        StringBuffer updateSQL = new StringBuffer();
        updateSQL.append(" UPDATE ");
        updateSQL.append(table);
        updateSQL.append(" SET ");
        for (String updateCol : updateCols) {
            updateSQL.append(updateCol).append(" = ?, ");
        }
        updateSQL.delete(updateSQL.length() - 2, updateSQL.length());
        updateSQL.append(" WHERE ");
        for (String key : keys) {
            updateSQL.append(key).append(" = ? ").append(" AND ");
        }
        updateSQL.delete(updateSQL.length() - 4, updateSQL.length());
        return updateSQL.toString();
    }


    /**
     * 结果集处理扩展：
     * 增加结果集为Map<key, value>类型处理, 默认处理第一列为KEY, 第二列为VALUE
     *
     * @param <K>
     * @param <V>
     * @author LIUKANGJIN
     */
    public static class KVMapHandler<K, V> implements ResultSetHandler<Map<K, V>> {

        private final int keyIndex;

        private final Class<K> keyType;

        private final int valueIndex;

        private final Class<V> valueType;

        /**
         * 返回Key/Value的Map集合信息
         * 默认取值：keyIdx = 1, valueIndex = 2
         */
        public KVMapHandler(Class<K> keyType, Class<V> valueType) {
            this(1, keyType, 2, valueType);
        }

        public KVMapHandler(int keyIndex, Class<K> keyType, int valueIndex, Class<V> valueType) {
            this.keyIndex = keyIndex;
            this.keyType = keyType;
            this.valueIndex = valueIndex;
            this.valueType = valueType;
        }

        @Override
        public Map<K, V> handle(ResultSet rs) throws SQLException {
            Map<K, V> results = new HashMap<K, V>();
            while (rs.next()) {
                results.put(this.createKey(rs), this.createValue(rs));
            }
            return results;
        }

        public K createKey(ResultSet rs) throws SQLException {
            return convert(rs, keyIndex, keyType);
        }

        public V createValue(ResultSet rs) throws SQLException {
            return convert(rs, valueIndex, valueType);
        }

    }


    /**
     * 结果集处理扩展：
     * 增加结果集为Map<key, value>类型处理, 默认处理最后一列的前面所有列构造KEY, 最后一列为VALUE
     *
     * @param <V>
     */
    public static class MultiKVMapHandler<V> implements ResultSetHandler<Map<String, V>> {

        private final Class<V> valueType;

        private String delimiter;

        /**
         * 返回Key/Value的Map集合信息
         */
        public MultiKVMapHandler(Class<V> valueType) {
            this(valueType, "~");
        }

        public MultiKVMapHandler(Class<V> valueType, String delimiter) {
            this.valueType = valueType;
            this.delimiter = delimiter;
        }

        @Override
        public Map<String, V> handle(ResultSet rs) throws SQLException {
            Map<String, V> results = new HashMap<String, V>();
            while (rs.next()) {
                results.put(this.createKey(rs), this.createValue(rs));
            }
            return results;
        }

        public String createKey(ResultSet rs) throws SQLException {
            int columnCount = rs.getMetaData().getColumnCount();
            StringBuffer keyBuf = new StringBuffer();
            for (int index = 1; index <= columnCount - 1; index++) {
                Object keyObj = rs.getObject(index);
                if (keyObj instanceof java.util.Date) {
                    keyBuf.append(DateFormatUtils.format((java.util.Date) keyObj, DateFormatUtils.YYYY_MM_DD_HH_MM_SS));
                } else {
                    keyBuf.append(keyObj);
                }
                if (index < columnCount - 1) {
                    keyBuf.append(delimiter);
                }
            }
            return keyBuf.toString();
        }

        public V createValue(ResultSet rs) throws SQLException {
            int columnCount = rs.getMetaData().getColumnCount();
            return convert(rs, columnCount, valueType);
        }

        public String getDelimiter() {
            return delimiter;
        }

        public void setDelimiter(String delimiter) {
            this.delimiter = delimiter;
        }

    }

    /**
     * 结果集扩展
     * 增加结果类型如：List<String>类型的结果查询
     *
     * @param <T>
     */
    public static class ColumnListHandler<T> extends AbstractListHandler<T> {

        private final Class<T> type;

        private final int columnIndex;

        public ColumnListHandler(Class<T> type) {
            this(type, 1);
        }

        public ColumnListHandler(Class<T> type, int columnIndex) {
            this.type = type;
            this.columnIndex = columnIndex;
        }

        @Override
        protected T handleRow(ResultSet rs) throws SQLException {
            return convert(rs, columnIndex, type);
        }
    }

    /**
     * 结果集扩展
     * 唯一值查询, 如统计结果select count(*) from table
     *
     * @param <T>
     */
    public static class ScalarHandler<T> implements ResultSetHandler<T> {

        private final Class<T> type;

        private final int columnIndex;

        public ScalarHandler(Class<T> type) {
            this(type, 1);
        }

        public ScalarHandler(Class<T> type, int columnIndex) {
            this.type = type;
            this.columnIndex = columnIndex;
        }

        @Override
        public T handle(ResultSet rs) throws SQLException {
            return rs.next() ? convert(rs, columnIndex, type) : null;
        }

    }

    private static <T> T convert(ResultSet rs, int columnIndex, Class<T> toType) throws SQLException {
        if (rs.getObject(columnIndex) == null) {
            return null;
        } else if (toType.equals(String.class)) {
            return (T) rs.getString(columnIndex);

        } else if (toType.equals(Integer.TYPE) || toType.equals(Integer.class)) {
            return (T) Integer.valueOf(rs.getInt(columnIndex));

        } else if (toType.equals(Boolean.TYPE) || toType.equals(Boolean.class)) {
            return (T) Boolean.valueOf(rs.getBoolean(columnIndex));

        } else if (toType.equals(Long.TYPE) || toType.equals(Long.class)) {
            return (T) Long.valueOf(rs.getLong(columnIndex));

        } else if (toType.equals(Double.TYPE) || toType.equals(Double.class)) {
            return (T) Double.valueOf(rs.getDouble(columnIndex));

        } else if (toType.equals(Float.TYPE) || toType.equals(Float.class)) {
            return (T) Float.valueOf(rs.getFloat(columnIndex));

        } else if (toType.equals(Short.TYPE) || toType.equals(Short.class)) {
            return (T) Short.valueOf(rs.getShort(columnIndex));

        } else if (toType.equals(Byte.TYPE) || toType.equals(Byte.class)) {
            return (T) Byte.valueOf(rs.getByte(columnIndex));

        } else if (toType.equals(Timestamp.class)) {
            return (T) rs.getTimestamp(columnIndex);

        } else if (toType.equals(SQLXML.class)) {
            return (T) rs.getSQLXML(columnIndex);

        } else {
            return ConvertUtils.convert(rs.getObject(columnIndex), toType);
        }
    }

}
