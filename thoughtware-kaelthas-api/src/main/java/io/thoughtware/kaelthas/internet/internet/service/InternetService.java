package io.thoughtware.kaelthas.internet.internet.service;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.kaelthas.internet.internet.model.Internet;

import java.util.List;

public interface InternetService {

    Pagination<Internet> findInternetPage(Internet internet);

    String createInternet(Internet internet);

    void deleteInternet(String id);

    void updateInternet(Internet internet);

    Internet findInternetById(String id);

    List<Internet> findAll();

}
