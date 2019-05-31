package review.service.activemqmessages;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@Service
public class SimpleMessageListener implements MessageListener {

    private static final Logger logger = Logger.getLogger(SimpleMessageListener.class);

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            logger.info(textMessage.getText());
        } catch (JMSException e) {
            logger.error(e.getMessage());
        }
    }
}
