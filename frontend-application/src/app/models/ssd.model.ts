export class Ssd {
    constructor(
        public name: string ='', 
        public capacityInGB: number = 0,
        public readSpeedInMBs: number = 0,
        public writeSpeedInMBs: number = 0, 
        public interfaceSSD: string = '',
        public format: string = ''
    ){}
}
