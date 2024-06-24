#!/usr/bin/env bash

echo
echo "Create topic participants"
echo "------------------------------"
docker exec -t zookeeper kafka-topics --create --bootstrap-server kafka:9092 --replication-factor 1 --partitions 1 --topic participants --config cleanup.policy=compact

echo
echo "Create topic actors"
echo "------------------------------"
docker exec -t zookeeper kafka-topics --create --bootstrap-server kafka:9092 --replication-factor 1 --partitions 1 --topic actors --config cleanup.policy=compact

echo
echo "Create topic assets"
echo "------------------------------"
docker exec -t zookeeper kafka-topics --create --bootstrap-server kafka:9092 --replication-factor 1 --partitions 1 --topic assets --config cleanup.policy=compact

echo
echo "Create topic OrdersBuy"
echo "------------------------------"
docker exec -t zookeeper kafka-topics --create --bootstrap-server kafka:9092 --replication-factor 1 --partitions 1 --topic OrdersBuy --config cleanup.policy=compact

echo
echo "Create topic OrdersSell"
echo "------------------------------"
docker exec -t zookeeper kafka-topics --create --bootstrap-server kafka:9092 --replication-factor 1 --partitions 1 --topic OrdersSell --config cleanup.policy=compact

echo
echo "Create topic TradesBuy"
echo "------------------------------"
docker exec -t zookeeper kafka-topics --create --bootstrap-server kafka:9092 --replication-factor 1 --partitions 1 --topic TradesBuy --config cleanup.policy=compact

echo
echo "Create topic TradesSell"
echo "------------------------------"
docker exec -t zookeeper kafka-topics --create --bootstrap-server kafka:9092 --replication-factor 1 --partitions 1 --topic TradesSell --config cleanup.policy=compact

echo
echo "Create topic EnrichedTradeSellStreamTopic"
echo "------------------------------"
docker exec -t zookeeper kafka-topics --create --bootstrap-server kafka:9092 --replication-factor 1 --partitions 1 --topic EnrichedTradeSellStreamTopic --config cleanup.policy=compact

echo
echo "Create topic EnrichedTradeBuyStreamTopic"
echo "------------------------------"
docker exec -t zookeeper kafka-topics --create --bootstrap-server kafka:9092 --replication-factor 1 --partitions 1 --topic EnrichedTradeBuyStreamTopic --config cleanup.policy=compact

echo
echo "List topics"
echo "-----------"
docker exec -t zookeeper kafka-topics --list --bootstrap-server kafka:9092