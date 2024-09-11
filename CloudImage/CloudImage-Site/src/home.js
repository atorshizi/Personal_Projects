import { useState, useRef, useEffect } from 'react'; 
import { useNavigate } from "react-router-dom"; 
import { useAuth } from './authContext'; 

import "./home.css"; 
export default function Home() { 
    return ( 
        <> 
        <div className='display'> 
            <NavBar page={0}/> 
            <Welcome/> 
        </div> 
        </>); 
} 

function Welcome(){ 
    const navigate = useNavigate(); 
    return (
        <div className='welc'> 
            <div className='side_by_side'> 
                <div className='moto'> 
                    <h1>Welcome to ImageCloud!</h1> 
                    <h3>A simple solution to take and store your pictures to the cloud and access them anywhere. Available on the website and on Android. Click to create an account or login to start.</h3>
                    <div className='moto_buttons'> 
                        <button onClick={()=>{navigate('/login')}}>Login</button> 
                        <button onClick={()=>{navigate('/create')}}>Create Account</button> 
                    </div> 
                </div> 
                <div className='welc_images'> 
                    <img src='home.png'/> 
                </div>
            </div> 
        </div>
    ); 
}


function SignoutDropdown(){ 
    const navigate = useNavigate(); 
    const { isLoggedIn, logout } = useAuth(); 
    const [openDrop, setOpenDrop] = useState(false); 
    const dropdownRef = useRef(null); 
    function handleSignout(){ 
        localStorage.removeItem('username'); 
        localStorage.removeItem('session_token'); 
        setOpenDrop(false); 
        logout(); 
        navigate('/login'); 
    } 

    function toggleSidebar(){ 
        setOpenDrop(!openDrop); 
    } 

    const handleClickOutside = (event) => {
        if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
            setOpenDrop(false);
        }
      };
    
      useEffect(() => {
        document.addEventListener('mousedown', handleClickOutside);
        return () => {
          document.removeEventListener('mousedown', handleClickOutside);
        };
      }, []); 

    if (!isLoggedIn){ 
        return (
            <> 
            <div class="dropdown" ref={dropdownRef}> 
                <div class={openDrop?"dropdown-content":'dropdown-content-hide'}> 
                    <a>Guest User</a> 
                </div> 
                <button id='userBtn' class="dropbtn">Guest User</button>
                <button onClick={toggleSidebar} id='hide' class="dropbtn"><img src='user.svg'/></button>
            </div> 
            </>); 
    } 
    return ( 
        <> 
        <div onClick={toggleSidebar} class="dropdown" ref={dropdownRef}>
            <div class={openDrop?"dropdown-content":'dropdown-content-hide'}> 
                <a onClick={handleSignout}>Sign Out</a> 
            </div>
            <button id='userBtn' class="dropbtn">{localStorage.getItem('username')}</button>
            <button id='hide' class="dropbtn"><img src='user.svg'/></button>
        </div> 
        </>
    ); 
}

export function NavBar(props){ 
    const navigate = useNavigate(); 

    function toggleWidth(){ 
        const nav = document.getElementById('navbar'); 
        if (nav.style.width<'30px'){  
            nav.style.width='30px'; 
            nav.style.padding='0 0.5em 0 0.5em'; 
        } else { 
            nav.style.width='0px'; 
            nav.style.padding='0px'; 
        }
    }
    return( 
        <>
            <button style={{position:'fixed',zIndex:'2'}} className='toggle-btn' onClick={toggleWidth}><img className='menu' src='menu.png'/></button>
            <div className='navbar-desktop'> 
                <div className='nav_middle_container'> 
                    <img id='home-img' onClick={()=>navigate('/')} src='icon.png'/> 
                    <div className='nav_button_container'> 
                        <button onClick={()=>navigate('/')}>Home</button> 
                        <button onClick={()=>navigate('/gallery')}>Gallery</button> 
                        <button onClick={()=>navigate('/create')}>Create Account</button> 
                        <button onClick={()=>navigate('/login')}>Login</button> 
                    </div> 
                    <div className='nav_button_container-images'> 
                        <div className='img-container'><img onClick={()=>navigate('/')} src='home.svg'/> </div> 
                        <div className='img-container'><img onClick={()=>navigate('/gallery')} src='gallery.svg'/> </div> 
                        <div className='img-container'><img onClick={()=>navigate('/create')} src='add.svg'/> </div> 
                        <div className='img-container'><img onClick={()=>navigate('/login')} src='login.svg'/> </div> 
                    </div> 
                </div> 
                <div className='nav_sides_container'> 
                    <SignoutDropdown/> 
                </div> 
            </div> 

            <div id='navbar' className='navbar'> 
                <div className='nav_middle_container'> 
                    <img id='home-img' onClick={()=>navigate('/')} src='icon.png'/> 
                    <div className='nav_button_container-images'> 
                        <div className='img-container'><img onClick={()=>navigate('/')} src='home.svg'/> </div> 
                        <div className='img-container'><img onClick={()=>navigate('/gallery')} src='gallery.svg'/> </div> 
                        <div className='img-container'><img onClick={()=>navigate('/create')} src='add.svg'/> </div> 
                        <div className='img-container'><img onClick={()=>navigate('/login')} src='login.svg'/> </div> 
                        <SignoutDropdown/> 
                    </div> 
                </div> 
            </div> 
        </>
    ); 
}
