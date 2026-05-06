package com.learning.admin.service;

import com.learning.common.dto.*;
import java.util.List;

public interface AdminService {

    UserResponse addUser(UserRequest request);

    TemporaryAccessResponse grantTemporaryAccess(
                            TemporaryAccessRequest
                            request);

    void revokeTemporaryAccess(Long accessId);

    List<EmployeeResponse> getAllEmployees();

    List<ManagerResponse> getAllManagers();

    List<CsmResponse> getAllCsms();
}
