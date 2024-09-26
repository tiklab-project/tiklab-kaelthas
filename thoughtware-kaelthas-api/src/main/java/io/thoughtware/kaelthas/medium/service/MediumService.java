package io.thoughtware.kaelthas.medium.service;

import io.thoughtware.kaelthas.medium.model.Medium;
import io.thoughtware.toolkit.join.annotation.FindAll;
import io.thoughtware.toolkit.join.annotation.JoinProvider;

import java.util.List;

@JoinProvider(model = Medium.class)
public interface MediumService {

    @FindAll
    List<Medium> getMediumAllList();

}
