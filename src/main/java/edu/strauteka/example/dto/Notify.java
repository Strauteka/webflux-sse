package edu.strauteka.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.strauteka.example.serializer.NotifyDeserializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Arrays;

@Getter
@ToString
@NoArgsConstructor
@JsonDeserialize(using = NotifyDeserializer.class)
public abstract class Notify {

    public static final String SERIALIZER_KEY = "serializerKey";
    public enum Key {
        Ping1(Ping.class),
        Ping2(Ping2.class),
        Data(NotifyData.class);

        public final Class<? extends Notify> aClass;

        Key(Class<? extends Notify> aClass) {
            this.aClass = aClass;
        }
    }

    private final Long time = System.currentTimeMillis();


    @ToString.Include
    @JsonProperty(SERIALIZER_KEY)
    public abstract Key getKey();

    protected Key getKey(Class<? extends Notify> aClass) {
        return Arrays
                .stream(Key.values())
                .filter(k -> k.aClass.equals(aClass))
                .findAny()
                .orElseThrow(() -> new RuntimeException(new ClassNotFoundException()));
    }
}
