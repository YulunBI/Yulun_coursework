#Yulun Wu
.data
str1: .asciiz "Enter a value for A"
str2: .asciiz "Enter a value for B"
str3: .asciiz "Enter a value for C"
str4: .asciiz "Before function\n"
str5: .asciiz "A + B + C = "
str6: .asciiz "Num solutions for Ax^2 + Bx + C is = "
.text
.globl main
# Store A in $t0 # Store B in $t1 # Store C in $t2 # Store result in $t3
main:
	 li $t0, 0 	# Store A
     	 li $t1, 0	# Store B
     	 li $t2, 0	# Store C
     	 li $t3, 0	# Store result
     	 
     	 # Print "Enter a value for A" (address of string to print should be in $a0)
	li $v0, 4
	la $a0, str1
	syscall
	
	# Read number A (result will be in $v0)
	li $v0, 5
	syscall
	move $t0, $v0 # move number in $v0 to $t0
	
	# Print "Enter a value for B" (address of string to print should be in $a0)
	li $v0, 4
	la $a0, str2
	syscall
	
	# Read number B (result will be in $v0)
	li $v0, 5
	syscall
	move $t1, $v0 # move number in $v0 to $t1
	
	# Print "Enter a value for C" (address of string to print should be in $a0)
	li $v0, 4
	la $a0, str3
	syscall
	
	# Read number C (result will be in $v0)
	li $v0, 5
	syscall
	move $t2, $v0 # move number in $v0 to $t2
	
	# Print "Before function" (address of string to print should be in $a0)
	li $v0, 4
	la $a0, str4
	syscall
	
P1:	# Print "A + B + C = " (address of string to print should be in $a0)
	li $v0, 4
	la $a0, str5
	syscall
	
	
STOREPARAM1:	addi $sp, $sp, -12 # move top of stack to the address after save data to stack
		sw $t0, 0($sp)	# Save $t0 to stack
		sw $t1, 4($sp)	# Save $t1 to stack
		sw $t2, 8($sp)	# Save $t2 to stack
		
	
		jal do_addition # Access function do_addition
		
PRINTR1:	lw $t3, 0($sp)	# Load result returned by do_addition to $t3
		addi $sp, $sp, 4 # Move the top of stack down by 4
		# Print result stored in $a0
		li $v0, 1
		move $a0, $t3 # Move result to #a0
		syscall
		
P2:	# Print "Num solutions for Axˆ2 + Bx + C is = " (address of string to print should be in $a0)
	li $v0, 4
	la $a0, str6
	syscall
	
STOREPARAM2:	move $a0, $t0	# Move A to $a0
		move $a1, $t1	# Move B to $a1
		move $a2, $t2	# Move C to $a2
		
		jal n_solutions	# Access function n_solutions
		
PRINTR2:	# Print result stored in $a0
		move $a0, $v0 # Move result to #a0
		li $v0, 1
		syscall
		
EXIT:	# End program
	li $v0, 10
	syscall
		
do_addition: 
	lw $t4, 0($sp)	# Load data from stack, $t0 = A   # fix here C
	lw $t5, 4($sp)	# Load data from stack, $t1 = B
	lw $t6, 8($sp)	# Load data from stack, $t2 = C	  # fix here A
	addi $sp, $sp, 12 # Move the top of stack down by 12
	
COMPUTE1:add $t4, $t4, $t5 # $t0 = $t0 + B
	add $t4, $t4, $t6 # $t0 = $t0 + C
SAVES:	addi $sp, $sp, -4 # Move the top of stack up by 4
	sw $t4, 0($sp) # Save result to stack
	jr $ra	# Return to main

n_solutions: 
COMPUTE2:mul $t3, $a1, $a1 # $t3 = B^2
	sll $t4, $a0, 2 # $t4 = 4A
	mul $t4, $t4, $a2 # $t4 = 4AC
	sub $t3, $t3, $t4 # $t3 = b^2 - 4ac
IF: 	beqz $t3, ONESOL # If $t3 == 0, go to ONESOL
	bltz $t3, NOSOL	# If $t3 < 0, go to NOSOL
TWOSOL:	li $v0, 2 # There is two solution
	j RETURN # Jump to PRINTR
NOSOL:	li $v0, 0 # There is no real solution 
	j RETURN	# Jump to PRINTR
ONESOL:	li, $v0, 1 # There is one solution
	
RETURN:	jr $ra	# Return to main

		
