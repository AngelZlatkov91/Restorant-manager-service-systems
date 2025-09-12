
import { SerialPort } from ('serialport');

let printerPort;

async function findPrinterPort() {
  const ports = await SerialPort.list();
  const datecsPort = ports.find(p =>
    p.manufacturer && p.manufacturer.toLowerCase().includes('datecs')
  );
  return datecsPort ? datecsPort.path : null;
}

async function connectToPrinter() {
  const configuredPort = 'COM3';
  const portPath = configuredPort || await findPrinterPort();

  if (!portPath) {
    throw new Error('❌ DATECS printer not found and CASH_REGISTER_PORT not set.');
  }

  console.log(`✅ Connecting to printer at ${portPath}...`);

  printerPort = new SerialPort({
    path: portPath,
    baudRate: 9600,
    autoOpen: false,
  });

  return new Promise((resolve, reject) => {
    printerPort.open(err => {
      if (err) {
        console.error('❌ Failed to open serial port:', err.message);
        reject(err);
      } else {
        console.log('🖨️ Printer connection established.');
        resolve();
      }
    });
  });
}

function printData(data) {
  if (!printerPort || !printerPort.isOpen) {
    console.error('❌ Printer is not connected.');
    return;
  }

  const buffer = Buffer.from(data, 'utf8');
  printerPort.write(buffer, err => {
    if (err) {
      console.error('❌ Failed to send data to printer:', err.message);
    } else {
      console.log('🧾 Data sent to printer.');
    }
  });
}

module.exports = {
  connectToPrinter,
  printData
};
