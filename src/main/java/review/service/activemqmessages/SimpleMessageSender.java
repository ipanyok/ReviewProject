package review.service.activemqmessages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class SimpleMessageSender implements MessageSender {

    @Autowired
    JmsTemplate jmsTemplate;

    @Override
    public void send(String message) {
        jmsTemplate.send(session -> session.createTextMessage(message));
    }
}
