import React from 'react'
import '../styles/sliderStyle.css'
import leftArrow from '../icons/left-arrow.svg'
import rightArrow from '../icons/right-arrow.svg'

function BtnSlider({direction, moveSlide}) {
    return (
        <button onClick={moveSlide}
            className ={direction === 'next'? 'btn-slide next': 'btn-slide prev'}
        >
            <img src={direction === 'next'? rightArrow: leftArrow } alt=""/>
        </button>
    )
}

export default BtnSlider
