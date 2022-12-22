import numpy as np
class BGDRegression:
    X = None
    Y = None
    Max_iter = 10000
    epsilon = 0.000001
    X_scalar = []
    def __init__(self,featureData,TrueAnswers) -> None:
        self.X = self.__transform(featureData)
        self.Y = TrueAnswers
    def __derCost(self,W,b,featNum):
        w_term = float(np.dot(self.X[0],W)) * self.X[0][featNum] 
        b_term = self.X[0][featNum] * b
        c_term = self.Y[0] * self.X[0][featNum]
        m = len(self.X)
        for i in range(1,m):
            w_term += self.X[i][featNum] * float(np.dot(np.matrix(self.X[i]),W))
            b_term += self.X[i][featNum] * b
            c_term += self.Y[i] * self.X[i][featNum]
        return ((w_term + b_term - c_term)/m)
    def __derCostB(self,W,b):
        w_term = float(np.dot(self.X[0],W))
        b_term = b
        c_term = self.Y[0]
        m = len(self.X)
        for i in range(1,m):
            w_term += float(np.dot(np.matrix(self.X[i]),W))
            b_term += b
            c_term += self.Y[i]
        return ((w_term + b_term - c_term)/m)
    def __converge(self,W_p,W,b_p,b):
        if (abs(b - b_p) > self.epsilon):
            return False
        for i in range(len(W_p)):
            if (abs(W_p[i] - W[i]) > self.epsilon):
                return False
        return True
    def __getCoeff(self):
        n = len(self.X[0])
        W = [1] * len(self.X[0])
        b = 1
        W_temp = [0] * len(self.X[0])
        W_prev = W
        b_temp = 0
        b_prev = b + 10
        alpha = 0.01
        for i in range(self.Max_iter):
        # while(not self.__converge(W_prev,W,b_prev,b)):
            for i in range(n):
                W_temp[i] = W[i] - (alpha * self.__derCost(W,b,i))
            b_temp = b - (alpha * self.__derCostB(W,b))
            W_prev = W
            b_prev = b
            W = W_temp
            b = b_temp
        return [W,b]
    def __transform(self,xToNormalize):
        xToNormalize= np.transpose(xToNormalize)
        xNorm = []
        for elem in xToNormalize:
            featMax = elem.max()
            self.X_scalar.append(featMax)
            xNorm.append(elem/featMax)
        return np.transpose(xNorm)
    def predict(self,sampleToPredict):
        for i in range(len(sampleToPredict)):
            sampleToPredict[i] /= self.X_scalar[i]
        coeffs = self.__getCoeff()
        return (np.dot(coeffs[0],sampleToPredict) + coeffs[1])

test = BGDRegression(featureData=[[1,0.5,1100],[2,1,1500],[2,1.5,1510],[1,1,1600]],
                     TrueAnswers=[100000,150000,200000,175000])
print("Predicted Value: " + str(test.predict([1,0.5,1100])))