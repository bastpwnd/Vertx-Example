package kafka;

import io.vertx.reactivex.kafka.client.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class Producer {

    KafkaProducer<String, String> producer = KafkaProducer.create(vertx, config);
    /**
     * Method to create producer to sent events, send one random product
     */
    public void createProducer(){
        Properties props = initProperties();
        producer =  KafkaProducer.create(vertx, config);

        TestCallback callback = new TestCallback();

        for(int i = 0; i < 1; i++) {

            String sentence = "Producto "+TypeProduct.getRandomProduct()+" :"+generateRandomId();
            //Se crea el dato que se enviara en un evento
            ProducerRecord< String, String > data = new ProducerRecord < String, String > (
                    "test_1", sentence);

            long startTime = System.currentTimeMillis();
            //Se envia al topic un evento por cada producto creado
            producer.send(data, callback);
            long elapsedTime = System.currentTimeMillis() - startTime;
            log.info("Evento enviado : " + sentence + " in " + elapsedTime + " ms");

        }
        //log.info("Eventos finalizados.");
        producer.flush();
        producer.close();

    }
    /**
     * Method to inicializate Properties to Kafka producer
     * @return props properties with values
     */
    public Properties initProperties(){
        Properties props = new Properties();
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "Producer");
        //props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        //config for local
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return props;
    }
}
