#Yulun Wu
#wuyulu10
.text
.global main
# a is stored in $t0     b is stored in $t1     
main:	 li $t0, 55	# a
     	 li $t1, 77	# b
     	 li $t9, 0 	# Store result
WHILE:	beq $t0, $t1, EXIT # if $t0 == $t1, exit while loop
IF: 	ble $t0, $t1, ELSE # If a <= b, branch to ELSE 
	sub $t0, $t0, $t1  # a = a - b
	j WHILE
ELSE:	sub $t1, $t1, $t0  # b = b - a
	j WHILE
EXIT:	move $t9, $t0	# Exit and store GCD in $t9
	li $v0, 10
	syscall