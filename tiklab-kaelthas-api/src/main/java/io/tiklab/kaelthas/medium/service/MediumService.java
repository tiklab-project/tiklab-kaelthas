package io.tiklab.kaelthas.medium.service;

import io.tiklab.kaelthas.medium.model.Medium;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.JoinProvider;

import java.util.List;

@JoinProvider(model = Medium.class)
public interface MediumService {

    @FindAll
    List<Medium> getMediumAllList();

}
