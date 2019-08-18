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
package com.cloud.mesh.auth.service.impl;

import com.cloud.mesh.auth.mapper.PermissionMapperExt;
import com.cloud.mesh.auth.service.IUserDetailService;
import com.cloud.mesh.entity.TbPermission;
import com.cloud.mesh.entity.TbUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自定义认证授权实现类
 *
 * @author Benji
 * @date 2019-06-07
 */
@Slf4j
@Service
public class UserDetailService2Impl implements IUserDetailService {

    @Autowired
    private PermissionMapperExt permissionMapperExt;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        TbUser user = new TbUser().selectOne("username = {0}", userName);

        if (user == null) {
            throw new UsernameNotFoundException("用户名[" + userName + "]不存在！");
        }

        List<TbPermission> permissions = permissionMapperExt.listPermission(user.getId());
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        for (TbPermission permission : permissions) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getEnname());

            grantedAuthorities.add(grantedAuthority);

        }

        return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
