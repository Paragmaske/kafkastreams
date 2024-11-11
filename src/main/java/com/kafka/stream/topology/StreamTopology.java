package com.kafka.stream.topology;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.stereotype.Component;


@Component
public class StreamTopology {

    public  static String GREETING="greeting";
    public  static String GREETING_UPPERCASE="greeting_uppercase";


public  static Topology buildTopology(){
    StreamsBuilder streamsBuilder=new StreamsBuilder();

    var greetingStream=streamsBuilder.stream(GREETING, Consumed.with(Serdes.String(),Serdes.String()));

    greetingStream.print(Printed.<String,String>toSysOut().withLabel("greetingStream"));
    var modifedStream=greetingStream.
            filter((key,value)->value.length()>5)
            .mapValues((readOnlyKey,value)->value.toUpperCase());
    modifedStream.print(Printed.<String,String>toSysOut().withLabel("modifedStream"));

    modifedStream.to(GREETING_UPPERCASE, Produced.with(Serdes.String(),Serdes.String()));

    return streamsBuilder.build();
}
}
