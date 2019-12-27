package com.grmek.rso.imageprocessing;

import com.kumuluz.ee.streaming.common.annotations.StreamListener;
import com.kumuluz.ee.streaming.common.annotations.StreamProducer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class KafkaStreamingInterface {

    @Inject
    ExternalLabelingService externalLabelingService;

    @Inject
    @StreamProducer
    private Producer producer;

    @StreamListener(topics = {"ptjc95jg-requests"})
    public void onMessage(ConsumerRecord<String, String> consumerRecord) {
        String id = consumerRecord.key();
        String url = consumerRecord.value();

        String labels = externalLabelingService.getLabels(url);

        ProducerRecord<String, String> producerRecord =
                new ProducerRecord<String, String>("ptjc95jg-responses", id, labels);

        producer.send(producerRecord, (metadata, e) -> {
            if (e != null) {
                System.err.println(e);
            }
            else {
                System.out.println("The offset of the produced message record is: " + metadata.offset());
            }
        });
    }
}
