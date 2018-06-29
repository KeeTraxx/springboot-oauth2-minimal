package ch.puzzle.oauth2.example.oauth2;

import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Extracts and converts Keycloak realm & client roles to Spring Security compatible {@link GrantedAuthority}
 * <p>
 * Basically takes roles Array and Prepends "ROLE_" / "ROLE_OPR_" to it.
 * <p>
 * Attention: Needs a "User Realm Role" Mapper in the Keycloak Client:
 * Mapper Type: User Realm Role
 * Realm Role prefix: empty
 * Multivalued: ON
 * Token Claim Name: roles
 * Claim JSON Type: String
 * Add to ID token: ON
 * Add to access token: ON
 * Add to userinfo: ON
 *
 * Attention: Needs a "User Client Role" Mapper in the Keycloak Client:
 * Mapper Type: User Client Role
 * Client ID: hast-opr
 * Realm Role prefix: empty
 * Multivalued: ON
 * Token Claim Name: client_roles
 * Claim JSON Type: String
 * Add to ID token: ON
 * Add to access token: ON
 * Add to userinfo: ON
 */
@Component
public class KeycloakRolesExtractor implements AuthoritiesExtractor {

    private final String REALM_ROLES_KEY = "roles";

    private final String CLIENT_ROLES_KEY = "client_roles";

    private final String REALM_ROLE_PREFIX = "ROLE_";

    private final String CLIENT_ROLE_PREFIX = "ROLE_OPR_";

    @Override
    @SuppressWarnings("unchecked")
    public List<GrantedAuthority> extractAuthorities(Map<String, Object> map) {
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.addAll(extractRealmRoles(map));
        roles.addAll(extractClientRoles(map));
        return roles;
    }

    @SuppressWarnings("unchecked")
    private List<GrantedAuthority> extractClientRoles(Map<String, Object> map) {
        if (map.containsKey(CLIENT_ROLES_KEY)) {
            Object rolesObj = map.get(CLIENT_ROLES_KEY);
            if (rolesObj instanceof List) {
                return extractRoles((List<String>) rolesObj, CLIENT_ROLE_PREFIX);
            }
        }
        return Collections.emptyList();
    }

    @SuppressWarnings("unchecked")
    private List<GrantedAuthority> extractRealmRoles(Map<String, Object> map) {
        if (map.containsKey(REALM_ROLES_KEY)) {
            Object rolesObj = map.get(REALM_ROLES_KEY);
            if (rolesObj instanceof List) {
                return extractRoles((List<String>) rolesObj, REALM_ROLE_PREFIX);
            }

        }
        return Collections.emptyList();
    }

    private List<GrantedAuthority> extractRoles(List<String> roles, String prefix) {
        return AuthorityUtils.createAuthorityList(roles.stream()
                .map(s -> prefix + s.toUpperCase())
                .toArray(String[]::new)
        );
    }
}
