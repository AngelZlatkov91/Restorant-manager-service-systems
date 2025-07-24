import { Kafka } from 'kafkajs';

const orders = [];

const kafka = new Kafka({
  clientId: 'bar-consumer',
  brokers: ['localhost:9092'],
});

const consumer = kafka.consumer({ groupId: 'bar-group' });

const runConsumer = async (onMessage) => {
  await consumer.connect();
  await consumer.subscribe({ topic: 'bar-display', fromBeginning: true });

  await consumer.run({
    eachMessage: async ({ topic, partition, message }) => {
      const data = JSON.parse(message.value.toString());
      console.log('📥 Получена поръчка:', data);
      onMessage(data); // подай към callback за обработка
    },
  });
};

export default runConsumer
