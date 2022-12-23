from CustomRegression import BGDRegression
from SKL_Regression import SKL_SGD

# test = BGDRegression(featureData=[[1,0.5,1100],[2,1,1500],[2,1.5,1510],[1,1,1600]],
#                      TrueAnswers=[100000,150000,200000,175000])
test = BGDRegression()
test.loadDataset("C:\\Users\\ators\\Documents\\Personal_Projects\\Regression\\test.csv")
print("Predicted Value: " + str(test.predict([1,0.5,1100])))
test.plotLearningCurve()

sum = 0
totalNum = 0
test = SKL_SGD()
test.loadDataset("C:\\Users\\ators\\Documents\\Personal_Projects\\Regression\\kc_house_data.csv")
xTest,yTest = test.pickRandomTests()
test.normalizeFeatures()
test.fitModel()
for elem in xTest:
    predicted = test.predict(elem)
    sum += abs((predicted - yTest[totalNum])/yTest[totalNum])
    print("Predicted: " + str(predicted) + "   Actual: " + str(yTest[totalNum]))
    totalNum += 1
avgDiff = sum/totalNum
print("Average Margin of Error: " + str(avgDiff))
test.plotFeatures(3,6)
print("Predicted Value: " + str(test.predict(input("Enter Sample: ").split(","))))