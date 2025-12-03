import runConsumer from './kafka/consumer.js'
import printer from './printer/printer.js';

async function start() {
  let orders = null;

runConsumer((data) => {
  orders = data;
});

// printTable((data) => {
//   orders = data;
// });


  if (!orders) {
    return;
  }
  console.log(orders);

  printer.clear();
  printer.println(`Нова поръчка`);
  printer.drawLine();
  printer.println(`Mаса: ${orders.tableName}`);
  printer.println(`Персонал: ${orders.personal}`);
  printer.drawLine();

  orders.products.forEach (p => {
    printer.println(`${p.name}  -  ${p.quantity}`)
  });

  printer.drawLine();
  printer.println(`${new Date().toLocaleString()}`);
  printer.cut();


  try {
    const success = await printer.execute();
    console.log("Success: ", success);

  } catch (err) {
    console.log('Fail');
  }

}

start().catch(console.error)





