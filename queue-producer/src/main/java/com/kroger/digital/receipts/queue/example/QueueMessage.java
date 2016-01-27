package com.kroger.digital.receipts.queue.example;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by kon3982 on 1/26/16.
 */
public class QueueMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    public String userId;
    public String division;
    public String store;
    public String terminalId;
    public String dateReceived;

    private static final DateFormat DF = new SimpleDateFormat("YYYY-MM-DD");

    public static QueueMessage randomMessage() {
        QueueMessage msg = new QueueMessage();
        msg.userId = UUID.randomUUID().toString();
        msg.division = "001";
        msg.store = "014";
        msg.terminalId = "999";
        msg.dateReceived = today();

        return msg;
    }

    public String toString() {
        return "{userId: '" + userId + "', division: '" + division + "', store: '" + store + "', terminalId: '" + terminalId + "', dateReceived: '" + dateReceived + "'}";
    }

    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }


    private static final String today() {
        return DF.format(new Date());
    }
}
