package com.bluestacks.users;

import com.bluestacks.roles.IRole;
import java.util.*;

public interface IUser {

    String getName();
    void setName(String name);

    Set<IRole> getRoleSet();
    void setRoleSet(Set<IRole> roleSet);
    void addRole(IRole role);
    void removeRole(IRole role);

    boolean isAdmin();
}
