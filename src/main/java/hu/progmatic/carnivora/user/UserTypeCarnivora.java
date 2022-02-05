package hu.progmatic.carnivora.user;

import java.util.Arrays;

public enum UserTypeCarnivora {
    ADMIN (Roles.USER_READ_ROLE,Roles.USER_WRITE_ROLE),
    USER(Roles.USER_CREATEANDMODIFY_ROLE,Roles.USER_READ_ROLE),
    GUEST(Roles.USER_READ_ROLE);
    private final String[]roles;

    UserTypeCarnivora(String...roles){this.roles = roles;}

    public String[] getRoles() {
        return roles;
    }
    public  boolean hasRole(String role){ return Arrays.asList(roles).contains(role);
    }
    public static  class Roles{
        public static final String USER_WRITE_ROLE = "USER WRITE";
        public static final String USER_READ_ROLE = "USER READ";
        public static final String USER_CREATEANDMODIFY_ROLE = "USER CREATE AND MODIFY";
    }
}
