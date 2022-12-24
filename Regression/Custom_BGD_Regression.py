import numpy as np
import matplotlib.pyplot as plt
# This class is a custom regression class that uses batch gradient descent to fit the model.
# Once trained it can be used to predict future outputs using the model. The training data must 
# be in the form of a .csv where the first row is the labels of columns
# and the first column represents the correct output value and the other columns are the features
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
    # Parameters: CSV - the string address of where on the local machine the .csv input file
    #             is located.
    # This method takes in, opens, and processes the given .csv file that are the training data.
    # The processed X (feature) and Y (true output) data are stored in the class variables. This 
    # method will automatically normalize the features using this class' transform method before
    # assigning to self.X
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
    # Parameters: Alpha - the learning rate 
    #             Max_iter - maximum number of times the gradient descent will run
    #             Epsilon - the percent change of loss that will determine convergence
    # This methods allows chaning of the above training parameters. If the value of the argument
    # is not of the correct type, the default value will be used for that parameter.
    def setParam(self,Alpha,Max_iter,Epsilon):
        if(type(Alpha) == type(self.alpha)):
            self.alpha = Alpha
        if(type(Max_iter) == type(self.max_iter)):
            self.max_iter = Max_iter
        if(type(Epsilon) == type(self.epsilon)):
            self.epsilon = Epsilon
        return True
    # Parameters: W - the current weights calculated
    #             b - the current b (constant) value calculated
    #             featNum - which feature the partial derivative is with respect to
    # This method returns the result of the partial derivative of the cost function with
    # respect to one of the features as determined by the method arguments 
    def __derCost(self,W,b,featNum):
        w_term = float(np.dot(self.X[0],W)) * self.X[0][featNum] 
        b_term = self.X[0][featNum] * b
        c_term = self.Y[0] * self.X[0][featNum]
        for i in range(1,self.m):
            w_term += self.X[i][featNum] * float(np.dot(np.matrix(self.X[i]),W))
            b_term += self.X[i][featNum] * b
            c_term += self.Y[i] * self.X[i][featNum]
        return ((w_term + b_term - c_term)/(self.m))
    # Parameters: W - the current weights calculated
    #             b - the current b (constant) value calculated
    # This method returns the result of the partial derivative of the cost function with
    # respect to b as determined by the method arguments 
    def __derCostB(self,W,b):
        w_term = float(np.dot(self.X[0],W))
        b_term = b
        c_term = self.Y[0]
        for i in range(1,self.m):
            w_term += float(np.dot(np.matrix(self.X[i]),W))
            b_term += b
            c_term += self.Y[i]
        return ((w_term + b_term - c_term)/(self.m))
    # Parameters: W - the current weights calculated
    #             b - the current b (constant) value calculated
    # This method returns the result of the cost function with the W and b that is passed 
    # into this method. This can be used to calculate the cost function itself.
    def __findJ(self,W,b):
        J = 0
        for i in range(self.m):
            J += pow((np.dot(W,self.X[i])-(self.Y[i])),2)
        return (J/(2* self.m))
    # Parameters: None
    # This method will fit the model with the correct weight by going through the batch
    # gradient descent algorithm. It will end when the percent change of the loss function
    # is less than or equal to epsilon or when Max_iter is reached.
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
    # Parameters: xToNormalize - the feature data to be normalized
    # This method is used to scale and normalize the inputed features. It will divide each
    # feature's value by the maximum value in the feature set and return a normalized version.
    def __transform(self,xToNormalize):
        # transpose the matrix to make it easier to iterate over each feature individually
        xToNormalize= np.transpose(xToNormalize)
        xNorm = []
        for elem in xToNormalize:
            featMax = elem.max()
            self.X_scalar.append(featMax)
            xNorm.append(elem/featMax)
        return np.transpose(xNorm)
    # Parameters: None
    # This method is used to plot the learning curve using the data that is collected after
    # the __getCoeff() method is run. 
    def plotLearningCurve(self):
        plt.scatter(self.s_X,self.s_Y)
        plt.ylabel("Loss")
        plt.xlabel("Iterations")
        plt.show()
        return True
    # Parameters: row - the number of desired rows in the graph 
    #             col - the number of desired cols in the graph
    # Plots the given feature data independently in self.X against the output values.
    # Allocates row x col spaces in the graph, if it is not enough for all features, then only
    # the first (row*col) features will be plotted. If there are too many spaces, the remaining
    # spots will be left empty. It will use the labels from the first row of the inputted .csv.
    def plotFeatures(self,row,col):
        if ((row == 0) or (col == 0)):
            return False
        # transpose the X matrix so features are the rows and easier to plot
        x_temp = np.transpose(self.X)
        figure, axis = plt.subplots(row,col)    # allocate the plots on the graph
        plotNum = 0                             # used to ensure we don't plot more than spaces
        # case where need multiple rows and columns
        if((row > 1) and (col > 1)):
            for i in range(row):
                for j in range(col):
                    axis[i,j].get_xaxis().set_ticks([])
                    axis[i,j].get_yaxis().set_ticks([])
                    if ((plotNum) < len(x_temp)):
                        axis[(i),(j)].scatter(x_temp[plotNum],self.Y)
                        axis[(i),(j)].xaxis.set_label_text(self.labels[plotNum+1])
                        plotNum += 1
        # case with multiple rows only
        elif((row > 1) and (col <= 1)):
            for i in range(row):
                axis[i].get_xaxis().set_ticks([])
                axis[i].get_yaxis().set_ticks([])
                if ((plotNum) < len(x_temp)):
                    axis[(i)].scatter(x_temp[plotNum],self.Y)
                    axis[(i)].xaxis.set_label_text(self.labels[plotNum+1])
                    plotNum += 1
        # case with multiple columns only
        else:
            for i in range(col):
                axis[i].get_xaxis().set_ticks([])
                axis[i].get_yaxis().set_ticks([])
                if ((plotNum) < len(x_temp)):
                    axis[(i)].scatter(x_temp[plotNum],self.Y)
                    axis[(i)].xaxis.set_label_text(self.labels[plotNum+1])
                    plotNum += 1
        plt.show()
        return True
    # Parameters: sampleToPredict - array of feature data in the same order and same number
    #             as the training set
    # Will run __getCoeff() and then can input an array of values to be predicted; the 
    # predicted value is returned
    def predict(self,sampleToPredict):
        for i in range(len(sampleToPredict)):
            sampleToPredict[i] /= self.X_scalar[i]
        coeffs = self.__getCoeff()
        return (np.dot(coeffs[0],sampleToPredict) + coeffs[1])