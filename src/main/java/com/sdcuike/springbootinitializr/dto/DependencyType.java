package com.sdcuike.springbootinitializr.dto;

/**
 * @author sdcuike
 * @date 2018/10/19
 * @since 2018/10/19
 */
public enum DependencyType {
    Core("Core");

    private String name;

    DependencyType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
