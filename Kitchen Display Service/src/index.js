import express from 'express';
import runConsumer from './kafka/consumer.js'
const app = express();
const port = 3000;

let latestORder = null;

runConsumer((data) => {
  latestORder = data;
});

app.get('/latest-order', (req, res) => {
  if (latestORder) {
    res.json(latestORder);
  } else {
    res.status(204).send();
  }
})
  app.listen(port, () =>
    console.log(`🚀 Kitchen listener REST → http://localhost:${port}/orders`)
  );



