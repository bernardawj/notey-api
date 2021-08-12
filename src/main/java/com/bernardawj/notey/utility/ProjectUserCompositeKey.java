package com.bernardawj.notey.utility;

import java.io.Serializable;

public class ProjectUserCompositeKey implements Serializable {

    private Integer projectId;
    private Integer userId;

    public ProjectUserCompositeKey() {
    }

    public ProjectUserCompositeKey(Integer projectId, Integer userId) {
        this.projectId = projectId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
