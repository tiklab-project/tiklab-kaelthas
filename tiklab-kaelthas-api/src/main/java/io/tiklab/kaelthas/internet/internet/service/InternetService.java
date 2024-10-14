package io.tiklab.kaelthas.internet.internet.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.internet.internet.model.Internet;

import java.util.List;

public interface InternetService {

    Pagination<Internet> findInternetPage(Internet internet);

    String createInternet(Internet internet);

    void deleteInternet(String id);

    void updateInternet(Internet internet);

    Internet findInternetById(String id);

    List<Internet> findAll();

}
