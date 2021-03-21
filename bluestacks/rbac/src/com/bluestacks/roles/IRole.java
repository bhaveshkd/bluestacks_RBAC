package com.bluestacks.roles;

import com.bluestacks.roles.actions.IAction;

import java.util.Set;

public interface IRole {
    String getName();
    void setName(String name);

    Set<IAction> getActionSet();
    void setActionSet(Set<IAction> action);
    void addAction(IAction action);
    void removeAction(IAction action);
}

