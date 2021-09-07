package edu.strauteka.example.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import edu.strauteka.example.dto.NotifyData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class NotifyDataDeserializer extends StdDeserializer<NotifyData> {
    public  NotifyDataDeserializer() {
        this(NotifyData.class);
    }

    protected NotifyDataDeserializer(Class<NotifyData> vc) {
        super(vc);
    }

    @Override
    public NotifyData deserialize(JsonParser p, DeserializationContext context) throws IOException {
        final JsonNode node = p.readValueAsTree();
        return new NotifyData(node.get(NotifyData.NOTIFY_KEY).asText());
    }
}
