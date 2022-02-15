.globl _start
_start:

#define r_type_insn(_f7, _rs2, _rs1, _f3, _rd, _opc) \
.word (((_f7) << 25) | ((_rs2) << 20) | ((_rs1) << 15) | ((_f3) << 12) | ((_rd) << 7) | ((_opc) << 0))

#define ISBN(_rd, _rs1, _rs2 ) \
r_type_insn(0b0000011, _rs2, _rs1, 0b000, _rd, 0b0110011)

//8175257660
//Test 1
    li x28, 1
    li x1, 0x75257660
    li x2, 0x00000081
    ISBN(1, 1, 2)
    
    bne x1, x0, fail

// 0-306-40615-2
//Test 2
    li x28, 2
    li x1, 0x06406152
    li x2, 0x00000003
    li x3, 0x00000002
    ISBN(1, 1, 2)
    bne x1, x3, fail

// 1402894627
//Test 3
    li x28, 3
    li x1, 0x02894627
    li x2, 0x00000014
    li x3, 0x00000007
    ISBN(1, 1, 2)
    bne x3, x1, fail

// 1861972717
//Test 4
    li x28, 4
    li x1, 0x61972717
    li x2, 0x00000018
    li x3, 0x00000007
    ISBN(1, 1, 2)
    bne x3, x1, fail

// 0716703440
//Test 5
    li x28, 5
    li x1, 0x16703440
    li x2, 0x00000007
    li x3, 0x00000000
    ISBN(1, 1, 2)
    bne x3, x1, fail

// 0198526636
//Test 6
    li x28, 6
    li x1, 0x98526636
    li x2, 0x00000001
    li x3, 0x00000006
    ISBN(1, 1, 2)
    bne x3, x1, fail

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
