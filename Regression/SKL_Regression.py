import numpy as np
from sklearn.linear_model import SGDRegressor
from sklearn.pipeline import make_pipeline
from sklearn.preprocessing import StandardScaler
import matplotlib.pyplot as plt
import random as rand
import datetime as dt
# This class can be used to train the model and use the values of coefficients obtained
# to predict other inputted values that may or may not be present in the training samples.
# The training data must be in the form of a .csv where the first row is the labels of columns
# and the first column represents the correct output value and the other columns are the features
class SKL_SGD:
    X = []
    Y = []
    labels = []
    Max_iter = 10000
    Tol = 0.001
    reg = None
    scaler = None
    # Parameters: CSV - the string address of where on the local machine the .csv input file
    #             is located.
    # This method takes in, opens, and processes the given .csv file that are the training data.
    # The processed X (feature) and Y (true output) data are stored in the class variables
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
    # Parameters: None
    # This method randomly selects and removes samples from the X and Y arrays to be used to
    # test the training against the correct values. The samples picked for testing will be 
    # returned
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
    # Parameters: None
    # This method normalizes the self.X array using the respective z-score as described in
    # scikit-learn's standard scaler. 
    def normalizeFeatures(self):
        self.scaler = StandardScaler()
        self.scaler.fit(self.X)
        xNorm = self.scaler.transform(self.X)
        self.X = xNorm
        return True
    # Parameters: None
    # Using the current self.X and self.Y, the coefficients of the model is found and fitted.
    # If self.Max_iter and self.Tol is not changed, it will use the default values of 10,000
    # and 0.001 respectively - tol being the stopping condition and Max_iter being the maximum
    # number of iterations of the gradient descent 
    def fitModel(self):
        self.reg = SGDRegressor(max_iter=self.Max_iter, tol=self.Tol)
        self.reg.fit(self.X,self.Y)
        return True
    # Parameters: sampleToPredict - array of feature data in the same order and same number
    #             as the training set
    # After fitting the model, can input an array of values to be predicted; the predicted
    # value is returned
    def predict(self,sampleToPredict):
        return self.reg.predict(self.scaler.transform([sampleToPredict]))[0]
    # Parameters: row - the number of desired rows in the graph 
    #             col - the number of desired cols in the graph
    # Plots the given feature data independently in self.X against the output values.
    # Allocates row x col spaces in the graph, if it is not enough for all features, then only
    # the first (row*col) features will be plotted. If there are too many spaces, the remaining
    # spots will be left empty. It will use the labels from the first row of the inputted .csv.
    def plotFeatures(self,row,col):
        # special case: not possible to have 0 rows of columns
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