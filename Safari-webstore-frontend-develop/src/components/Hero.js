import React from 'react'
// import '../App.css'
// import './css/Hero.css'

// var slideIndex = 0;
// showSlides();

// function showSlides() {
//   var i;
//   var slides = document.getElementsByClassName("mySlides");
//   var dots = document.getElementsByClassName("dot");
//   for (i = 0; i < slides.length; i++) {
//     slides[i].style.display = "none";  
//   }
//   slideIndex++;
//   if (slideIndex > slides.length) {slideIndex = 1}    
//   for (i = 0; i < dots.length; i++) {
//     dots[i].className = dots[i].className.replace(" active", "");
//   }
//   slides[slideIndex-1].style.display = "block";  
//   dots[slideIndex-1].className += " active";
//   setTimeout(showSlides, 2000); // Change image every 2 seconds
// }

function Hero() {
    return (
    <div className="hero-container slideshow-container">
             <img src='./images/bg.png' alt='bg' className='Hero-image1'/>
             <div className="dot-group">
                <span className="dot"></span> 
                <span className="dot"></span> 
                <span className="dot"></span> 
             </div>
             <p className="collection">Explore our collection</p>
             <img src="./images/dropdown.svg" alt="dropdown"  className="checkout-collection"/>
         {/* <div className="mySlides fade">
             <img src='./images/bg.png' alt='bg' className='Hero-image1'/>
             <p className="collection">Explore our collection</p>
             <img src="./images/dropdown.svg" alt="dropdown"  className="checkout-collection"/>
         </div>
         <div className="mySlides fade">
             <img src='./images/bg.png' alt='bg' className='Hero-image1'/>
             <p className="collection">Explore our collection</p>
             <img src="./images/dropdown.svg" alt="dropdown"  className="checkout-collection"/>
         </div> */}
    </div>
    )
}

export default Hero
