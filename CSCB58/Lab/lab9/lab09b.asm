#Yulun Wu
.data
A: .word 5, 8, -3, 4, -7, 2, 33
B: .word 0:7 
.text
.globl main
# Store current offset in $t0 # Store total offset 28 in $t1 # Store address of A[i] in $t2, # Store address of B[i] in $t3
# Store value of current number in $t4
main:	
	la $t1, 28	# initialize total offset to 28 in $t1
	la $t2, A 	# load address of A[0] to $t2
	la $t3, B	# load address of B[0] to $t3
	li $t4, 0	# initialize value of current number to 0 in $t4
	
WHILE: blez $t1, EXIT	# If $t0 >= $t1, branch to EXIT
LOADA:	lw $t4, 0($t2)	# Load A[i] to $t4 from memory address stored in $t2
	addi $t2, $t2, 4	# Increase address in $t2 by 4
IF:	mul $t4, $t4, 5	# multiply $t4 by 5
	bgez $t4, ELSE	# If $t4 >= 0, branch to ELSE
	j STOREB
ELSE:	sll $t4, $t4, 1	# multiply $t4 by 2
STOREB:	sw $t4, 0($t3)	# Store number to B[i] in memory stored in $t3
	addi $t3, $t3, 4	# Increase address in $t3 by 4
	addi $t1, $t1, -4	# increase the current offset by 4
	j WHILE	# jump to WHILE
EXIT:		# Exit
	li $v0, 10
	syscall
	