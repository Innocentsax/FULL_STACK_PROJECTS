
import React from 'react'
// import './css/BackToTop.css';
// var Top = document.getElementById("backtotop");
// window.onscroll = function() {scrollFunction()};

// function scrollFunction() {
//     if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
//       Top.style.display = "block";
//     } else {
//       Top.style.display = "none";
//     }
//   }

//   function topFunction() {
//     document.body.scrollTop = 0;
//     document.documentElement.scrollTop = 0;
//   }

  function BackToTop() {
    return (
        <div>
            <button id="backtotop" title="Go to top"><img src="/images/backtotop-icon.svg" alt="backtotop" className="top-icon"/></button>
        </div>  
    )
}

export default BackToTop
