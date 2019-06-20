package com.cloud.example.service.mybatis.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * MYBATIS栗子 控制层
 *
 * @author Benji
 * @date 2019-06-20
 */
@RestController
@RequestMapping("/mybatis")
@Slf4j
@Api(value = "MybatisController", description = "MYBATIS栗子")
public class MybatisController {
}
