#Yulun Wu
.data
str1: .asciiz "Enter 3 numbers: a, b and c!\n"
str2: .asciiz "There are "
str3: .asciiz " solutions for ax^2+bx+c\n"
.text
.globl main
# Store a in $t0 # Store b in $t1 # Store c in $t2
main:
	 li $t3, 0 	# Store temp value
     	 li $t4, 0	# Store temp value
     	 li $t5, 0	# Store result
     	 
PRINTQ:	# Print "Enter 3 numbers: a, b and c" (address of string to print should be in $a0)
	li $v0, 4
	la $a0, str1
	syscall
	
READ:	# Read number a (result will be in $v0)
	li $v0, 5
	syscall
	move $t0, $v0 # move number in $v0 to $t0
	
	# Read number b (result will be in $v0)
	li $v0, 5
	syscall
	move $t1, $v0 # move number in $v0 to $t1
	
	# Read number c (result will be in $v0)
	li $v0, 5
	syscall
	move $t2, $v0 # move number in $v0 to $t2
	
COMPUTE:mul $t3, $t1, $t1 # $t3 = b^2
	sll $t4, $t0, 2 # $t4 = 4a
	mul $t4, $t4, $t2 # $t4 = 4ac
	sub $t3, $t3, $t4 # $t3 = b^2 - 4ac
IF: 	beqz $t3, ONESOL # If $t3 == 0, go to ONESOL
	bltz $t3, NOSOL	# If $t3 < 0, go to NOSOL
TWOSOL:	li $t5, 2 # There is two solution
	j PRINTR # Jump to PRINTR
NOSOL:	li $t5, 0 # There is no real solution 
	j PRINTR	# Jump to PRINTR
ONESOL:	li, $t5, 1 # There is one solution
PRINTR:		
	# Print result
	li $v0, 4
	la $a0, str2 # Print "There are "
	syscall
	li $v0, 1
	move $a0, $t5 # Print "N" (value of N is stored in $a0)
	syscall
	li $v0, 4
	la $a0, str3 # Print " solutions for ax^2+bx+c"
	syscall
	                                                                                                      
	# End program
	li $v0, 10
	syscall
