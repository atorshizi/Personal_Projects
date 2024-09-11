import React, { StrictMode } from "react";
import { createRoot } from "react-dom/client"; 
import { BrowserRouter, Routes, Route } from "react-router-dom"; 
import "./styles.css";
import {AuthProvider} from './authContext'; 

import Login from "./login"; 
import MyGallery from "./gallery"; 
import Home from './home'; 
import Create from './create'; 

export default function App() { 
  return ( 
      <BrowserRouter>
        <Routes> 
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/gallery" element={<MyGallery />} /> 
          <Route path="/create" element={<Create />} /> 
        </Routes>
      </BrowserRouter> 

  );
}

export function Background(){ 
  return(
    <ul class="background">
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
  </ul>
  );
}
const root = createRoot(document.getElementById("root"));
root.render(
  <AuthProvider> 
    <Background/> 
    <App /> 
  </AuthProvider> 
); 
