export class Cpu {
    constructor(
        public cpuName: string ='',
        public cpuCores: number = 0,
        public cpuClockRate: number =0,
        public cpuGaming: boolean = false,
        public cpuTDP: number = 0,
        public cpuThreads: number =0,
        public cpuType: string = '', 
        public cpuIntegratedGPU: boolean = false
      ) {}
}
