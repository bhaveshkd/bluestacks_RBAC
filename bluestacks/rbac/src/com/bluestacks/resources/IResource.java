package com.bluestacks.resources;

import com.bluestacks.roles.IRole;

import java.util.Set;

public interface IResource {
    String getName();
    void setName(String name);

    Set<IRole> getRoleSet();
    void setRoleSet(Set<IRole> roleSet);
    void addRole(IRole role);
    void removeRole(IRole role);
}
