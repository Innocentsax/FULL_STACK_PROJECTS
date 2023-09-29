import React from "react"
import {useEffect, useState} from 'react'


const BackToTop = () => {

    const [backToTopBtn, setBackToTopBtn] = useState(null);

    useEffect(() =>{
        window.addEventListener('scroll', ()=>{
            if(window.scrollY > 600) {
                setBackToTopBtn(true)
            }else{
                setBackToTopBtn(false)
            }    
        })
    }, [])

    const scrollUp = () => {
        window.scrollTo({
            top: 0,
            behaviour: 'smooth'
        })
    }

    return(
        <div className="">
            {backToTopBtn && (
                <button onClick={scrollUp} className="fixed bottom-[50px] right-[50px] h-[50px] w-[50px] text-3xl bg-[#493d14] rounded-full  text-white">&#8593;</button>
            )}
        </div>
    )
}

export default BackToTop