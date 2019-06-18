package com.cloud.example.auth.controller;

import com.cloud.example.auth.entity.PermissionDTO;
import com.cloud.example.auth.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 权限控制层
 *
 * @author Benji
 * @date 2019-06-17
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    @PostMapping("/checkPermission")
    public Boolean checkPermission(@RequestBody PermissionDTO permissionDTO) {
        return permissionService.checkPermission(permissionDTO);
    }

}
