import { useState } from 'react';
import {useNavigate} from "react-router-dom"; 
import {NavBar} from './home'; 
import "./create.css"; 
export default function Create(){ 
    return ( 
        <>
            <div className='display'> 
                <NavBar page={2}></NavBar> 
                <div className='form'> 
                    <Form></Form> 
                </div>
            </div> 
        </>
        
    ); 
} 


function Form() { 
    const [Username,setUsername] = useState(null); 
    const [Password,setPassword] = useState(null); 
    const [message,setMessage] = useState("Please create an account below"); 
    const [PasswordConf,setPasswordConf] = useState(null); 
    const [Failed,setFailed] = useState(false); 
    const navigate = useNavigate(); 
    function createHandler(){ 
        if (PasswordConf != Password) { 
            setMessage("Passwords don't match") 
            setFailed(true); 
        } else { 
        fetch(process.env.REACT_APP_API_BASE_URL+"/user", {method:"POST", 
                                            headers: {'content-type':'application/json'}, 
                                            body: JSON.stringify({"username": Username,"password":Password }) 
            })  
            .then((response) => {setFailed(response.status!=200); 
                                if (response.status!=200){setMessage("Username alredy exists, try another email or login!")}
                                else{navigate('/login')}}) 
        }
    } 
    const flags = Failed ? "Username alredy exists, please try another email or login!" : "Please create an account below";   
    return ( 
        <> 
            <div className="create-form"> 
                <img src='icon.png'/> 
                <p className='create-messages'>{message}</p> 
                <div className="create-container"> 
                    <input type="email" id="user" placeholder="Username" onChange={(e) => setUsername(e.target.value)}/> 
                    <input type="password" id="pass" placeholder="Password" onChange={(e) => setPassword(e.target.value)}/> 
                    <input type="password" id="passC" placeholder="Confirm Password" onChange={(e) => setPasswordConf(e.target.value)}/> 
                    <div className='create-buttons'> 
                        <button type="submit" onClick={createHandler}>Create Account</button> 
                    </div> 
                </div> 
            </div> 
        </> 
    ); 
} 
