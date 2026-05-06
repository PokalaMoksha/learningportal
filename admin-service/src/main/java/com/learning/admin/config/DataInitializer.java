package com.learning.admin.config;

import com.learning.admin.client.*;
import com.learning.common.dto.*;
import com.learning.common.enums.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer
       implements CommandLineRunner {

    private final AuthClient authClient;
    private final EmployeeClient employeeClient;
    private final ManagerClient managerClient;
    private final CsmClient csmClient;

    @Override
    public void run(String... args)
                throws Exception {

        if (isDataAlreadyLoaded()) {
            log.info("Data already exists!" +
                     " Skipping! ✅");
            return;
        }

        log.info("Starting data " +
                 "initialization...");

        try {

            // ── Admins ────────────────────────

            createUser("karthik",
                       "karthik@learning.com",
                       Role.ADMIN);

            createUser("manikanta",
                       "manikanta@learning.com",
                       Role.ADMIN);

            log.info("Admins created! ✅");

            // ── L&D ──────────────────────────

            createUser("dhana",
                       "dhana@learning.com",
                       Role.LD);

            createUser("jyotsna",
                       "jyotsna@learning.com",
                       Role.LD);

            createUser("arvind",
                       "arvind@learning.com",
                       Role.LD);

            log.info("L&D created! ✅");

            // ── CSMs ──────────────────────────

            CsmResponse santhosh =
                createCsm("santhosh",
                          "santhosh@learning.com",
                          "CSM001");

            CsmResponse raju =
                createCsm("raju",
                          "raju@learning.com",
                          "CSM002");

            CsmResponse ravi =
                createCsm("ravi",
                          "ravi@learning.com",
                          "CSM003");

            log.info("CSMs created! ✅");

            // ── Managers under Santhosh ───────

            ManagerResponse abdul =
                createManager(
                    "abdul",
                    "abdul@learning.com",
                    "MGR001",
                    santhosh.getId());

            ManagerResponse ram =
                createManager(
                    "ram",
                    "ram@learning.com",
                    "MGR002",
                    santhosh.getId());

            ManagerResponse sowjanya =
                createManager(
                    "sowjanya",
                    "sowjanya@learning.com",
                    "MGR003",
                    santhosh.getId());

            // ── Managers under Raju ───────────

            ManagerResponse sailesh =
                createManager(
                    "sailesh",
                    "sailesh@learning.com",
                    "MGR004",
                    raju.getId());

            ManagerResponse yamini =
                createManager(
                    "yamini",
                    "yamini@learning.com",
                    "MGR005",
                    raju.getId());

            ManagerResponse uday =
                createManager(
                    "uday",
                    "uday@learning.com",
                    "MGR006",
                    raju.getId());

            // ── Managers under Ravi ───────────

            ManagerResponse jagadeesh =
                createManager(
                    "jagadeesh",
                    "jagadeesh@learning.com",
                    "MGR007",
                    ravi.getId());

            ManagerResponse vicky =
                createManager(
                    "vicky",
                    "vicky@learning.com",
                    "MGR008",
                    ravi.getId());

            ManagerResponse sravya =
                createManager(
                    "sravya",
                    "sravya@learning.com",
                    "MGR009",
                    ravi.getId());

            log.info("Managers created! ✅");

            // ── Team 1 (Abdul) ────────────────

            createEmployee(
                "moksha",
                "moksha@learning.com",
                "EMP001",
                abdul.getId());

            createEmployee(
                "asha",
                "asha@learning.com",
                "EMP002",
                abdul.getId());

            createEmployee(
                "venu",
                "venu@learning.com",
                "EMP003",
                abdul.getId());

            // ── Team 2 (Ram) ──────────────────

            createEmployee(
                "swapna",
                "swapna@learning.com",
                "EMP004",
                ram.getId());

            createEmployee(
                "chetana",
                "chetana@learning.com",
                "EMP005",
                ram.getId());

            createEmployee(
                "harshitha",
                "harshitha@learning.com",
                "EMP006",
                ram.getId());

            // ── Team 3 (Sowjanya) ─────────────

            createEmployee(
                "sonika",
                "sonika@learning.com",
                "EMP007",
                sowjanya.getId());

            createEmployee(
                "shalini",
                "shalini@learning.com",
                "EMP008",
                sowjanya.getId());

            createEmployee(
                "debaasish",
                "debaasish@learning.com",
                "EMP009",
                sowjanya.getId());

            // ── Team 4 (Sailesh) ──────────────

            createEmployee(
                "pravallika",
                "pravallika@learning.com",
                "EMP010",
                sailesh.getId());

            createEmployee(
                "thanush",
                "thanush@learning.com",
                "EMP011",
                sailesh.getId());

            createEmployee(
                "raghava",
                "raghava@learning.com",
                "EMP012",
                sailesh.getId());

            // ── Team 5 (Yamini) ───────────────

            createEmployee(
                "geetanjali",
                "geetanjali@learning.com",
                "EMP013",
                yamini.getId());

            createEmployee(
                "jyothi",
                "jyothi@learning.com",
                "EMP014",
                yamini.getId());

            createEmployee(
                "reshma",
                "reshma@learning.com",
                "EMP015",
                yamini.getId());

            // ── Team 6 (Uday) ─────────────────

            createEmployee(
                "chandana",
                "chandana@learning.com",
                "EMP016",
                uday.getId());

            createEmployee(
                "sadaf",
                "sadaf@learning.com",
                "EMP017",
                uday.getId());

            createEmployee(
                "narasimha",
                "narasimha@learning.com",
                "EMP018",
                uday.getId());

            // ── Team 7 (Jagadeesh) ────────────

            createEmployee(
                "sahitha",
                "sahitha@learning.com",
                "EMP019",
                jagadeesh.getId());

            createEmployee(
                "padhu",
                "padhu@learning.com",
                "EMP020",
                jagadeesh.getId());

            createEmployee(
                "pappu",
                "pappu@learning.com",
                "EMP021",
                jagadeesh.getId());

            // ── Team 8 (Vicky) ────────────────

            createEmployee(
                "vijju",
                "vijju@learning.com",
                "EMP022",
                vicky.getId());

            createEmployee(
                "monika",
                "monika@learning.com",
                "EMP023",
                vicky.getId());

            createEmployee(
                "prabha",
                "prabha@learning.com",
                "EMP024",
                vicky.getId());

            // ── Team 9 (Sravya) ───────────────

            createEmployee(
                "madhavi",
                "madhavi@learning.com",
                "EMP025",
                sravya.getId());

            createEmployee(
                "keerthana",
                "keerthana@learning.com",
                "EMP026",
                sravya.getId());

            createEmployee(
                "sruju",
                "sruju@learning.com",
                "EMP027",
                sravya.getId());

            log.info("Employees created! ✅");
            log.info("Data initialization " +
                     "completed! ✅");

        } catch (Exception e) {
            log.error("Data initialization " +
                      "failed: {}",
                      e.getMessage());
        }
    }

    // ── Check Data Exists ─────────────────────

    private boolean isDataAlreadyLoaded() {
        try {
            UserResponse existing =
                authClient
                    .getUserByUsername("karthik");
            return existing != null;
        } catch (Exception e) {
            return false;
        }
    }

    // ── Helper Methods ────────────────────────

    private void createUser(
                 String username,
                 String email,
                 Role role) {
        try {
            UserRequest request =
                UserRequest.builder()
                    .username(username)
                    .password("password123")
                    .email(email)
                    .role(role.name())
                    .name(username)
                    .code(username
                          .toUpperCase())
                    .build();

            authClient.createUser(request);

            log.info("{} created: {}",
                     role, username);

        } catch (Exception e) {
            log.warn("Failed to create " +
                     "user: {} - {}",
                     username,
                     e.getMessage());
        }
    }

    private CsmResponse createCsm(
                         String username,
                         String email,
                         String code) {
        try {
            UserRequest request =
                UserRequest.builder()
                    .username(username)
                    .password("password123")
                    .email(email)
                    .role(Role.CSM.name())
                    .name(username)
                    .code(code)
                    .build();

            CsmResponse csm =
                csmClient.addCsm(request);

            log.info("CSM created: {}",
                     username);

            return csm;

        } catch (Exception e) {
            log.warn("Failed to create " +
                     "CSM: {} - {}",
                     username,
                     e.getMessage());
            return CsmResponse.builder()
                    .id(0L)
                    .build();
        }
    }

    private ManagerResponse createManager(
                             String username,
                             String email,
                             String code,
                             Long csmId) {
        try {
            UserRequest request =
                UserRequest.builder()
                    .username(username)
                    .password("password123")
                    .email(email)
                    .role(Role.MANAGER.name())
                    .name(username)
                    .code(code)
                    .csmId(csmId)
                    .build();

            ManagerResponse manager =
                managerClient
                    .addManager(request);

            log.info("Manager created: {}",
                     username);

            return manager;

        } catch (Exception e) {
            log.warn("Failed to create " +
                     "manager: {} - {}",
                     username,
                     e.getMessage());
            return ManagerResponse.builder()
                    .id(0L)
                    .build();
        }
    }

    private void createEmployee(
                 String username,
                 String email,
                 String code,
                 Long managerId) {
        try {
            UserRequest request =
                UserRequest.builder()
                    .username(username)
                    .password("password123")
                    .email(email)
                    .role(Role.EMPLOYEE.name())
                    .name(username)
                    .code(code)
                    .managerId(managerId)
                    .build();

            employeeClient
                .addEmployee(request);

            log.info("Employee created: {}",
                     username);

        } catch (Exception e) {
            log.warn("Failed to create " +
                     "employee: {} - {}",
                     username,
                     e.getMessage());
        }
    }
}