/*
 * Copyright (c) 2012. Axon Framework
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package group.bison.axon.live.listener;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import group.bison.axon.live.audience.events.AudienceJoinedRoomEvent;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * <p>Creates a JSON object and broadcasts it to every connected WebSocket session. The structure of the json object is:</p>
 * <pre>
 * {
 *     tradeExecuted :
 *     {
 *         orderbookId: ... ,
 *         count: ... ,
 *         price: ...
 *     }
 * }
 * </pre>
 * <p>The url to send the request to can be configured.</p>
 *
 * @author Jettro Coenradie
 */
@Component
public class AudienceJoinedRoomBroadcaster extends BroadcastingTextWebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(AudienceJoinedRoomBroadcaster.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @EventHandler
    public void handle(AudienceJoinedRoomEvent event) {
        try {
            doHandle(event);
        } catch (IOException e) {
            logger.warn("Problem while sending TradeExecutedEvent to external system");
        }
    }

    private void doHandle(AudienceJoinedRoomEvent event) throws IOException {
        String jsonObjectAsString = createJsonInString(event);

        this.broadcast(jsonObjectAsString);
    }

    private String createJsonInString(AudienceJoinedRoomEvent event) throws IOException {
        Writer writer = new StringWriter();
        JsonGenerator g = objectMapper.createGenerator(writer);
        g.writeStartObject();
        g.writeObjectFieldStart("audienceJoinedRoom");
        g.writeStringField("audienceId", event.getAudienceId().toString());
        g.writeStringField("roomId", event.getRoomId().toString());
        g.writeStringField("joinTimeTs", String.valueOf(event.getJoinTimeTs()));
        g.writeEndObject(); // for trade-executed
        g.close();
        return writer.toString();
    }
}