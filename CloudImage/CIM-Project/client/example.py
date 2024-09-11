import requests 
from pprint import pprint 
from PIL import Image
from io import BytesIO
import matplotlib.pyplot as plt 
# Define the URL of the Spring Boot server
# url = 'http://54.151.88.194:8080' 
url = 'https://cimserver.xyz:8443'

def login(username,password): 
    response = requests.post(url+'/auth/login',json={'username':username, 'password':password}) 
    if response.status_code == 200:
        data = response.json()
        pprint(data)
        return data['token'] 
    else:
        pprint(f"Request failed with status code: {response.status_code}") 
        return None 
    
def getAll(): 
    response = requests.get(url+'/user')
    if response.status_code == 200:
        data = response.json()
        pprint(data)
    else:
        pprint(f"Request failed with status code: {response.status_code}") 

def createAccount(username, password): 
    response = requests.post(url+'/user', json={'username':username, 'password':password}) 
    # Check if the request was successful
    if response.status_code == 200:
        # Parse the response (assuming it's JSON)
        data = response.json() 
        pprint(data) 
    else:
        pprint(f"Request failed with status code: {response.status_code}") 
def uploadPicture(path,token,username): 
     with open(path, "rb") as image_file:
        header = {'Authorization':token} 
        # Open the file in binary mode and send it as a multipart/form-data
        with open(path, 'rb') as file:
            files = {'file': (path, file, 'multipart/form-data')}
            response = requests.post(url+'/user/'+username+'/images', files=files, headers=header)
        if response.status_code == 200:
            # Parse the response (assuming it's JSON)
            data = response.json() 
            pprint(data)
        else:
            pprint(f"Request failed with status code: {response.status_code}") 
            print(response.content) 

def getAllPics(): 
    # Perform the GET request
    response = requests.get(url+'/user/images')
    # Check if the request was successful
    if response.status_code == 200:
        # Parse the response (assuming it's JSON)
        data = response.json() 
        pprint(data)
    else: 
        pprint(f"Request failed with status code: {response.status_code}") 

def getUserPics(token,username): 
    # Perform the GET request
    response = requests.get(url+'/user/'+username+'/images', headers={"Authorization":token})
    # Check if the request was successful
    if response.status_code == 200:
        # Parse the response (assuming it's JSON)
        data = response.json() 
        pprint(data) 
    else:
        pprint(f"Request failed with status code: {response.status_code}") 
    return data 

def show_images(urls):
    images = []
    for url in urls:
        response = requests.get(url)
        img = Image.open(BytesIO(response.content))
        images.append(img)

    fig, axes = plt.subplots(1, len(images), figsize=(15, 5))
    if len(images) == 1:
        axes = [axes] 

    for ax, img in zip(axes, images):
        ax.imshow(img)
        ax.axis('off')

    plt.show()

# createAccount('ariari','atotez')
# createAccount('arian','atotez') 
# createAccount('atorshizi13@gmail.com','arian1380')
# getAll() 
# t1=login('arian','atotez') 
# login('arian ','atotez') 
# t2=login('ariari','atotez') 

# uploadPicture(r'C:\Users\Arian\Documents\CIM-Project\client\pic1.jpg',t1,'arian') 
# uploadPicture(r'C:\Users\Arian\Documents\CIM-Project\client\pic1.jpg',t2,'ariari') 
# uploadPicture(r'C:\Users\Arian\Documents\CIM-Project\client\pic2.png',t2,'ariari') 
# print("USER 1") 
# show_images(getUserPics(t1,'arian')) 
# print("USER 1") 
# show_images(getUserPics(t2,'ariari')) 

createAccount('a','1') 
t = login('a','1') 
# uploadPicture(r'C:\Users\Arian\Documents\CIM-Project\client\1.jpg',t,'a') 


