package com.hrsupportcentresq014.data_seeder;

import com.hrsupportcentresq014.entities.Permission;
import com.hrsupportcentresq014.entities.Role;
import com.hrsupportcentresq014.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class DataSeeder implements CommandLineRunner {
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        // set the list of permission for the admin role
        List<Permission> adminPermission = new ArrayList<>();
        adminPermission.add(new Permission(Permission.ADMIN_DELETE));
        adminPermission.add(new Permission(Permission.ADMIN_CREATE));
        adminPermission.add(new Permission(Permission.ADMIN_READ));
        adminPermission.add(new Permission(Permission.ADMIN_UPDATE));
        adminPermission.add(new Permission(Permission.HR_CREATE));
        adminPermission.add(new Permission(Permission.HR_DELETE));
        adminPermission.add(new Permission(Permission.HR_READ));
        adminPermission.add(new Permission(Permission.HR_UPDATE));

        Role adminRole = new Role();
        adminRole.setId("admin");
        adminRole.setName("ADMIN");
        adminRole.setPermissions(adminPermission);

        // set the list of permission for the Hr role
        List<Permission> hrPermission = new ArrayList<>();
        hrPermission.add(new Permission(Permission.HR_CREATE));
        hrPermission.add(new Permission(Permission.HR_DELETE));
        hrPermission.add(new Permission(Permission.HR_READ));
        hrPermission.add(new Permission(Permission.HR_UPDATE));

        Role hrRole = new Role();
        hrRole.setId("hr");
        hrRole.setName("HR");
        hrRole.setPermissions(hrPermission);


        Role staffRole = new Role();
        staffRole.setId("staff");
        staffRole.setName("STAFF");
        staffRole.setPermissions(Collections.emptyList());

        //save all the roles into the database
        roleRepository.saveAll(Arrays.asList(adminRole, hrRole, staffRole));


    }
}
