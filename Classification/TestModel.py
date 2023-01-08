import numpy as np
import tensorflow as tf
import os

goodPathTest = "C:\\Users\\ators\\Documents\\Personal_Projects\\Classification\\Datasets\\full\\testing\\good"
spamPathTest = "C:\\Users\\ators\\Documents\\Personal_Projects\\Classification\\Datasets\\full\\testing\\spam"
savePath = "C:\\Users\\ators\\Documents\\Personal_Projects\\Classification\\Datasets\\full\\saves"

fl = open("C:\\Users\\ators\\Documents\\Personal_Projects\\Classification\\Datasets\\wordBank.txt")
wordBank = {}
for i in range(10000):
    word = fl.readline()[0:-1]
    wordBank[word] = i
fl.close()

xTest = []
Y = []
# files = os.listdir(goodPathTest)
# for currFile in files:
#     try:
#         xTest.append(open(goodPathTest + "\\" + str(currFile)).read())
#         Y.append([0])
#     except:
#         continue
# files = os.listdir(spamPathTest)
# for currFile in files:
#     try:
#         xTest.append(open(spamPathTest + "\\" + str(currFile)).read())
#         Y.append([1])
#     except:
#         continue
files = os.listdir(goodPathTest)
for currFile in files:
    Y.append(0)
    newList = [0] * 10000
    i = 0
    for eachWord in open(goodPathTest + "\\" + str(currFile)).read():
        i += 1
        try:
            newList[wordBank[eachWord]] += 1
        except:
            continue
    xTest.append(newList)
files = os.listdir(spamPathTest)
for currFile in files:
    newList = [0] * 10000
    i = 0
    try:
        Y.append(1)
        for eachWord in open(spamPathTest + "\\" + str(currFile)).read():
            i += 1
            try:
                newList[wordBank[eachWord]] += 1
            except:
                continue
    except:
        del Y[-1]
        continue
    xTest.append(newList)

# tok = tf.keras.preprocessing.text.Tokenizer(num_words=50000)
# tok.fit_on_texts(texts=xTest)
# xTest = tok.texts_to_sequences(xTest)
# xTest = tf.keras.preprocessing.sequence.pad_sequences(xTest,11939)
xTest = np.array(xTest)
Y = np.array(Y)

My_model = tf.keras.models.load_model(savePath)
X_test = np.array(xTest)
output = My_model.predict(X_test)

threshold = 0.9
tot = 0
incorrect = 0
# for elem in output:
#     if (elem[0] <= threshold):
#         elem[0] = 0
#     else:
#         elem[0] = 1
#     tot += 1
#     elem[0] -= Y[tot-1]
#     if (elem[0] != 0):
#         incorrect += 1
classified = []
for i in range(len(output)):
    if (output[i] <= threshold):
        classified.append(0) 
    else:
        classified.append(1)
print(classified)
for elem in classified:
    tot += 1
    elem -= Y[tot-1]
    if (elem != 0):
        incorrect += 1
print("Accuracy: " + str((tot-incorrect)/tot))