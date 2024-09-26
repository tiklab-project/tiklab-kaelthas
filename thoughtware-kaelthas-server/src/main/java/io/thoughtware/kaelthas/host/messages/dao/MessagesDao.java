package io.thoughtware.kaelthas.host.messages.dao;

import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.kaelthas.host.messages.entity.MessagesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class MessagesDao {

    @Autowired
    JpaTemplate jpaTemplate;

    public void sendMessage(MessagesEntity messagesEntity) {
        jpaTemplate.save(messagesEntity, MessagesEntity.class);
    }
}
