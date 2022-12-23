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
    def plotFeatures(self,row,col):
        if ((row == 0) or (col == 0)):
            return False
        x_temp = np.transpose(self.X)
        figure, axis = plt.subplots(row,col)
        plotNum = 0
        if((row > 1) and (col > 1)):
            for i in range(row):
                for j in range(col):
                    axis[i,j].get_xaxis().set_ticks([])
                    axis[i,j].get_yaxis().set_ticks([])
                    if ((plotNum) < len(x_temp[0])):
                        axis[(i),(j)].scatter(x_temp[plotNum],self.Y)
                        axis[(i),(j)].xaxis.set_label_text(self.labels[plotNum+1])
                        plotNum += 1
        elif((row > 1) and (col <= 1)):
            for i in range(row):
                axis[i].get_xaxis().set_ticks([])
                axis[i].get_yaxis().set_ticks([])
                if ((plotNum) < len(x_temp[0])):
                    axis[(i)].scatter(x_temp[plotNum],self.Y)
                    axis[(i)].xaxis.set_label_text(self.labels[plotNum+1])
                    plotNum += 1
        else:
            for i in range(col):
                axis[i].get_xaxis().set_ticks([])
                axis[i].get_yaxis().set_ticks([])
                if ((plotNum) < len(x_temp[0])):
                    axis[(i)].scatter(x_temp[plotNum],self.Y)
                    axis[(i)].xaxis.set_label_text(self.labels[plotNum+1])
                    plotNum += 1
        plt.show()
        return True