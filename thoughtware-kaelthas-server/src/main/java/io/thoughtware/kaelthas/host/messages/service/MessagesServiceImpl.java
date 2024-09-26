package io.thoughtware.kaelthas.host.messages.service;

import io.thoughtware.rpc.annotation.Exporter;
import io.thoughtware.kaelthas.alarm.model.Alarm;
import io.thoughtware.kaelthas.host.messages.dao.MessagesDao;
import io.thoughtware.kaelthas.host.messages.entity.MessagesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Exporter
public class MessagesServiceImpl implements MessageService {

    @Autowired
    MessagesDao messagesDao;

    @Override
    public void sendMessage(Alarm alarm) {
        MessagesEntity messagesEntity = new MessagesEntity();
        messagesEntity.setInformation(alarm.getSendMessage());
        messagesEntity.setAlarmId(alarm.getId());
        messagesEntity.setSendTime(alarm.getAlertTime());
        messagesEntity.setMediumId("3");

        messagesDao.sendMessage(messagesEntity);
    }
}
