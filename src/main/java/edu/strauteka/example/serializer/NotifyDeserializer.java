package edu.strauteka.example.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import edu.strauteka.example.dto.Notify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@JsonComponent
@Component
public class NotifyDeserializer extends StdDeserializer<Notify> {
    private final ObjectMapper mapper = new ObjectMapper();

    public NotifyDeserializer() {
        this(Notify.class);
    }

    protected NotifyDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Notify deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        JsonNode jsonNode = jsonParser.readValueAsTree();
        final String descriptor = jsonNode.get(Notify.SERIALIZER_KEY).asText();
        final Notify.Key key = Arrays
                .stream(Notify.Key.values())
                .filter(k -> k.name().equals(descriptor))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Could not recognize Class!"));
        return mapper.treeToValue(jsonNode, key.aClass);
    }
}
