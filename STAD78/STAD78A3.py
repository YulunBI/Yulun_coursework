# To add a new cell, type '# %%'
# To add a new markdown cell, type '# %% [markdown]'
# %% [markdown]
# # A3

# %%
# Import packages
import matplotlib.pyplot as plt
import numpy as np
from matplotlib.pyplot import figure
from mpl_toolkits.mplot3d import Axes3D
from pprint import pprint


# %%
def generate_data(d, n):
    # x_0' 
    x = np.random.normal(0,1,(1,d))
    x = x/np.linalg.norm(x)
    x = x[0]
    # Generate random Gaussian sample and normalize it
    z = np.random.normal(0,1,(n,d))  
    norm = np.linalg.norm(z,axis=1)
    z = z/norm.reshape(-1,1)
    y = np.sign(np.inner(x,z))
    return (z,y,x)

# %% [markdown]
# ## E3.1
# 

# %%
np.random.seed(100)
d = 2
N = 40
(z,y,x_0) = generate_data(d,N)
print(x_0)
# Calculate lambda and x_star
lambda_0 = np.amin(y*np.inner(x_0,z))
x_star = x_0/lambda_0
print(lambda_0)
print(x_star)

# Scatter plot of data points
area1 = np.ma.masked_where(y < 0,y,copy=True)
area2 = np.ma.masked_where(y > 0,y,copy=True)
plt.scatter(z[:,0],z[:,1], s=area1*35, marker="+", c='blue')
plt.scatter(z[:,0],z[:,1], s=(-1)*area2*35, marker="_", c='red')
plt.show()
plt.plot(x_star[0], x_star[1], marker="o", markersize=10, markeredgecolor="yellow")
plt.show()

# %% [markdown]
# ## E3.2

# %%
def gradient(yt,zt,x):
    if (1-yt*np.inner(zt,x) <= 0):
        return 0
    return -yt*zt


# %%
def SGD(T, z,y,learning_rate):
    n = np.shape(z)[0]
    d = np.shape(z)[1]
    x = np.zeros(d)
    # Setting of plots
    fig, axs= plt.subplots(2)
    axs[0].set_title('Number of rounds v.s. Empirical risk')
    axs[0].set_xlabel("T")
    axs[0].set_ylabel("Empirical risk")
    axs[1].set_title('The cumulative loss of SGD v.s. ||x_star||') 
    axs[1].set_ylabel("||x_star||")
    axs[1].set_xlabel("The cumulative loss of SGD")
    fig.set_size_inches(10, 10)
    # Loop for T times
    for t in range(T):
        random_index = np.random.randint(0,n)
        zt = z[random_index,:]
        yt_real = y[random_index]
        cl_SGD = 0
        #print(i,x)
        yt_predicted = np.sign(np.inner(x,zt))
        #diff_loss = diff_loss-learning_rate*gradient(yt_real,zt,x) #np.max(0,1-yt_real*np.inner(zt,x))
        x = x-learning_rate*gradient(yt_real,zt,x)
        # x_star and the cumulative loss of SGD, then plot x_star against the cumulative loss of SGD
        if (t == 0):
            lambda_0 = yt_real*np.inner(x,zt)
        else:
            lambda_0 = np.minimum(lambda_0,yt_real*np.inner(x,zt))
        x_star = x/lambda_0
        norm_x_star = np.linalg.norm(x_star)
        cl_SGD += np.maximum(0.0,1-yt_real*np.inner(zt,x))
        axs[1].scatter(cl_SGD,norm_x_star,c='blue')
        if (t%5 == 0):
            # For every 5 rounds, compute empeirical risk for the whole dataset, p;ot it #(rounds) against it
            empeirical_risk = np.sum(np.fmax(np.zeros(n),1-y*np.inner(z,x)))/n
            #plt.scatter(t,emperial_risk,c='blue')
            axs[0].scatter(t,empeirical_risk,c='blue')
        #plt.scatter(x[0],x[1])
    plt.show()
    return x
        


# %%

T = 200
aita = 1/np.sqrt(T)
result = SGD(T,z,y,aita)
print(result) # This is the final x

# %% [markdown]
# The second plot looks like more points are condensed in loss=0, which remaind me that Regret converges as T increases.
# %% [markdown]
# ## E3.3
# 

# %%
d = 2
fig, axs= plt.subplots(1)
axs.set_title('log(Number of data) v.s. log(||x_star||)')
axs.set_xlabel("log(N)")
axs.set_ylabel("log(||x_star||)")
for n in range(1,100):
    (z,y,x_0) = generate_data(d,n)
    lambda_0 = np.amin(y*np.inner(x_0,z))
    x_star = x_0/lambda_0
    norm_x_star = np.linalg.norm(x_star)
    axs.scatter(np.log2(n),np.log2(norm_x_star),c='blue')
plt.show()

# %% [markdown]
# The order of growth of ||x_star|| is about linear.
# Bound of Regret of SGD applied to the empirical risk minimization problem is sqrt(T)(R||x_star||^2+1)/2. R = 1 in our case, so this is sqrt(T)(||x_star||^2+1)/2 => sqrt(T)*quadratic, which fail to be sublinear.
# The geometric meaning of the inverse of the norm of x_star is the inversed distance between the center of unit sphere and x_star.
# %% [markdown]
# ## E3.4

# %%
def generate_data_modified(d, n, p):
    # x_0' 
    x = np.random.normal(0,1,(1,d))
    x = x/np.linalg.norm(x)
    x = x[0]
    # Generate random Gaussian sample and normalize it
    counter = 0 # Count #(datapoints in dataset)
    z = np.empty((n,d))
    y = np.empty(n)
    while counter < n:
        zi = np.random.normal(0,1,(1,d)).reshape(-1,d)  
        norm = np.linalg.norm(zi)
        zi = zi/norm
        yi = np.sign(np.inner(x,zi))
        if (np.absolute(np.inner(x,zi)) > p):
            z[counter] = zi
            y[counter] = yi
            counter += 1
    return (z,y,x)


# %%
np.random.seed(100)
N = 200
d = 2
p = 0.5
(z,y,x_0) = generate_data_modified(d,N,p)
print(x_0)
# Calculate lambda and x_star
lambda_0 = np.min(y*np.inner(x_0,z))
x_star = x_0/lambda_0
print(lambda_0)
print(x_star)

# Scatter plot of data points
area1 = np.ma.masked_where(y < 0,y,copy=True)
area2 = np.ma.masked_where(y > 0,y,copy=True)
plt.scatter(z[:,0],z[:,1], s=area1*35, marker="+", c='blue')
plt.scatter(z[:,0],z[:,1], s=(-1)*area2*35, marker="_", c='red')
plt.plot(x_star[0], x_star[1], marker="o", markersize=10, markeredgecolor="yellow")
plt.show()

# %% [markdown]
# ## E3.5

# %%
np.random.seed(100)
N = 40
d = 2
T = 200
aita = 1/np.sqrt(T)
p = np.array([0.0,0.1,0.5,0.9])
for i in p:
    (z,y,x_0) = generate_data_modified(d,N,i)
    result = SGD(T,z,y,aita)

# %% [markdown]
# As the value of p increase, the number of mistake that SGD makes decreases since more points are condensed at loss=0 as p increases implies less mistake are made in SGD as p increases.
# %% [markdown]
# ## E3.6
# 

# %%



