export class Ram {
    constructor(
        public name: string = '',
        public capacityInGB: number = 0,
        public maxFrequencyInMHz: number = 0,
        public voltage: number = 0,
        public latency: string = '',
        public type: string = '',
        public hasRGB: boolean = false,
      ) {}
}
