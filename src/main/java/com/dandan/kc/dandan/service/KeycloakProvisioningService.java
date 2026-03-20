package com.dandan.kc.dandan.service;

import com.dandan.kc.dandan.dto.CustomerOnboardRequest;
import com.dandan.kc.dandan.dto.MerchantOnboardRequest;
import com.dandan.kc.dandan.dto.StaffOnboardRequest;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.GroupsResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KeycloakProvisioningService {

    private final Keycloak keycloak;

    private final String realm = "dandan";

    public String onboardMerchant(MerchantOnboardRequest request) {
        String merchantId = UUID.randomUUID().toString();
        GroupsResource groupsResource = keycloak.realm(realm).groups();
        GroupRepresentation merchantGroup = new GroupRepresentation();
        merchantGroup.setName("Merchant-" + merchantId);
        Response response = groupsResource
                .group(getMerchantsGroupId())
                .subGroup(merchantGroup);
        String merchantGroupId = CreatedResponseUtil.getCreatedId(response);
        createSubGroup(merchantGroupId, "staff");
        createSubGroup(merchantGroupId, "customers");
        return merchantId;
    }

    private void createSubGroup(String parentGroupId, String name) {
        GroupRepresentation subGroup = new GroupRepresentation();
        subGroup.setName(name);
        keycloak.realm(realm)
                .groups()
                .group(parentGroupId)
                .subGroup(subGroup);
    }

    private String getMerchantsGroupId() {
        List<GroupRepresentation> groups =
                keycloak.realm(realm).groups().groups();
        return groups.stream()
                .filter(g -> g.getName().equals("Merchants"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Merchants group not found"))
                .getId();
    }

    public String onboardCustomer(CustomerOnboardRequest request) {
        String userId = createUser(request.getEmail(), request.getFirstName(), request.getLastName());
        String groupId = findSubGroup(
                request.getMerchantId(),
                "customers"
        );
        /*
        RoleRepresentation staffRole = keycloak.realm(realm)
                .roles()
                .get("CUSTOMER")
                .toRepresentation();
         */
        keycloak.realm(realm)
                .users()
                .get(userId)
                .joinGroup(groupId);
    //.roles().realmLevel().add(Collections.singletonList(staffRole));
        return userId;
    }

    public String onboardStaff(StaffOnboardRequest request) {
        String userId = createUser(request.getEmail(), request.getFirstName(), request.getLastName());
        String groupId = findSubGroup(
                request.getMerchantId(),
                "staff"
        );
        keycloak.realm(realm)
                .users()
                .get(userId)
                .joinGroup(groupId);
        return userId;
    }

    private String createUser(String email, String firstName, String lastName) {
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(email);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmailVerified(true);
        user.setRequiredActions(
                List.of("webauthn-register-passwordless")
        );
        Response response = keycloak.realm(realm).users().create(user);
        return CreatedResponseUtil.getCreatedId(response);
    }

    private String findSubGroup(String merchantId, String subgroupName) {
        String merchantGroupName = "Merchant-" + merchantId;

        // 1. Get the top-level "Merchants" group
        GroupRepresentation merchantsTopGroup = keycloak.realm(realm).groups().groups().stream()
                .filter(g -> g.getName().equals("Merchants"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Merchants group not found"));

        // 2. Get subgroups of "Merchants" group
        List<GroupRepresentation> merchantsSubGroups = keycloak.realm(realm)
                .groups()
                .group(merchantsTopGroup.getId())
                .getSubGroups(null, null, null, null, false);

        // 3. Find the specific merchant group
        GroupRepresentation merchantGroup = merchantsSubGroups.stream()
                .filter(g -> g.getName().equals(merchantGroupName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Merchant sub-group not found: " + merchantGroupName));

        // 4. IMPORTANT: Explicitly fetch subgroups of the merchant group
        List<GroupRepresentation> merchantSubGroups = keycloak.realm(realm)
                .groups()
                .group(merchantGroup.getId())  // Use the merchant group's ID
                .getSubGroups(null, null, null, null, false);

        // 5. Find the specific subgroup
        return merchantSubGroups.stream()
                .filter(g -> g.getName().equals(subgroupName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Subgroup not found: " + subgroupName))
                .getId();
    }

}