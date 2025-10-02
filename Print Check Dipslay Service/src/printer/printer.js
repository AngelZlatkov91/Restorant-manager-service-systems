import { ThermalPrinter} from 'node-thermal-printer';
import { PrinterTypes } from 'node-thermal-printer';

const printer = new ThermalPrinter({
    type: PrinterTypes.EPSON,
    interface: 'usb',
    width: 48,
    characterSet: 'SLOVENIA',
    removeSpecialCharacters: false,
    lineCharacter: "=",

});

export default printer;

