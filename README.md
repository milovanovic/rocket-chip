Demonstration of sucessful Verilog code generated for AXI4 pins
===============================================================

This repository is a fork of rocket-chip. The only difference is added module that contains a single AXI4 memory mapped register [TEST_CODE.scala](src/main/scala/TEST_HERE/TEST_CODE.scala) and [Node.scala](src/main/scala/TEST_HERE/Node.scala) file which is obtained from [here](https://github.com/ucb-bar/rocket-dsp-utils/blob/master/src/main/scala/freechips/rocketchip/amba/axi4/Node.scala). Both files can be found inside `/src/main/scala/TEST_HERE` directory.

The last rocket-chip commit in which the given code builds is `5571442` (this branch is a fork from this specific commit). The given code on next commit `9a2d27b` won't compile. Please see the branch: [`RegisterRouterError`](https://github.com/milovanovic/rocket-chip/tree/RegisterRouterError).

## Generate Verilog

In order to start Verilog generation please run the script:
```bash
$ ./RUNME.sh
```
Or in the command line just type:
```bash
$ sbt "runMain axi4test.AXI4TestBlockApp"
```

## Example of error:

Please see branch: [`RegisterRouterError`](https://github.com/milovanovic/rocket-chip/tree/RegisterRouterError).
