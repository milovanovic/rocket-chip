Demonstration of error when generating AXI4 pins
=====================
This repository is fork of rocket-chip. Only difference is added module that contains single AXI4 memory mapped register [TEST_CODE.scala](src/main/scala/TEST_HERE/TEST_CODE.scala) and [Node.scala](src/main/scala/TEST_HERE/Node.scala) file which is obtained from [here](https://github.com/ucb-bar/rocket-dsp-utils/blob/master/src/main/scala/freechips/rocketchip/amba/axi4/Node.scala). Both files can be found in `/src/main/scala/TEST_HERE` folder.

Code compilation fails for rocket-chip commit `9a2d27b` (this branch is fork of this commit). For previous commits, code compiles sucessfully. Please see branch: [`RegisterRouter`](https://github.com/milovanovic/rocket-chip/tree/RegisterRouter).

The error reported is:
```scala
[error] (run-main-0) chisel3.package$ExpectedChiselTypeException: wire type 'AXI4TestBlock.in.bits.extra: Wire[BundleMap]' must be a Chisel type, not hardware
[error] chisel3.package$ExpectedChiselTypeException: wire type 'AXI4TestBlock.in.bits.extra: Wire[BundleMap]' must be a Chisel type, not hardware
[error]         at ... ()
[error]         at freechips.rocketchip.amba.axi4.AXI4RegisterNode.$anonfun$regmap$16(RegisterRouter.scala:42)
[error]         at chisel3.internal.prefix$.apply(prefix.scala:48)
[error]         at freechips.rocketchip.amba.axi4.AXI4RegisterNode.$anonfun$regmap$15(RegisterRouter.scala:42)
[error]         at chisel3.internal.plugin.package$.autoNameRecursively(package.scala:33)
[error]         at freechips.rocketchip.amba.axi4.AXI4RegisterNode.regmap(RegisterRouter.scala:42)
[error]         at axi4test.AXI4TestBlock.regmap(TEST_CODE.scala:38)
[error]         at axi4test.TestBlock$$anon$1.<init>(TEST_CODE.scala:22)
[error]         at axi4test.TestBlock.$anonfun$module$1(TEST_CODE.scala:19)
[error]         at chisel3.internal.plugin.package$.autoNameRecursively(package.scala:33)
[error]         at axi4test.TestBlock.module$lzycompute(TEST_CODE.scala:19)
[error]         at axi4test.TestBlock.module(TEST_CODE.scala:19)
[error]         at axi4test.AXI4TestBlockApp$.$anonfun$new$2(TEST_CODE.scala:45)
[error]         at ... ()
[error] Nonzero exit code: 1

```

And it seems that error is due to the Wire of Wire in [RegisterRouter](https://github.com/chipsalliance/rocket-chip/blob/25e2c63/src/main/scala/amba/axi4/RegisterRouter.scala) or more concisely:

```scala
...
41. val in = Wire(Decoupled(new RegMapperInput(params)))
42. val ar_extra = Wire(in.bits.extra)
43. val aw_extra = Wire(in.bits.extra)
...
```

From the part of the linked code, it can be seen that ar_extra is Wire(Wire(...)) and it looks like that this is the reason for the error. Unfortunatelly, removing the additional Wire in lines 42 and 43 solves this problem but generates additional ones.

## Reproducing error

In order to start verilog generation run script:
```bash
$ ./RUNME.sh
```
Or in command line type:
```bash
$ sbt "runMain axi4test.AXI4TestBlockApp"
```

## Example of verilog code sucessfully generated:
Please see branch: [`RegisterRouter`](https://github.com/milovanovic/rocket-chip/tree/RegisterRouter) .


