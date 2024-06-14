package com.example.all_streams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde; // Add this import statement
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;

import com.example.all_streams.JsonSerde; // Add this import statement


@SpringBootApplication
public class KafkaStreamsJoinApp {

    public static void main(String[] args) {
        
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafka-streams-all");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // Forzar a leer desde el inicio del tópico
       
        StreamsBuilder builder = new StreamsBuilder();

        // Configurar Serdes
        Serde<String> stringSerde = Serdes.String();
        JsonSerde<Order> orderSerde = new JsonSerde<>(Order.class);
        JsonSerde<Trade> tradeSerde = new JsonSerde<>(Trade.class);
        JsonSerde<Asset> assetSerde = new JsonSerde<>(Asset.class);
        JsonSerde<Actor> actorSerde = new JsonSerde<>(Actor.class);
        JsonSerde<Participant> participantSerde = new JsonSerde<>(Participant.class);
        JsonSerde<JoinedValue> joinSerde = new JsonSerde<>(JoinedValue.class);


        // Leer los tópicos
        KStream<String, Trade> tradeStream= builder.stream("TradesBuy", Consumed.with(stringSerde, tradeSerde));
        KTable<String, Order> orderTable = builder.table("OrdersBuy", Consumed.with(stringSerde, orderSerde));
        KTable<String, Asset> assetTable = builder.table("assets", Consumed.with(stringSerde, assetSerde));
        KTable<String, Actor> actorTable = builder.table("actors", Consumed.with(stringSerde, actorSerde));
        KTable<String, Participant> participantTable = builder.table("participants", Consumed.with(stringSerde, participantSerde));
        
        // Transformar el stream para extraer la llave de unión, y colocarla de key
        KStream<String, Trade> stream1WithKey = tradeStream.map((key, value) -> KeyValue.pair(value.getOrderId(), value));

        // Unir el stream con la tabla de ordenes
        KStream<String, JoinedValue> joinStream = stream1WithKey.join(orderTable,
            (trade, order) -> new JoinedValue(trade, order),
            Joined.with(stringSerde, tradeSerde, orderSerde)) // Union a partir de la llave orderId
            .map((key, value) -> KeyValue.pair(String.valueOf(value.getAssetId()), value)) // Extraer la llave de unión con asset
            .join(assetTable,
                (joinedValue, asset) -> new JoinedValue(joinedValue, asset),
                Joined.with(stringSerde, joinSerde, assetSerde)) // Unir con la tabla de assets; 
            .map((key, value) -> KeyValue.pair(String.valueOf(value.getActorId()), value)) //Extraer la llave de unión con actor
            .join(actorTable,
                (joinedValue, actor) -> new JoinedValue(joinedValue, actor),
                Joined.with(stringSerde, joinSerde, actorSerde)) //unir con la tabla de actores
            .map((key, value) -> KeyValue.pair(String.valueOf(value.getParticipantId()), value)) //Extraer la llave de unión con participant
            .join(participantTable,
                (joinedValue, participant) -> new JoinedValue(joinedValue, participant),
                Joined.with(stringSerde, joinSerde, participantSerde)) // Unir con la tabla de participantes
            .map((key, value) -> KeyValue.pair(value.getMatchId(), value)); // Reestablecer la llave original matchId


        joinStream.to("JoinedTopic", Produced.with(stringSerde, joinSerde)); // Enviar el resultado a un tópico


        // Iniciar la aplicación
        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        
        streams.start();

        // Añadir un gancho de apagado para cerrar el cliente de streams
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
