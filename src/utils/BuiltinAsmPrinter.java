package utils;

import java.io.FileOutputStream;
import java.io.IOException;


public class BuiltinAsmPrinter {
  String builtin_s_code = """
	.file	"builtin.c"
	.option nopic
	.attribute arch, "rv32i2p0_m2p0_a2p0_f2p0_d2p0_c2p0"
	.attribute unaligned_access, 0
	.attribute stack_align, 16
	.text
	.section	.rodata.str1.4,"aMS",@progbits,1
	.align	2
.LC0:
	.string	"%s"
	.text
	.align	1
	.globl	print
	.type	print, @function
print:
	mv	a1,a0
	lui	a0,%hi(.LC0)
	addi	a0,a0,%lo(.LC0)
	tail	printf
	.size	print, .-print
	.align	1
	.globl	println
	.type	println, @function
println:
	tail	puts
	.size	println, .-println
	.section	.rodata.str1.4
	.align	2
.LC1:
	.string	"%d"
	.text
	.align	1
	.globl	printInt
	.type	printInt, @function
printInt:
	mv	a1,a0
	lui	a0,%hi(.LC1)
	addi	a0,a0,%lo(.LC1)
	tail	printf
	.size	printInt, .-printInt
	.section	.rodata.str1.4
	.align	2
.LC2:
	.string	"%d\\n"
	.text
	.align	1
	.globl	printlnInt
	.type	printlnInt, @function
printlnInt:
	mv	a1,a0
	lui	a0,%hi(.LC2)
	addi	a0,a0,%lo(.LC2)
	tail	printf
	.size	printlnInt, .-printlnInt
	.align	1
	.globl	getString
	.type	getString, @function
getString:
	addi	sp,sp,-16
	li	a0,256
	sw	ra,12(sp)
	sw	s0,8(sp)
	call	malloc
	mv	s0,a0
	mv	a1,a0
	lui	a0,%hi(.LC0)
	addi	a0,a0,%lo(.LC0)
	call	scanf
	lw	ra,12(sp)
	mv	a0,s0
	lw	s0,8(sp)
	addi	sp,sp,16
	jr	ra
	.size	getString, .-getString
	.align	1
	.globl	getInt
	.type	getInt, @function
getInt:
	addi	sp,sp,-32
	lui	a0,%hi(.LC1)
	addi	a1,sp,12
	addi	a0,a0,%lo(.LC1)
	sw	ra,28(sp)
	call	scanf
	lw	ra,28(sp)
	lw	a0,12(sp)
	addi	sp,sp,32
	jr	ra
	.size	getInt, .-getInt
	.align	1
	.globl	toString
	.type	toString, @function
toString:
	addi	sp,sp,-16
	sw	s1,4(sp)
	mv	s1,a0
	li	a0,16
	sw	ra,12(sp)
	sw	s0,8(sp)
	call	malloc
	lui	a1,%hi(.LC1)
	mv	a2,s1
	addi	a1,a1,%lo(.LC1)
	mv	s0,a0
	call	sprintf
	lw	ra,12(sp)
	mv	a0,s0
	lw	s0,8(sp)
	lw	s1,4(sp)
	addi	sp,sp,16
	jr	ra
	.size	toString, .-toString
	.align	1
	.globl	__mx_substring
	.type	__mx_substring, @function
__mx_substring:
	addi	sp,sp,-32
	sw	s0,24(sp)
	sub	s0,a2,a1
	sw	s4,8(sp)
	mv	s4,a0
	addi	a0,s0,1
	sw	s1,20(sp)
	sw	s2,16(sp)
	sw	s3,12(sp)
	sw	ra,28(sp)
	mv	s1,a1
	mv	s3,a2
	call	malloc
	mv	s2,a0
	ble	s3,s1,.L13
	mv	a2,s0
	add	a1,s4,s1
	call	memcpy
.L13:
	add	s0,s2,s0
	sb	zero,0(s0)
	lw	ra,28(sp)
	lw	s0,24(sp)
	lw	s1,20(sp)
	lw	s3,12(sp)
	lw	s4,8(sp)
	mv	a0,s2
	lw	s2,16(sp)
	addi	sp,sp,32
	jr	ra
	.size	__mx_substring, .-__mx_substring
	.align	1
	.globl	__mx_parseInt
	.type	__mx_parseInt, @function
__mx_parseInt:
	addi	sp,sp,-32
	lui	a1,%hi(.LC1)
	addi	a2,sp,12
	addi	a1,a1,%lo(.LC1)
	sw	ra,28(sp)
	call	sscanf
	lw	ra,28(sp)
	lw	a0,12(sp)
	addi	sp,sp,32
	jr	ra
	.size	__mx_parseInt, .-__mx_parseInt
	.align	1
	.globl	__mx_ord
	.type	__mx_ord, @function
__mx_ord:
	add	a0,a0,a1
	lbu	a0,0(a0)
	ret
	.size	__mx_ord, .-__mx_ord
	.align	1
	.globl	__mx_stradd
	.type	__mx_stradd, @function
__mx_stradd:
	addi	sp,sp,-32
	sw	ra,28(sp)
	sw	s0,24(sp)
	sw	s1,20(sp)
	sw	s2,16(sp)
	sw	s3,12(sp)
	sw	s4,8(sp)
	mv	s2,a1
	mv	s4,a0
	call	strlen
	mv	s0,a0
	mv	a0,s2
	call	strlen
	mv	s3,a0
	add	a0,s0,a0
	addi	a0,a0,1
	call	malloc
	mv	a2,s0
	mv	a1,s4
	mv	s1,a0
	call	memcpy
	add	a0,s1,s0
	addi	a2,s3,1
	mv	a1,s2
	call	memcpy
	lw	ra,28(sp)
	lw	s0,24(sp)
	lw	s2,16(sp)
	lw	s3,12(sp)
	lw	s4,8(sp)
	mv	a0,s1
	lw	s1,20(sp)
	addi	sp,sp,32
	jr	ra
	.size	__mx_stradd, .-__mx_stradd
	.align	1
	.globl	__mx_strlt
	.type	__mx_strlt, @function
__mx_strlt:
	addi	sp,sp,-16
	sw	ra,12(sp)
	call	strcmp
	lw	ra,12(sp)
	srli	a0,a0,31
	addi	sp,sp,16
	jr	ra
	.size	__mx_strlt, .-__mx_strlt
	.align	1
	.globl	__mx_strle
	.type	__mx_strle, @function
__mx_strle:
	addi	sp,sp,-16
	sw	ra,12(sp)
	call	strcmp
	lw	ra,12(sp)
	slti	a0,a0,1
	addi	sp,sp,16
	jr	ra
	.size	__mx_strle, .-__mx_strle
	.align	1
	.globl	__mx_strgt
	.type	__mx_strgt, @function
__mx_strgt:
	addi	sp,sp,-16
	sw	ra,12(sp)
	call	strcmp
	lw	ra,12(sp)
	sgt	a0,a0,zero
	addi	sp,sp,16
	jr	ra
	.size	__mx_strgt, .-__mx_strgt
	.align	1
	.globl	__mx_strge
	.type	__mx_strge, @function
__mx_strge:
	addi	sp,sp,-16
	sw	ra,12(sp)
	call	strcmp
	lw	ra,12(sp)
	not	a0,a0
	srli	a0,a0,31
	addi	sp,sp,16
	jr	ra
	.size	__mx_strge, .-__mx_strge
	.align	1
	.globl	__mx_streq
	.type	__mx_streq, @function
__mx_streq:
	addi	sp,sp,-16
	sw	ra,12(sp)
	call	strcmp
	lw	ra,12(sp)
	seqz	a0,a0
	addi	sp,sp,16
	jr	ra
	.size	__mx_streq, .-__mx_streq
	.align	1
	.globl	__mx_strneq
	.type	__mx_strneq, @function
__mx_strneq:
	addi	sp,sp,-16
	sw	ra,12(sp)
	call	strcmp
	lw	ra,12(sp)
	snez	a0,a0
	addi	sp,sp,16
	jr	ra
	.size	__mx_strneq, .-__mx_strneq
	.ident	"GCC: (g2ee5e430018) 12.2.0"
	.section	.note.GNU-stack,"",@progbits	
""";
  public BuiltinAsmPrinter(String fileName) throws IOException {
    FileOutputStream out = new FileOutputStream(fileName);
    out.write(builtin_s_code.getBytes());
    out.close();
  }
}
