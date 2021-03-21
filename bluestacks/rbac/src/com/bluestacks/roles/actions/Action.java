package com.bluestacks.roles.actions;

public class Action implements IAction{

    private String name;
    private String accessLevel;

    public Action(String accessLevel){
        this.accessLevel = accessLevel;
        this.name = accessLevel;
    }

    @Override
    public String getAccessLevel() {
        return this.accessLevel;
    }

    public void setAccessLevel(String accessLevel){
        this.accessLevel = accessLevel;
    }

    @Override
    public String getAccessLevelMessage() {
        return "This user/resource has available action : " + this.accessLevel;
    }
}
