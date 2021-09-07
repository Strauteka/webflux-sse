package edu.strauteka.example.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import edu.strauteka.example.dto.Ping2;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class Ping2Deserializer extends StdDeserializer<Ping2> {

    public Ping2Deserializer() {
        this(Ping2.class);
    }

    protected Ping2Deserializer(Class<Ping2> vc) {
        super(vc);
    }

    @Override
    public Ping2 deserialize(JsonParser p, DeserializationContext context) throws IOException {
        final JsonNode node = p.readValueAsTree();
        return new Ping2(node.get(Ping2.PING_ID).asLong());
    }
}
