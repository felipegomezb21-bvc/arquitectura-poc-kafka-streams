package com.example.all_streams_many_builder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Properties;
import java.time.Duration;

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

import com.example.all_streams_many_builder.JsonSerde; 


@SpringBootApplication
public class KafkaStreamsJoinApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(KafkaStreamsJoinApp.class, args);
        KafkaStreamsJoinApp app = context.getBean(KafkaStreamsJoinApp.class);
        app.runKafkaStreamsTradesBuy();
        app.runKafkaStreamsTradesSell();
        app.runKafkaStreamsTrades();
    }

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    public void runKafkaStreamsTradesBuy() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafka-streams.trades-buy");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
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
        KStream<String, Trade> tradeBuyStream= builder.stream("TradesBuy", Consumed.with(stringSerde, tradeSerde));
        KTable<String, Order> orderBuyTable = builder.table("OrdersBuy", Consumed.with(stringSerde, orderSerde));
        KTable<String, Asset> assetTable = builder.table("assets", Consumed.with(stringSerde, assetSerde));
        KTable<String, Actor> actorTable = builder.table("actors", Consumed.with(stringSerde, actorSerde));
        KTable<String, Participant> participantTable = builder.table("participants", Consumed.with(stringSerde, participantSerde));

        // TradeBuyStream
        // Unir el stream con la tabla de ordenes
        KStream<String, JoinedValue> enrichedTradeBuyStream = tradeBuyStream
            .map((key, value) -> KeyValue.pair(value.getOrderId(), value)) // Extraer la llave de unión con orderId
            .join(orderBuyTable,(trade, order) -> new JoinedValue(trade, order),
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

        enrichedTradeBuyStream.to("EnrichedTradeBuyStreamTopic", Produced.with(stringSerde, joinSerde));


        // Iniciar la aplicación
        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        
        streams.start();

        // Añadir un gancho de apagado para cerrar el cliente de streams
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

    public void runKafkaStreamsTradesSell() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafka-streams.trades-sell");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
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
        KStream<String, Trade> tradeSellStream= builder.stream("TradesSell", Consumed.with(stringSerde, tradeSerde));
        KTable<String, Order> orderSellTable = builder.table("OrdersSell", Consumed.with(stringSerde, orderSerde));
        KTable<String, Asset> assetTable = builder.table("assets", Consumed.with(stringSerde, assetSerde));
        KTable<String, Actor> actorTable = builder.table("actors", Consumed.with(stringSerde, actorSerde));
        KTable<String, Participant> participantTable = builder.table("participants", Consumed.with(stringSerde, participantSerde));

        // TradeSellStream
        // Unir el stream con la tabla de ordenes
        KStream<String, JoinedValue> enrichedTradeSellStream  = tradeSellStream
            .map((key, value) -> KeyValue.pair(value.getOrderId(), value)) // Extraer la llave de unión con orderId
            .join(orderSellTable,(trade, order) -> new JoinedValue(trade, order),
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


            enrichedTradeSellStream.to("EnrichedTradeSellStreamTopic", Produced.with(stringSerde, joinSerde));


        // Iniciar la aplicación
        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        
        streams.start();

        // Añadir un gancho de apagado para cerrar el cliente de streams
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
    
    public void runKafkaStreamsTrades() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafka-streams.enriched-trades");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // Forzar a leer desde el inicio del tópico
       
        StreamsBuilder builder = new StreamsBuilder();

        // Configurar Serdes
        Serde<String> stringSerde = Serdes.String();
        JsonSerde<JoinedValue> joinSerde = new JsonSerde<>(JoinedValue.class);
        JsonSerde<EnrichedTrade> enrichedTradeSerde = new JsonSerde<>(EnrichedTrade.class);

        // Leer los tópicos
        KStream<String, JoinedValue> tradeSellStream= builder.stream("EnrichedTradeSellStreamTopic", Consumed.with(stringSerde, joinSerde));
        KStream<String, JoinedValue> tradeBuyStream= builder.stream("EnrichedTradeBuyStreamTopic", Consumed.with(stringSerde, joinSerde));

        // TradeSellStream
        // Unir el stream con la tabla de ordenes
        KStream<String, EnrichedTrade> enrichedTradeStream = tradeBuyStream
            .leftJoin(tradeSellStream,
                (buySide, sellSide) -> new EnrichedTrade(buySide, sellSide),
                JoinWindows.ofTimeDifferenceAndGrace(Duration.ofHours(1), Duration.ofMillis(0L)), // Ventana de tiempo
                StreamJoined.with(stringSerde, joinSerde, joinSerde)); // Unir los dos streams 


        enrichedTradeStream.to("EnrichedTradeTopic2", Produced.with(stringSerde, enrichedTradeSerde));


        // Iniciar la aplicación
        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        
        streams.start();

        // Añadir un gancho de apagado para cerrar el cliente de streams
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}


