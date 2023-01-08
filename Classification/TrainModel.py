import numpy as np
import tensorflow as tf
from keras import Sequential
from keras.layers import Dense
import os
import random as rand
import matplotlib.pyplot as plt

fl = open("C:\\Users\\ators\\Documents\\Personal_Projects\\Classification\\Datasets\\wordBank.txt")
wordBank = {}
for i in range(10000):
    word = fl.readline()[0:-1]
    wordBank[word] = i
fl.close()

model = Sequential([Dense(units=500, activation='relu', kernel_regularizer=tf.keras.regularizers.l2(0.02)),
                    Dense(units=200, activation='relu', kernel_regularizer=tf.keras.regularizers.l2(0.02)),
                    Dense(units=1, activation='sigmoid')
                   ])
model.compile(optimizer=tf.keras.optimizers.Adam(learning_rate=0.001), loss=tf.keras.losses.BinaryCrossentropy(), metrics=['accuracy'])

Y = []
# X = []

goodPath = "C:\\Users\\ators\\Documents\\Personal_Projects\\Classification\\Datasets\\full\\all\\good"
spamPath = "C:\\Users\\ators\\Documents\\Personal_Projects\\Classification\\Datasets\\full\\all\\spam"
goodPathTest = "C:\\Users\\ators\\Documents\\Personal_Projects\\Classification\\Datasets\\full\\testing\\good"
spamPathTest = "C:\\Users\\ators\\Documents\\Personal_Projects\\Classification\\Datasets\\full\\testing\\spam"
savePath = "C:\\Users\\ators\\Documents\\Personal_Projects\\Classification\\Datasets\\full\\saves"

X = []
rand.seed(42)
files = os.listdir(goodPath)
for currFile in files:
    newList = [0] * 10000
    i = 0
    try:
        if (rand.randint(0,10) <= 8):
            Y.append(0)
            for eachWord in open(goodPath + "\\" + str(currFile)).read():
                i += 1
                try:
                    newList[wordBank[eachWord]] += 1
                except:
                    continue
            X.append(newList)
        else:
            newFile = open(goodPathTest + "\\" + str(currFile),"x")
            newFile.write(open(goodPath + "\\" + str(currFile)).read())
    except:
        continue

files = os.listdir(spamPath)
for currFile in files:
    newList = [0] * 10000
    i = 0
    try:
        if (rand.randint(0,10) <= 8):
            for eachWord in open(spamPath + "\\" + str(currFile)).read():
                i += 1
                try:
                    newList[wordBank[eachWord]] += 1
                except:
                    continue
            X.append(newList)
            Y.append(1)
        else:
            newFile = open(spamPathTest + "\\" + str(currFile),"x")
            newFile.write(open(spamPath + "\\" + str(currFile)).read())
    except:
        continue


# files = os.listdir(goodPath)
# for currFile in files:
#     try:
#         if (rand.randint(0,100)%10 <= 8):
#             X.append(open(goodPath + "\\" + str(currFile)).read())
#             Y.append([0])
#         else:
#             newFile = open(goodPathTest + "\\" + str(currFile),"x")
#             newFile.write(open(goodPath + "\\" + str(currFile)).read())
#     except:
#         continue
# files = os.listdir(spamPath)
# for currFile in files:
#     try:
#         if (rand.randint(0,100)%10 <= 8):
#             X.append(open(spamPath + "\\" + str(currFile)).read())
#             Y.append([1])
#         else:
#             newFile = open(spamPathTest + "\\" + str(currFile),"x")
#             newFile.write(open(spamPath + "\\" + str(currFile)).read())
#     except:
#         continue

# tok = tf.keras.preprocessing.text.Tokenizer(num_words=50000)
# tok.fit_on_texts(texts=X)
# X = tok.texts_to_sequences(X)
# X = tf.keras.preprocessing.sequence.pad_sequences(X)
X = np.array(X)
Y = np.array(Y)

ep=50
# callbacks=tf.keras.callbacks.EarlyStopping(monitor='loss',mode='min',patience=5,restore_best_weights=True)
history = model.fit(X,Y,epochs=ep,batch_size=32)
model.save(savePath)
YS = []
for i in range(ep-1):
    YS.append(i+2)
plt.scatter(YS,history.history['loss'][1:])
plt.show()