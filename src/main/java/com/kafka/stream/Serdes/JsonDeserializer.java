package com.kafka.stream.Serdes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class JsonDeserializer<T> implements Deserializer<T> {

    private Class<T> destinationClass;

public JsonDeserializer(Class<T> destinationClass) {
    this.destinationClass = destinationClass;
}
    private final ObjectMapper objectMapper=new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS,false);
    @Override
    public T deserialize(String topic, byte[] data) {
        if(data==null)
        {
            return null;
        }
        try
        {
            return  objectMapper.readValue(data,destinationClass);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

}
