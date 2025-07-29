import { Kafka } from 'kafkajs';

const orders = [];

const kafka = new Kafka({
  clientId: 'receipt-consumer',
  brokers: ['localhost:9092'],
});

const consumer = kafka.consumer({ groupId: 'receipt-group' });

const runConsumer = async (onMessage) => {
  await consumer.connect();
  await consumer.subscribe({ topic: 'print-receipt', fromBeginning: true });

  await consumer.run({
    eachMessage: async ({ topic, partition, message }) => {
      const data = JSON.parse(message.value.toString());
      console.log('📥 Получена сметка:', data);
      onMessage(data); // подай към callback за обработка
    },
  });
};

export default runConsumer
