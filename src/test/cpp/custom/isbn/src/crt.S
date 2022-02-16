.globl _start
_start:

#define r_type_insn(_f7, _rs2, _rs1, _f3, _rd, _opc) \
.word (((_f7) << 25) | ((_rs2) << 20) | ((_rs1) << 15) | ((_f3) << 12) | ((_rd) << 7) | ((_opc) << 0))

#define ISBN(_rd, _rs1, _rs2 ) \
r_type_insn(0b0000011, _rs2, _rs1, 0b000, _rd, 0b0110011)

//8175257660
//Test 1
    li x28, 0xF1
    li x1, 0x75257660
    li x2, 0x00000081
    ISBN(1, 1, 2)
    or x28, x1, x0
    bne x1, x0, fail

// 0306406152
//Test 2
    li x28, 0xF2
    li x1, 0x06406152
    li x2, 0x00000003
    ISBN(1, 1, 2)
    bne x1, x0, fail

// 1402894627
//Test 3
    li x28, 0xF3
    li x1, 0x02894627
    li x2, 0x00000014
    ISBN(1, 1, 2)
    bne x0, x1, fail

// 1861972717
//Test 4
    li x28, 0xF4
    li x1, 0x61972717
    li x2, 0x00000018
    ISBN(1, 1, 2)
    bne x0, x1, fail

// 0716703440
//Test 5
    li x28, 0xF5
    li x1, 0x16703440
    li x2, 0x00000007
    ISBN(1, 1, 2)
    bne x0, x1, fail

// 0198526636
//Test 6
    li x28, 0xF6
    li x1, 0x98526636
    li x2, 0x00000001
    ISBN(1, 1, 2)
    bne x0, x1, fail

    j pass

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
