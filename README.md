## kafka streams

### prerequisites

follow confluent CLI and tools setup

### stream csv file to topic-x

```  
confluent kafka topic produce topic-x < src/main/resources/steam-200k.csv
```

### run application

export the following environment variables

```   
export CLUSTER_BOOTSTRAP_SERVERS=some-bootstrap-server:9092
```

```  
export CLUSTER_API_KEY=some-key
```

```  
export CLUSTER_API_SECRET=some-secret
```

run application

```  
./gradlew bootRun
```

