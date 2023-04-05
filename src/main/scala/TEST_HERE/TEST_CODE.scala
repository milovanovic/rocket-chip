// SPDX-License-Identifier: Apache-2.0

package axi4test

import chisel3.stage.{ChiselGeneratorAnnotation, ChiselStage}
import chisel3._
import freechips.rocketchip.amba.axi4._
import freechips.rocketchip.config.Parameters
import freechips.rocketchip.diplomacy._
import freechips.rocketchip.regmapper._

/* Mixin for adding CSRs to */
trait HasCSR {
  def regmap(mapping: RegField.Map*)
  def addCSR(address: Int, field: Seq[RegField]): Unit = { }
}

abstract class TestBlock[D, U, E, O, B <: Data] extends LazyModule()(Parameters.empty) with HasCSR {
  lazy val module = new LazyModuleImp(this) {
    val regTest = RegInit(0.U(32.W))
    regTest := regTest + 1.U
    regmap(0x0 -> Seq(RegField.r(32, regTest)))
  }
}

trait AXI4TestBlockStandalone extends AXI4TestBlock {
  def standaloneParams = AXI4BundleParameters(addrBits = 32, dataBits = 32, idBits = 1)
  val ioMem = mem.map { m => {
    val ioMemNode = BundleBridgeSource(() => AXI4Bundle(standaloneParams))
    m := BundleBridgeToAXI4(AXI4MasterPortParameters(Seq(AXI4MasterParameters("bundleBridgeToAXI4")))) := ioMemNode
    val ioMem = InModuleBody { ioMemNode.makeIO() }
    ioMem
  }}
}

class AXI4TestBlock (csrAddress: AddressSet)(implicit p: Parameters) extends TestBlock[AXI4MasterPortParameters, AXI4SlavePortParameters, AXI4EdgeParameters, AXI4EdgeParameters, AXI4Bundle] {
  val mem = Some(AXI4RegisterNode(address = csrAddress, beatBytes = 4))
  override def regmap(mapping: (Int, Seq[RegField])*): Unit = mem.get.regmap(mapping:_*)
}


object AXI4TestBlockApp extends App {
  implicit val p: Parameters = Parameters.empty
  val lazyDut = LazyModule(new AXI4TestBlock(csrAddress = AddressSet(0x2000, 0xff)) with AXI4TestBlockStandalone)
  (new ChiselStage).execute(Array("--target-dir", "verilog/TestBlock"), Seq(ChiselGeneratorAnnotation(() => lazyDut.module)))
}
