package io.thoughtware.kaelthas.host.messages.service;

import io.thoughtware.kaelthas.alarm.model.Alarm;

public interface MessageService {

    void sendMessage(Alarm alarm);
}
