import { Kafka } from 'kafkajs';

const orders = [];

const kafka = new Kafka({
  clientId: 'print-display',
  brokers: ['localhost:9092'],
});

const consumer = kafka.consumer({ groupId: 'print-check' });

const checkDisplay = async (onMessage) => {
  await consumer.connect();
  await consumer.subscribe({ topic: 'check-display', fromBeginning: true });

  await consumer.run({
    eachMessage: async ({ topic, partition, message }) => {
      const data = JSON.parse(message.value.toString());
      console.log('📥 Получена поръчка:', data);
      onMessage(data); // подай към callback за обработка
    },
  });
};

const printTable = async (onMessage) => {
  await consumer.connect();
  await consumer.subscribe({ topic: 'print-table', fromBeginning: true });

  await consumer.run({
    eachMessage: async ({ topic, partition, message }) => {
      const data = JSON.parse(message.value.toString());
      console.log('📥 Получена поръчка:', data);
      onMessage(data); // подай към callback за обработка
    },
  });
};
export default {
checkDisplay,  
printTable
}
