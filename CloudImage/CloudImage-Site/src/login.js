import { useState, useContext, createContext} from 'react';
import {useNavigate} from "react-router-dom"; 
import {NavBar} from './home'; 
import "./login.css"; 
import { useAuth } from './authContext'; 

export default function Login(){ 
    return ( 
        <>
            <div className='display'> 
                <NavBar page={3}></NavBar> 
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
    const [Failed,setFailed] = useState(false); 
    const navigate = useNavigate(); 
    const { login } = useAuth(); 

    function loginHandler(){ 
        fetch(process.env.REACT_APP_API_BASE_URL+"/auth/login", {method:"POST", 
                                                    headers: {'content-type':'application/json'}, 
                                                    body: JSON.stringify({username: Username,password:Password }) 
            }) 
            .then((response) => response.json()) 
            .then((json) => {console.log(json); return json['token']})
            .then((token) => {localStorage.setItem("session_token",token); 
                              localStorage.setItem("username",Username); 
                                if(token==null){ 
                                    setFailed(true); 
                                } else {
                                    setFailed(false); 
                                    login(); 
                                    navigate("/gallery"); 
                                }
            }); 
    } 
    const flags = Failed ? "Failed Auth!" : "Login Below";   
    return ( 
        <> 
            <div className="credentials-form"> 
                <img src='icon.png'/>
                <p className='messages'>{flags}</p> 
                <div className="submit-container"> 
                    <input type="email" id="user" placeholder="Username" onChange={(e) => setUsername(e.target.value)}/> 
                    <input type="password" id="pass" placeholder="Password" onChange={(e) => setPassword(e.target.value)}/> 
                    <div className='buttons'> 
                        <button type="submit" onClick={loginHandler}>Login</button> 
                    </div> 
                </div> 
            </div> 
        </> 
    ); 
} 
