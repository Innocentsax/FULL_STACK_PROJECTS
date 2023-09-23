import React from 'react'
import {useState, useEffect} from 'react'
import {AiOutlineArrowLeft, AiOutlineArrowRight} from 'react-icons/ai'
import "./slider.css"
import data from "./Data"
import ChevronRightIcon from '@mui/icons-material/ChevronRight';


const Slider = () => {
    const [currentSlide, setCurrentSLide] = useState(0)
    const slideLength = data.length - 1 

    const autoScrollState = true
    let slideInterval;
    let intervalTime = 4000;

    const nxtSlide = () => {
        setCurrentSLide(currentSlide === slideLength ? 0 :  currentSlide + 1)
    }
    const nxtSlide2 = () => {
        setCurrentSLide(currentSlide === slideLength ? 0 :  currentSlide + 1)
    }

    const prevSlide = () => {
        setCurrentSLide(currentSlide === 0 ? slideLength :  currentSlide - 1)
    }


    function automaticScrolling(){
        slideInterval = setInterval(nxtSlide, intervalTime);
    }

    useEffect(() => {
        setCurrentSLide(0)
    }, [])
    

    useEffect(() => {
        if(autoScrollState) {
            automaticScrolling()
        }
        return () => clearInterval(slideInterval)
    }, [currentSlide])

   const datum = localStorage.getItem("peopledata");

   console.log(datum);


  return (
    <div className="slider">
        <AiOutlineArrowLeft  className="arrow prev" onClick={prevSlide}/>
        <AiOutlineArrowRight  className="arrow next" onClick={nxtSlide2}/>

        {data.map((slide, index) => {
            return (
                <div className={index === currentSlide ? "slide current" : "slide"} key={index}>
                    {index === currentSlide && (
                        <>
                        <img src={slide.image} alt="slide" />
                        <div className="content">
                        <a style={{display:'flex',alignItems:'center',fontWeight:'lighter', fontSize:'25px'}} href="">{slide.title}</a>
                            <p style={{display:'flex',alignItems:'space-between',fontSize:'20px'}}>{slide.description}</p>
                        </div>
                        </>
                    )}
                </div>
            )
        })}
    </div>
  )
}

export default Slider