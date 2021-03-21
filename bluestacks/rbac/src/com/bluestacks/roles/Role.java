package com.bluestacks.roles;

import com.bluestacks.roles.actions.IAction;

import java.util.Set;
import java.util.HashSet;

public class Role implements IRole {

    private String name;
    private Set<IAction> actionSet;

    public Role(String name){
        this.name = name;
        this.actionSet = new HashSet<IAction>();
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
    public Set<IAction> getActionSet() {
        return this.actionSet;
    }

    @Override
    public void setActionSet(Set<IAction> action) {
        this.actionSet = actionSet;
    }

    @Override
    public void addAction(IAction action) {
        this.actionSet.add(action);
    }

    @Override
    public void removeAction(IAction action) {
        this.actionSet.remove(action);
    }
}
