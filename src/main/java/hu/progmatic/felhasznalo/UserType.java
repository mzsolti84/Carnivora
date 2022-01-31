package hu.progmatic.felhasznalo;


import java.util.Arrays;

public enum UserType {
  ADMIN(
      Roles.USER_WRITE_ROLE,
      Roles.USER_READ_ROLE
  ),
  USER(Roles.USER_READ_ROLE),
  GUEST();
  private final String[] roles;


  UserType(String... roles) {
    this.roles = roles;
  }

  public String[] getRoles() {
    return roles;
  }

  public boolean hasRole(String role) {
    return Arrays.asList(roles).contains(role);
  }

  public static class Roles {
    public static final String USER_WRITE_ROLE = "USER_WRITE";
    public static final String USER_READ_ROLE = "USER_READ";
  }
}
