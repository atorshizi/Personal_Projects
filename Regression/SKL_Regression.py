import numpy as np
from sklearn.linear_model import SGDRegressor
from sklearn.pipeline import make_pipeline
from sklearn.preprocessing import StandardScaler
import matplotlib.pyplot as plt
import random as rand
import datetime as dt
class SKL_SGD:
    X = []
    Y = []
    labels = []
    Max_iter = 10000
    Tol = 0.001
    reg = None
    scaler = None
    def loadDataset(self,CSV):
        i = 0
        f = open(CSV,"r")
        for x in f:
            if (i != 0):
                split = x.split(",")
                self.Y.append(float(split[0]))
                xInt = []
                for i in range(1,len(split)):
                    xInt.append(float(split[i]))
                self.X.append(xInt)
            else:
                self.labels = x.split(",")
            i += 1
        f.close()
        return True
    def pickRandomTests(self):
        xTest, yTest = [], []
        rand.seed(0)
        for i in range(int(input("Enter Test Number: "))):
            j = rand.randint((dt.datetime.now().second),len(self.X)-1)
            xTest.append(self.X[j])
            yTest.append(self.Y[j])
            del self.X[j]
            del self.Y[j]
        return xTest,yTest
    def normalizeFeatures(self):
        self.scaler = StandardScaler()
        self.scaler.fit(self.X)
        xNorm = self.scaler.transform(self.X)
        self.X = xNorm
        return True
    def fitModel(self):
        self.reg = SGDRegressor(max_iter=self.Max_iter, tol=self.Tol)
        self.reg.fit(self.X,self.Y)
    def predict(self,sampleToPredict):
        return self.reg.predict(self.scaler.transform([sampleToPredict]))[0]
    def plotFeatures(self):
        x_temp = np.transpose(self.X)
        row = 3
        col = 6
        figure, axis = plt.subplots(row,col)
        for i in range(row):
            for j in range(col):
                axis[i,j].get_xaxis().set_ticks([])
                axis[i,j].get_yaxis().set_ticks([])
        for i in range(len(x_temp)):
            axis[i//(col),(i%(col))].scatter(x_temp[i],self.Y)
            axis[(i//col),(i%(col))].xaxis.set_label_text(self.labels[i+1])
        plt.show()