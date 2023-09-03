package es.ull.adapter.websocket;

import es.ull.application.OutputPort;

public class WebsocketOutputPort implements OutputPort {
    @Override
    public void sendEvent(String json) {
        System.out.println(json);
    }
}
