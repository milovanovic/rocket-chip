Demonstration of verilog sucessfully generated for AXI4 pins
=====================
This repository is fork of rocket-chip. Only difference is added module that contains single AXI4 memory mapped register [TEST_CODE.scala](src/main/scala/TEST_HERE/TEST_CODE.scala) and [Node.scala](src/main/scala/TEST_HERE/Node.scala) file which is obtained from [here](https://github.com/ucb-bar/rocket-dsp-utils/blob/master/src/main/scala/freechips/rocketchip/amba/axi4/Node.scala). Both files can be found in `/src/main/scala/TEST_HERE` folder.

Last rocket-chip commit that compiles given code is `5571442` (this barnch is fork from this specific commit). The given code on next commit `9a2d27b` wont compile. Please see branch: [`RegisterRouterError`](https://github.com/milovanovic/rocket-chip/tree/RegisterRouterError).

## Generate verilog

In order to start verilog generation run script:
```bash
$ ./RUNME.sh
```
Or in command line type:
```bash
$ sbt "runMain axi4test.AXI4TestBlockApp"
```

## Example of error:
Please see branch: [`RegisterRouterError`](https://github.com/milovanovic/rocket-chip/tree/RegisterRouterError).

