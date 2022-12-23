from CustomRegression import BGDRegression
from SKL_Regression import SKL_SGD

BGDtest = BGDRegression()
BGDtest.loadDataset("C:\\Users\\ators\\Documents\\Personal_Projects\\Regression\\test.csv")
BGDtest.setParam("def","def",0.001)
print("Predicted Value: " + str(BGDtest.predict([1,0.5,1100])))
BGDtest.plotLearningCurve()
BGDtest.plotFeatures(2,2)

sum = 0
totalNum = 0
SKLtest = SKL_SGD()
SKLtest.loadDataset("C:\\Users\\ators\\Documents\\Personal_Projects\\Regression\\kc_house_data.csv")
xTest,yTest = SKLtest.pickRandomTests()
SKLtest.normalizeFeatures()
SKLtest.fitModel()
for elem in xTest:
    predicted = SKLtest.predict(elem)
    sum += abs((predicted - yTest[totalNum])/yTest[totalNum])
    print("Predicted: " + str(predicted) + "   Actual: " + str(yTest[totalNum]))
    totalNum += 1
avgDiff = sum/totalNum
print("Average Margin of Error: " + str(avgDiff))
SKLtest.plotFeatures(3,6)
print("Predicted Value: " + str(SKLtest.predict(input("Enter Sample: ").split(","))))