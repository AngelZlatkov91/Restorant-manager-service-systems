import { ThermalPrinter} from 'node-thermal-printer';
import { PrinterTypes } from 'node-thermal-printer';

let printer = new ThermalPrinter({
    type: PrinterTypes.EPSON,
    interface: 'usb'
});

