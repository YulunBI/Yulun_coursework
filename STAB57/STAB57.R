x=6
x+10
y=x*2

#ln
log(100)
#log base 10
log10(100)

#list
x=c(2,4,6)
x+2

#list(include 10)
x=c(1:10)
x+1

#sequence
x=seq(1,10,by=2)
#index
x[1]
x[c(1,3)]
#everything except index 1
x[-1]
x+2

#repeat
x=rep(1,5)
x+1

#random without duplicate
sample(c(1:6),size=3)

#random with duplicate
sample(c(1:6),size=7,replace=TRUE)

#generate normal distribution sample(default is Z)
set.seed(999) #generate same random number everytime,can be any number in ()
s=rnorm(10,mean=10,sd=2)
mean(s)
sd(s)
min(s)

#die in for loop
set.seed(2021)
for (i in 1:300){
  r=sample(c(1:6),size=i,replace=TRUE)
  print(mean(r))
}

#function
func_name=function(param){
  x=param^2
  return(x)
}

#calling func
func_name() #gives error
func_name(3) #gives 9

#func with 2 param
func2=function(x,y){
  z=x+y
  return(z)
}

#call func2
func2(4,8) #gives 12

#function with no param
func3=function(){
  x=3+2
  return(x)
}

#call func3
func3() #gives 5

#func4 generate sample size of 30 and calculate sample mean
func4=function(){
  s=rnorm(30,mean=10,sd=2)
  return(mean(s))
}

replicate(5,func4()) #repeat func4 for 5 times
x_bar=replicate(1000000,func4())
mean(x_bar)
plot(density(x_bar))
var(x_bar) #sd^2/30


#CLT example with poission distribution
func4=function(){
  s=rpois(30,lambda=15)
  return(mean(s))
}

y_bar=replicate(1000000,func4())
mean(y_bar)
plot(density(y_bar))
var(y_bar) #about 0.5, b|c for poission, mean=var=15,15/30=0.5

#varience of sample
func4=function(){
  s=rnorm(30,mean=10,sd=2)
  return(var(s))
}

replicate(5,func4()) #repeat func4 for 5 times
x_bar=replicate(1000000,func4())
mean(x_bar)
plot(density(x_bar))

#function calculate score and mle from repeated sample
score_function=function(){
  s=rpois(30,lambda=5)
  score=-length(s) + sum(s)/5 # -n+(sum(x)/lambda)
  mle=mean(s) # mle of pois is sample mean (X bar)
  return(c(mle,score))
}

mle_score_vec=replicate(100000,score_function())
# mle=X_bar for pois
# first row. close to 5 E[X_bar]
mean(mle_score_vec[1,])
# first row. close to 5/30 Var[X_bar]
var(mle_score_vec[1,])
# density of sample mean
plot(density(mle_score_vec[1,]),col="red")
#summary(mle_score_vec)
cov(mle_score_vec[1,],mle_score_vec[2,]) # cov[T,Z]=1 from slide


# A matrix
x= matrix(c(2,4,7,9,10,12),nrow=3)
x
# A matrix
x= matrix(c(2,4,7,9,10,12),nrow=3,byrow=TRUE)
x
# row wise operation (A1)
apply(x,1,mean)
apply(x,1,sum)
apply(x,1,sd)

# column wise operation
apply(x,2,mean)
apply(x,2,sum)
apply(x,2,sd)

