import numpy as np
import matplotlib.pyplot as plt
class BGDRegression:
    X = []
    Y = []
    m = 0
    labels = []
    max_iter = 10000
    epsilon = 0.001
    alpha = 0.01
    X_scalar = []
    s_X = []
    s_Y = []

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
        self.X = self.__transform(self.X)
        self.m = len(self.X)
        return True
    def setParam(self,Alpha,Max_iter,Epsilon):
        if(type(Alpha) == type(self.alpha)):
            self.alpha = Alpha
        if(type(Max_iter) == type(self.max_iter)):
            self.max_iter = Max_iter
        if(type(Epsilon) == type(self.epsilon)):
            self.epsilon = Epsilon
    def __derCost(self,W,b,featNum):
        w_term = float(np.dot(self.X[0],W)) * self.X[0][featNum] 
        b_term = self.X[0][featNum] * b
        c_term = self.Y[0] * self.X[0][featNum]
        for i in range(1,self.m):
            w_term += self.X[i][featNum] * float(np.dot(np.matrix(self.X[i]),W))
            b_term += self.X[i][featNum] * b
            c_term += self.Y[i] * self.X[i][featNum]
        return ((w_term + b_term - c_term)/(self.m))
    def __derCostB(self,W,b):
        w_term = float(np.dot(self.X[0],W))
        b_term = b
        c_term = self.Y[0]
        for i in range(1,self.m):
            w_term += float(np.dot(np.matrix(self.X[i]),W))
            b_term += b
            c_term += self.Y[i]
        return ((w_term + b_term - c_term)/(self.m))
    def __findJ(self,W,b):
        J = 0
        for i in range(self.m):
            J += pow((np.dot(W,self.X[i])-(self.Y[i])),2)
        return (J/(2* self.m))
    def __getCoeff(self):
        n = len(self.X[0])
        W = [1] * len(self.X[0])
        b = 1
        W_temp = [0] * len(self.X[0])
        b_temp = 0
        J = self.__findJ(W,b)
        J_prev = J/10
        j = 0
        while((abs((J-J_prev)/J_prev) > (self.epsilon)) and (j <= self.max_iter)):
            self.s_X.append(j)
            self.s_Y.append(J)
            for i in range(n):
                W_temp[i] = W[i] - (self.alpha * self.__derCost(W,b,i))
            b_temp = b - (self.alpha * self.__derCostB(W,b))
            J_prev = J
            W = W_temp
            b = b_temp
            J = self.__findJ(W,b)
            j += 1
        return [W,b]
    def __transform(self,xToNormalize):
        xToNormalize= np.transpose(xToNormalize)
        xNorm = []
        for elem in xToNormalize:
            featMax = elem.max()
            self.X_scalar.append(featMax)
            xNorm.append(elem/featMax)
        return np.transpose(xNorm)
    def plotLearningCurve(self):
        plt.scatter(self.s_X,self.s_Y);
        plt.ylabel("Loss")
        plt.xlabel("Iterations")
        plt.show()
        return True
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
                    if ((plotNum) < len(x_temp)):
                        axis[(i),(j)].scatter(x_temp[plotNum],self.Y)
                        axis[(i),(j)].xaxis.set_label_text(self.labels[plotNum+1])
                        plotNum += 1
        elif((row > 1) and (col <= 1)):
            for i in range(row):
                axis[i].get_xaxis().set_ticks([])
                axis[i].get_yaxis().set_ticks([])
                if ((plotNum) < len(x_temp)):
                    axis[(i)].scatter(x_temp[plotNum],self.Y)
                    axis[(i)].xaxis.set_label_text(self.labels[plotNum+1])
                    plotNum += 1
        else:
            for i in range(col):
                axis[i].get_xaxis().set_ticks([])
                axis[i].get_yaxis().set_ticks([])
                if ((plotNum) < len(x_temp)):
                    axis[(i)].scatter(x_temp[plotNum],self.Y)
                    axis[(i)].xaxis.set_label_text(self.labels[plotNum+1])
                    plotNum += 1
        plt.show()
    def predict(self,sampleToPredict):
        for i in range(len(sampleToPredict)):
            sampleToPredict[i] /= self.X_scalar[i]
        coeffs = self.__getCoeff()
        return (np.dot(coeffs[0],sampleToPredict) + coeffs[1])