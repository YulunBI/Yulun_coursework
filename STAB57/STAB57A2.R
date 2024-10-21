#1 
X=c(11, 13, 15, 17, 19, 21, 23)
d=expand.grid(X,X,X)
X_bar=apply(d,1,mean)
# 1(a)
sample_mean = mean(c(19,21,23))
test_statistic = pnorm(abs((sample_mean-17)/(sqrt(16/3))))
p_value = 2*(1-test_statistic)
sprintf("The p-value under CLT assumption is %f",p_value)

# 1(b)
# Code from A1
freq=table(X_bar)
freq
size_X_bar=length(X_bar)
freq_val=as.vector(freq)
proportion=freq_val/size_X_bar
proportion

p_value = sum(proportion[16:19])
sprintf("The p-value without CLT assumption is %f",p_value)

# 1(c)
# In (a), I assume that X_bar follows normal distribution under CLT and 
# calculated two-sided p-value.
# But in (b), I don't make this assumption, and sum up the proportion of 
# X_bar as X_bar >= 21, this is one-sided p-value. 
# Since the size of n is small, there is a difference between p-value/2(this 
# convert two-sided p-value to right sided p-value) in (a) and p-value in(b) 
# because CLT requires n ->inf, so a small n doesn't work good in CLT. 
# If the size of sample(as known as n) is big enough(such as n -> inf) and 
# both (a) and (b) calculate the same type of p-value, I expect that the 
# two p-values from (a) and (b) are similar.

# 2(a) i
sample_2a=function(){
  s=rnorm(5, mean = 10, sd = 10)
  X_bar = mean(s)
  L_theta0 = exp((-1/200)*sum((s-10)^2))*(200*pi)^(-5/2)
  L_theta1 = exp((-1/200)*sum((s-X_bar)^2))*(200*pi)^(-5/2)
  return(-2*log(L_theta0/L_theta1))
}

# 2(a) ii
LRT_vec = replicate(10000,sample_2a())
# 2(a) iii
LRT_vec_hist = hist(LRT_vec,freq=FALSE, breaks=100)
LRT_vec_hist
# 2(a) iv
chisq_sample = rchisq(100000, df = 1)
lines(density(chisq_sample))

# 2(b) i
sample_2b=function(){
  s=rpois(5, lambda = 10)
  X_bar = mean(s)
  L_theta0 = prod(exp(-10)*10^s/factorial(s))
  L_theta1 = prod(exp(-X_bar)*X_bar^s/factorial(s))
  return(-2*log(L_theta0/L_theta1))
}

# 2(b) ii
LRT_vec = replicate(100000,sample_2b()) 
# 2(b) iii
LRT_vec_hist = hist(LRT_vec,freq=FALSE, breaks=100)
LRT_vec_hist
# 2(b) iv
lines(density(chisq_sample))

# 2(c)
# The the histograms and the density are close to each other doesn't 
# matter the simple size is big or small.

# 3(a)
sample_q3 = c(11, 12, 8, 5, 11)
# Score
l_p_theta = function(t){-5+sum(sample_q3)/t}
# double differentiation
l_pp_theta = function(t){-sum(sample_q3)/t^2}
t_old = 4.5
iter = 1
dif = 1
while(dif>0.00001){
  t_new = t_old - l_p_theta(t_old)/l_pp_theta(t_old)
  print(t_new)
  dif = abs(t_new - t_old)
  t_old = t_new
  iter = iter + 1
}
sprintf("After %d iteration, the MLE of lambda is %f", iter-1, t_old)

# 3(b)
# -E[double differentiation]
e_pp_theta = function(t){5/t}
t_old = 4.5
iter = 1
dif = 1
while(dif>0.00001){
  t_new = t_old + l_p_theta(t_old)/e_pp_theta(t_old)
  print(t_new)
  dif = abs(t_new - t_old)
  t_old = t_new
  iter = iter + 1
}
sprintf("After %d iteration, the MLE of lambda is %f", iter-1, t_old)

# 3(c)
# The updating equation in (a) sometimes fail to give me the solution,
# and takes more iteration to converge. For example, (a) fails to converge 
# when initial guess is 25, and when initial guess is 4.5, (a) takes 6 
# iteration to run while (b) only takes 2 iteration.