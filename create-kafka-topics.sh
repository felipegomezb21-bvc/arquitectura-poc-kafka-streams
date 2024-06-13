#!/usr/bin/env bash

echo
echo "Create topic participants"
echo "------------------------------"
docker exec -t zookeeper kafka-topics --create --bootstrap-server kafka:9092 --replication-factor 1 --partitions 5 --topic participants --config cleanup.policy=compact

echo
echo "Create topic actors"
echo "------------------------------"
docker exec -t zookeeper kafka-topics --create --bootstrap-server kafka:9092 --replication-factor 1 --partitions 5 --topic actors --config cleanup.policy=compact

echo
echo "Create topic assets"
echo "------------------------------"
docker exec -t zookeeper kafka-topics --create --bootstrap-server kafka:9092 --replication-factor 1 --partitions 5 --topic assets --config cleanup.policy=compact


echo
echo "List topics"
echo "-----------"
docker exec -t zookeeper kafka-topics --list --bootstrap-server kafka:9092