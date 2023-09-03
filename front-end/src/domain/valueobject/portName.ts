import { Utils } from '../../utils/utils';

export class PortName {
  	private static readonly MAX_LENGTH: number = 40;
  	private static readonly PORT_NAME_NULL_ERROR_MESSAGE: string = "Port name cannot be null or empty";
  	private static readonly ERROR_MAX_LENGTH: string = `Port name must be at most ${PortName.MAX_LENGTH} characters in length`;
  	private readonly _portName: string;

  	constructor(portName: string) {
  	  	PortName.validate(portName);
  	  	this._portName = portName;
  	}

  	private static validate(portName: string): void {
  	  	if (portName == null || portName === "") {
  	  	  	throw new Error(PortName.PORT_NAME_NULL_ERROR_MESSAGE);
  	  	}
  	  	if (portName.length > PortName.MAX_LENGTH) {
  	  	  	throw new Error(PortName.ERROR_MAX_LENGTH);
  	  	}
  	}

  	get portName(): string {
  	  	return this._portName;
  	}

  	static random(): PortName {
  	 	const portName = Utils.randomString(Utils.randomInt(1, this.MAX_LENGTH));
  	  	return new PortName(portName);
  	}
}