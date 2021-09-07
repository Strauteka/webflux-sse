package edu.strauteka.example.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import edu.strauteka.example.dto.Ping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@Slf4j
@JsonComponent
public class PingDeserializer extends StdDeserializer<Ping> {

    public PingDeserializer() {
        this(Ping.class);
    }

    protected PingDeserializer(Class<Ping> vc) {
        super(vc);
    }

    @Override
    public Ping deserialize(JsonParser p, DeserializationContext context) throws IOException {
        final JsonNode node = p.readValueAsTree();
        return new Ping(node.get(Ping.PING_ID).asLong());
    }
}
