.globl _start
_start:

#define r_type_insn(_f7, _rs2, _rs1, _f3, _rd, _opc) \
.word (((_f7) << 25) | ((_rs2) << 20) | ((_rs1) << 15) | ((_f3) << 12) | ((_rd) << 7) | ((_opc) << 0))

#define IS_ISBN (_rd, _rs1, _rs2 ) \
r_type_insn(0b0000011, _rs2, _rs1, 0b000, _rd, 0b0110011)

//Test 1
    li x28, 1
    IS_ISBN(1, 0, 0)
    bne x1, x0, fail

fail: //x28 => error code
    li x2, 0xF00FFF24
    sw x28, 0(x2)

pass:
    li x2, 0xF00FFF20
    sw x0, 0(x2)



    nop
    nop
    nop
    nop
    nop
    nop
