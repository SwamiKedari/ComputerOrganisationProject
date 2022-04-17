#dalkdkal

_start:
#dala

.data

array: .byte 90, 67, 30, 1, 45, 50, 11, 33, 67, 19, 2
.text

li x5, 4
li x6, 20

Add: 
    add x7, x5, x16
beq x5, x6, loop
sub x5, x7, x6
loop:
    add x28, x5, x6
j loop2
addi x5,x5,34
li x8 , 10
loop2:
    li x6, 20
subi x5,x1 ,10

