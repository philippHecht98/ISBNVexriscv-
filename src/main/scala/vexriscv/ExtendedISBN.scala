package vexriscv

import vexriscv.plugin._
import vexriscv.ip.{DataCacheConfig, InstructionCacheConfig}
import vexriscv.{plugin, VexRiscv, VexRiscvConfig}
import spinal.core._

/**
 * Created by spinalvm on 15.06.17.
 */
object ExtendedISBN extends App{
  def config = VexRiscvConfig(
    plugins = List(
        new IBusSimplePlugin(
          resetVector = 0x80000000l,
          cmdForkOnSecondStage = false,
          cmdForkPersistence = false,
          prediction = NONE,
          catchAccessFault = false,
          compressedGen = false
        ),
        new DBusSimplePlugin(
          catchAddressMisaligned = false,
          catchAccessFault = false
        ),
        new CsrPlugin(CsrPluginConfig.smallest),
        new DecoderSimplePlugin(
          catchIllegalInstruction = false
        ),
        new RegFilePlugin(
          regFileReadyKind = plugin.SYNC,
          zeroBoot = false
        ),
        new IntAluPlugin,
        new MulPlugin,
        new SrcPlugin(
          separatedAddSub = false,
          executeInsertion = false
        ),
        new LightShifterPlugin,
        new HazardSimplePlugin(
          bypassExecute           = false,
          bypassMemory            = false,
          bypassWriteBack         = false,
          bypassWriteBackBuffer   = false,
          pessimisticUseSrc       = false,
          pessimisticWriteRegFile = false,
          pessimisticAddressMatch = false
        ),
        new BranchPlugin(
          earlyBranch = false,
          catchAddressMisaligned = false
        ),
        new YamlPlugin("cpu0.yaml"),
        new ISBNPlugin()
    )
  )

  def cpu() = new VexRiscv(
    config
  )

  SpinalVerilog(cpu())
}
