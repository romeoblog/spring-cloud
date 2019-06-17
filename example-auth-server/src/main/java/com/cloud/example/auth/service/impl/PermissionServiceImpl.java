package com.cloud.example.auth.service.impl;

import com.cloud.example.auth.service.IPermissionService;
import org.springframework.stereotype.Service;

/**
 * 权限检查 实现类
 *
 * @author Benji
 * @date 2019-06-17
 */
@Service
public class PermissionServiceImpl implements IPermissionService {

    @Override
    public Boolean checkSign() {
        return null;
    }

    @Override
    public Boolean checkToken() {
        return null;
    }
}
