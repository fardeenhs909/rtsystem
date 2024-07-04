package com.company.rtsystem.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UserRoleService {
    public static final String ANONYMOUS_AUTHORTIY = "ROLE_ANONYMOUS";
}
