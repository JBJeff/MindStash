import { BrowserRouter, Route, Routes, Navigate, useNavigate } from 'react-router-dom'
import WelcomeComponent from './basic/WelcomeComponent'
import HeaderComponent from './basic/HeaderComponent'
import FooterComponent from './basic/FooterComponent'



import React, { useRef, useState } from 'react'



export default function MindStash() {


    return (

        <div className="MindStash">
            {/* AuthProvider ist ein Context Provider, der den Authentifizierungszustand für alle untergeordneten Komponenten bereitstellt 
            
            Für bessere veranschaulichung und präsentation der App wurde die Authentifizierung rausgenommen.
            
            */}
           
                <BrowserRouter>
                     <HeaderComponent></HeaderComponent> 

                    {/* <AuthenticatedRoute> ist die Kompenente welche regelt das nur User zugriff auf die entsprechenden Routes erhalten die die Befugniss haben */}
                    <Routes>
                        {/* <AuthenticatedRoute></AuthenticatedRoute> wird erstmal rausgenommen, SPÄTER WIEDER EINFÜGEN */}

                        <Route path='' element={<WelcomeComponent />}></Route>


                    </Routes>

                <FooterComponent></FooterComponent>

                    {/* <FooterComponent></FooterComponent> */}
                </BrowserRouter>
            
        </div>
    )
}