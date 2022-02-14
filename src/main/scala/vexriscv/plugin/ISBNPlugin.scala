package vexriscv.plugin

import spinal.core._
import vexriscv.plugin.Plugin
import vexriscv.{Stageable, DecoderService, VexRiscv}


class ISBNPlugin extends Plugin[VexRiscv]{
  //Define the concept of IS_SIMD_ADD signals, which specify if the current instruction is destined for this plugin
  object IS_ISBN extends Stageable(Bool)

  //Callback to setup the plugin and ask for different services
  override def setup(pipeline: VexRiscv): Unit = {
    import pipeline.config._

    //Retrieve the DecoderService instance
    val decoderService = pipeline.service(classOf[DecoderService])

    //Specify the IS_SIMD_ADD default value when instructions are decoded
    decoderService.addDefault(IS_ISBN, False)

    //Specify the instruction decoding which should be applied when the instruction matches the 'key' parttern
    decoderService.add(
      //Bit pattern of the new SIMD_ADD instruction
      key = M"0000011----------000-----0110011",

      //Decoding specification when the 'key' pattern is recognized in the instruction
      List(
        IS_ISBN                  -> True,
        REGFILE_WRITE_VALID      -> True, //Enable the register file write
        BYPASSABLE_EXECUTE_STAGE -> True, //Notify the hazard management unit that the instruction result is already accessible in the EXECUTE stage (Bypass ready)
        BYPASSABLE_MEMORY_STAGE  -> True, //Same as above but for the memory stage
        RS1_USE                  -> True, //Notify the hazard management unit that this instruction uses the RS1 value
        RS2_USE                  -> False  //Same as above but for RS2.
      )
    )
  }

  override def build(pipeline: VexRiscv): Unit = {
    import pipeline._
    import pipeline.config._

    //Add a new scope on the execute stage (used to give a name to signals)
    execute plug new Area {
      //Define some signals used internally by the plugin
      val rs1 = execute.input(RS1).asUInt

      // Blackbox goes up here

      val rd = UInt(32 bits)

      //Do some computations
      rd(31 downto 0) := (rs1(3 downto 0) + 2 * rs1(7 downto 4) + 3 * rs1(11 downto 8) + 4 * rs1(15 downto 12)) % 11

      //When the instruction is a SIMD_ADD, write the result into the register file data path.
      when(execute.input(IS_ISBN)) {
        execute.output(REGFILE_WRITE_DATA) := rd.asBits
      }
    }
  }
}