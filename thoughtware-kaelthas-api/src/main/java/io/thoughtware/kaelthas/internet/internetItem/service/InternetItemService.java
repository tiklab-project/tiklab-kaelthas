package io.thoughtware.kaelthas.internet.internetItem.service;

import io.thoughtware.kaelthas.internet.internetItem.model.InternetItem;

import java.util.List;

public interface InternetItemService {

    List<InternetItem> findItemList(InternetItem internetItem);
}
