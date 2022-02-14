package vexriscv

import spinal.core._

// 
class ISBNBlackbox() extends BlackBox {
  // Add VHDL Generics / Verilog parameters to the blackbox
  // You can use String, Int, Double, Boolean, and all SpinalHDL base
  // types as generic values
  /*
  addGeneric("wordCount", wordCount)
  addGeneric("wordWidth", wordWidth)
  */

  // Define IO of the VHDL entity / Verilog module
  val io = new Bundle {
      val clk = in Bool()
      val rs1 = in UInt(),
      val rd = out UInt()
  }
  /*
  val io = new Bundle {
    val clk = in Bool()
    val wr = new Bundle {
      val en   = in Bool()
      val addr = in UInt (log2Up(wordCount) bit)
      val data = in Bits (wordWidth bit)
    }
    val rd = new Bundle {
      val en   = in Bool()
      val addr = in UInt (log2Up(wordCount) bit)
      val data = out Bits (wordWidth bit)
    }
  }
  */

  // Map the current clock domain to the io.clk pin
  mapClockDomain(clock=io.clk)

  addRTLPath("../HE")
}