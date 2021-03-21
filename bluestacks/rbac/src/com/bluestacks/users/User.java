package com.bluestacks.users;

import com.bluestacks.roles.IRole;
import com.bluestacks.roles.actions.IAction;

import java.util.HashSet;
import java.util.Set;

public class User implements IUser{

    private String name;
    private Set<IRole> roleSet;

    public User(String name){
        this.name = name;
        this.roleSet = new HashSet<IRole>();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Set<IRole> getRoleSet() {
        return this.roleSet;
    }

    @Override
    public void setRoleSet(Set<IRole> roleSet) {
        this.roleSet = roleSet;
    }

    @Override
    public void addRole(IRole role) {
        this.roleSet.add(role);
    }

    @Override
    public void removeRole(IRole role) {
        this.roleSet.remove(role);
    }

    public boolean isAdmin(){
        for (IRole role : roleSet){
            for (IAction action : role.getActionSet()){
                if (action.getAccessLevel().equals("admin"))
                    return true;
            }
        }
        return false;
    }
}
