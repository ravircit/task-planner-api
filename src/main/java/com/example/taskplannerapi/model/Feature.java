package com.example.taskplannerapi.model;

import com.example.taskplannerapi.enums.Status;
import com.example.taskplannerapi.enums.TaskType;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("Feature")
public class Feature extends Type {

    public Feature() {
        super(TaskType.FEATURE);
    }
    @Override
    public Status nextStatus(Status taskStatus) {
        switch (taskStatus) {
            case OPEN:
                return Status.IN_PROGRESS;
            case IN_PROGRESS:
                return Status.TESTING;
            case TESTING:
                return Status.DEPLOYED;
            default:
                return null;
        }
    }
}
