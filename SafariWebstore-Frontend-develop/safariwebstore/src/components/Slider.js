import { useState } from "react";
import '../styles/sliderStyle.css'
import navdot from '../icons/dot-nav-btn.png'

import BtnSlider from './BtnSlider'
import sliderData from '../sliderData'
import dataSlider from "../sliderData";

const Slider = () => {
    const [slideIndex, setSlideIndex] = useState(1)
    
    const nextSlide = () => {
        if(slideIndex !== sliderData.length){
            setSlideIndex(slideIndex + 1)
        }else if(slideIndex === sliderData.length){
            setSlideIndex(1)
        }
    }
    
    const prevSlide = () => {
        if(slideIndex !== 1){
            setSlideIndex(slideIndex - 1)
        }else if(slideIndex === 1){
            setSlideIndex(dataSlider.length) 
        }
    }

    const moveDot = index => setSlideIndex(index)
    
    
    return (
        <div className='container-slider'>
            {sliderData.map((obj,index) => {
                return(
                    <div 
                        // className='slide' 
                        key={obj.id}
                        className ={slideIndex === index + 1? 'slide active-anim': 'slide'}
                        >
                        <img 
                        src ={process.env.PUBLIC_URL + `Imgs/Img${index + 1}.png`} 
                        alt=""
                        />
                    </div>
                )
            })}
            <BtnSlider moveSlide = {nextSlide} direction ='next' />
            <BtnSlider moveSlide = {prevSlide} direction ='prev' />
            <div className='container-dots'>
                {Array.from({length: 5}).map((item, index) =>(
                    <div 
                    onClick ={()=> moveDot (index + 1)}
                    className={slideIndex === index + 1? 'dot active' : 'dot'}
                    ></div>
                ))}
            </div>
            <div >
                <h3 className='container-dots-nav'>Explore Our Collection</h3>
                    <a href='#shopYourStyle' className='container-dots-nav-btn'><img src ={navdot} alt='dot-nav-btn'/></a>
            </div>
        </div>
    )
}

export default Slider
