_start:
.data
.text

li x5, 10
li x7, 20
sw x7, 4(x7)
lw x5, 0(x7)
addi x3,x7,10
bne x5, x6, loop1
sub x4, x5, x6
add x7, x5, x6 
loop1:  
    sub x28, x5, x6
subi x5,x6,10

