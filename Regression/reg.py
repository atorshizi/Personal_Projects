import numpy as np
from sklearn.linear_model import SGDRegressor
from sklearn.pipeline import make_pipeline
from sklearn.preprocessing import StandardScaler
import matplotlib.pyplot as plt
import random as rand
import decimal

f = open(input("Enter CSV File Adress: "))
i = 0
X, Y = [], []
labels = []
for x in f:
    if (i != 0):
        split = x.split(",")
        Y.append(float(split[0]))
        xInt = []
        for i in range(1,len(split)):
            xInt.append(float(split[i]))
        X.append(xInt)
    else:
        labels = x.split(",")
    i += 1
f.close()


xTest, yTest = [], []
rand.seed(0)
for i in range(int(input("Enter Test Number: "))):
    j = rand.randint(0,len(X)-1)
    xTest.append(X[j])
    yTest.append(Y[j])
    del X[j]
    del Y[j]

    
scaler = StandardScaler()
scaler.fit(X)
xNorm = scaler.transform(X)
for sample in xNorm:
    arrToAdd = []
    for feature in sample:
        arrToAdd.append((pow(feature,2)))
    np.append(sample,[[arrToAdd]])
reg = SGDRegressor(max_iter=10000, tol=0.001)
reg.fit(xNorm,Y)
# size = int(input("Enter Size: "))
# bed = int(input("Enter Bedroos: "))
# bath = float(input("Enter Bathrooms: "))
# year = int(input("Enter Year: "))
# print(round(reg.predict(scaler.transform([[size,bed,bath,year]]))[0],-2))

sum = 0
totalNum = 0
for elem in xTest:
    predicted = reg.predict(scaler.transform([elem]))[0]
    sum += abs((predicted - yTest[totalNum])/yTest[totalNum])
    print("Predicted: " + str(predicted) + "   Actual: " + str(yTest[totalNum]))
    totalNum += 1
avgDiff = sum/totalNum
print("Average Margin of Error: " + str(avgDiff))


if (input("Show Plots? ") == "y"):
    npArr = np.array(X)
    X = np.transpose(npArr)
    row = 3
    col = 6
    figure, axis = plt.subplots(row,col)
    for i in range(row):
        for j in range(col):
            axis[i,j].get_xaxis().set_ticks([])
            axis[i,j].get_yaxis().set_ticks([])
    for i in range(len(X)):
        axis[i//(col),(i%(col))].scatter(X[i],Y)
        axis[(i//col),(i%(col))].xaxis.set_label_text(labels[i+1])
    plt.show()
# C:\\Users\\ators\\Documents\\Personal_Projects\\Regression\\kc_house_data.csv