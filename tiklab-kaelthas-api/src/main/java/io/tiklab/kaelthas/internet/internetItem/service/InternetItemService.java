package io.tiklab.kaelthas.internet.internetItem.service;

import io.tiklab.kaelthas.internet.internetItem.model.InternetItem;

import java.util.List;

public interface InternetItemService {

    List<InternetItem> findItemList(InternetItem internetItem);
}
