import { useState, useEffect } from 'react'; 


export function userImages(){ 
  const [Images,setImages] = useState([]); 
  useEffect(()=>{
    const header = { 
      'content-type':'application/json', 
      'Authorization': localStorage.getItem('session_token')
    }; 
    fetch(process.env.REACT_APP_API_BASE_URL+"/user/"+localStorage.getItem('username')+"/images", {method:"GET", 
                                                                headers: header}).
                                                                then((response)=>response.json()).
                                                                then((json)=>setImages(json)); 
  }, []); 
  return Images.map((img)=>({src:img, width:1, height:1})); 
} 

