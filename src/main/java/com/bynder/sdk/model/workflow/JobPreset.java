package com.bynder.sdk.model.workflow;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JobPreset {

    @SerializedName(value = "ID")
    private String id;

    private String name;

    private List<StagePreset> stages;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<StagePreset> getStages() {
        return stages;
    }
}
