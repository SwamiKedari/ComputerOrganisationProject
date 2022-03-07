# procedure:    bubbleSort
# Objective:    sort an array of integer elements in nondecreasing order
# Input:        an address of an array of integers
# Output:       an array sorted in nondecreasing order
# Please observe the data segment before and after running the program to
# observe the sorted array

_start:

.data

array:      .byte 90, 67, 30 , 1, 45, 50, 11, 33, 67, 19, 2

.text


bubbleSort:
	la      x5, array
	li      x6, 0     # i = 0;
	li      x7, 0      # j = 0;
	li      x8, 11     # array length
loop:
	beq     x6, x8, exit       # exit if i == length of array -1
	la    	x5, array
	li      x7, 0      # j = 0;
	forLoop:
		beq     x7, x8, exitForLoop   # exit loop if j==length of array -1
		lw      x10, 0(x5)         # a0 = array[j]
		lw      x11, 4(x5)         # a1 = array[j+1]
		ble     x10, x11, update   # if array[j]<=array[j+1] skip
		sw      x11, 0(x5)         # a[j+1] = a[j]
		sw      x10, 4(x5)         # a[j] = a[j+1]
	update:
		addi    x7, x7, 1         # j++
		addi    x5, x5, 4        # point to next element -->
		j       forLoop
	exitForLoop:
		addi  x6, x6, 1  # i++;
		j   loop
exit:
	jr ra