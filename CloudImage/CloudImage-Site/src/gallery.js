import { useState} from 'react'; 
import Gallery from 'react-photo-gallery'; 
import {userImages} from './images'; 
import {NavBar} from './home'; 
import Carousel, { Modal, ModalGateway }  from 'react-images'; 
import { useAuth } from './authContext'; 
import "./gallery.css"; 

export default function MyGallery() { 
    const { isLoggedIn } = useAuth(); 
    if(!isLoggedIn){ 
        window.alert("Please login or create an account first"); 
    } 
    return ( 
        <> 
            <div className='display'> 
                <NavBar page={1}></NavBar> 
                <div className='non-nav'> 
                    <Title></Title> 
                    <div className='gallary_container'> 
                        {isLoggedIn?<MainContent></MainContent>:<div className='content'></div> } 
                    </div> 
                </div> 
            </div> 
        </>); 
} 

function Title(){ 
    const [file,setFile] = useState(null); 
    async function uploadImage(e){ 
        setFile(e.target.files[0]); 
        const url = process.env.REACT_APP_API_BASE_URL + "/user/"+localStorage.getItem('username')+"/images"; 
        const header = { 
            'Authorization': localStorage.getItem('session_token')
        }; 
        try {
            const formData = new FormData(); 
            formData.append('file', e.target.files[0]); 
            const response = await fetch(url, {
                method: 'POST',
                body: formData, 
                headers: header
            }); 
            console.log(response); 
            location.reload(); 
        } catch (error) {
            console.error(error);
        } 
    } 
    return (<> 
                <header className="hero"><p>My Gallery</p> 
                    <div className='upload'> 
                        <label> 
                            <input type="file" name="filename" accept=".jpg, .jpeg, .png" onChange={(e)=>uploadImage(e)}/> 
                            <img src='upload.svg'/> 
                            {(file==null)? "Select File":file.name}  
                        </label>
                        {/* <button type='submit' onClick={(uploadImage)}>Upload</button>  */}
                    </div> 
                </header> 
            </>); 
} 

function MainContent(){ 
    const images = userImages(); 
    const [Caropened,setCaropened] = useState(false); 
    const [Currimg, setCurrimg] = useState(0); 
    function imageRenderer({ index=0, key='key', photo={}}){ 
        return ( 
        <img 
            className='thumbnail'
            index={index} 
            key={key}
            src={photo.src} 
            width={photo.width} 
            height={photo.height} 
            onClick={()=>openLightbox(index,photo)} 
        /> 
    );} 

    function openLightbox(index,photo){ 
        setCaropened(true); 
        setCurrimg(index); 
    } 
    function closeLightbox(){ 
        setCaropened(false); 
    }

    if (images.length==0){ 
        return (<> 
            <div className='content'> 
                <h2>Upload Your First Picture</h2> 
            </div> 
        </>); 
    } 
    
    return (<> 
                <div className='content'> 
                    <Gallery photos={images} renderImage={imageRenderer} direction='row'/>
                    <ModalGateway>
                    {Caropened ? (
                    <Modal onClose={closeLightbox}>
                        <Carousel
                        currentIndex={Currimg} 
                        views={images.map(x => ({
                            ...x,
                            srcset: x.srcSet,
                        }))} 
                        />
                    </Modal>
                    ) : null}
                </ModalGateway> 
                </div> 
            </>); 
} 