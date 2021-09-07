package edu.strauteka.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.strauteka.example.serializer.PingDeserializer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@RequiredArgsConstructor
@JsonDeserialize(using = PingDeserializer.class)
public class Ping extends Notify {
    public static final String PING_ID = "pingId";
    @JsonProperty(PING_ID)
    private final Long id;

    @Override
    public Key getKey() {
        return super.getKey(this.getClass());
    }
}
