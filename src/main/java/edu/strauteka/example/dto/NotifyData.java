package edu.strauteka.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.strauteka.example.serializer.NotifyDataDeserializer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@RequiredArgsConstructor
@JsonDeserialize(using = NotifyDataDeserializer.class)
public class NotifyData extends Notify {
    public static final String NOTIFY_KEY = "notify";
    @JsonProperty(NOTIFY_KEY)
    private final String notify;

    @Override
    public Key getKey() {
        return super.getKey(this.getClass());
    }
}
