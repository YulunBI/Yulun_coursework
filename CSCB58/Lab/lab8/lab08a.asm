#Yulun Wu
#wuyulu10
.text
.global main
# a is stored in $t0     b is stored in $t1     c is stored in $t2
main:	 li $t0, 2	# a
     	 li $t1, 5	# b
     	 li $t2, 1	# c
     	 li $t3, 0 	# Store temp value
     	 li $t4, 0	# Store temp value
     	 li $t5, 0	# Store result
COMPUTE:mul $t3, $t1, $t1 # $t3 = b^2
	mul $t4, $t0, 4 # $t4 = 4a
	mul $t4, $t4, $t2 # $t4 = 4ac
	sub $t3, $t3, $t4 # $t3 = b^2 - 4ac
IF: 	beqz $t3, ONESOL # If $t3 == 0, go to ONESOL
	bltz $t3, NOSOL	# If $t3 < 0, go to NOSOL
TWOSOL:	li $t5, 2 # There is two solution
	j EXIT # Jump to EXIT
NOSOL:	li $t5, 0 # There is no real solution 
	j EXIT	# Jump to EXIT
ONESOL:	li, $t5, 1 # There is one solution
EXIT:		# Exit
	li $v0, 10
	syscall
