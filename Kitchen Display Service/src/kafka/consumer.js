import { Kafka } from 'kafkajs';

const orders = [];

const kafka = new Kafka({
  clientId: 'kitchen-consumer',
  brokers: ['localhost:9092'],
});

const consumer = kafka.consumer({ groupId: 'kitchen-group' });

const runConsumer = async (onMessage) => {
  await consumer.connect();
  await consumer.subscribe({ topic: 'kitchen-display', fromBeginning: true });

  await consumer.run({
    eachMessage: async ({ topic, partition, message }) => {
      const data = JSON.parse(message.value.toString());
      console.log('📥 Получена поръчка:', data);
      onMessage(data); // подай към callback за обработка
    },
  });
};

export default runConsumer
