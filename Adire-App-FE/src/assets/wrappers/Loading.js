import styled from 'styled-components'

const Wrapper = styled.div`
   
.dot-spinner {
    --uib-size: 2.8rem;
    --uib-speed: .9s;
    --uib-color: #5925dc;
    position: relative;
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-align: center;
        -ms-flex-align: center;
            align-items: center;
    -webkit-box-pack: start;
        -ms-flex-pack: start;
            justify-content: flex-start;
    height: var(--uib-size);
    width: var(--uib-size);
  }
  
  .dot-spinner__dot {
    position: absolute;
    top: 0;
    left: 0;
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-align: center;
        -ms-flex-align: center;
            align-items: center;
    -webkit-box-pack: start;
        -ms-flex-pack: start;
            justify-content: flex-start;
    height: 100%;
    width: 100%;
  }
  
  .dot-spinner__dot::before {
    content: '';
    height: 20%;
    width: 20%;
    border-radius: 50%;
    background-color: var(--uib-color);
    -webkit-transform: scale(0);
        -ms-transform: scale(0);
            transform: scale(0);
    opacity: 0.5;
    -webkit-animation: pulse0112 calc(var(--uib-speed) * 1.111) ease-in-out infinite;
            animation: pulse0112 calc(var(--uib-speed) * 1.111) ease-in-out infinite;
    -webkit-box-shadow: 0 0 20px rgba(18, 31, 53, 0.3);
            box-shadow: 0 0 20px rgba(18, 31, 53, 0.3);
  }
  
  .dot-spinner__dot:nth-child(2) {
    -webkit-transform: rotate(45deg);
        -ms-transform: rotate(45deg);
            transform: rotate(45deg);
  }
  
  .dot-spinner__dot:nth-child(2)::before {
    -webkit-animation-delay: calc(var(--uib-speed) * -0.875);
            animation-delay: calc(var(--uib-speed) * -0.875);
  }
  
  .dot-spinner__dot:nth-child(3) {
    -webkit-transform: rotate(90deg);
        -ms-transform: rotate(90deg);
            transform: rotate(90deg);
  }
  
  .dot-spinner__dot:nth-child(3)::before {
    -webkit-animation-delay: calc(var(--uib-speed) * -0.75);
            animation-delay: calc(var(--uib-speed) * -0.75);
  }
  
  .dot-spinner__dot:nth-child(4) {
    -webkit-transform: rotate(135deg);
        -ms-transform: rotate(135deg);
            transform: rotate(135deg);
  }
  
  .dot-spinner__dot:nth-child(4)::before {
    -webkit-animation-delay: calc(var(--uib-speed) * -0.625);
            animation-delay: calc(var(--uib-speed) * -0.625);
  }
  
  .dot-spinner__dot:nth-child(5) {
    -webkit-transform: rotate(180deg);
        -ms-transform: rotate(180deg);
            transform: rotate(180deg);
  }
  
  .dot-spinner__dot:nth-child(5)::before {
    -webkit-animation-delay: calc(var(--uib-speed) * -0.5);
            animation-delay: calc(var(--uib-speed) * -0.5);
  }
  
  .dot-spinner__dot:nth-child(6) {
    -webkit-transform: rotate(225deg);
        -ms-transform: rotate(225deg);
            transform: rotate(225deg);
  }
  
  .dot-spinner__dot:nth-child(6)::before {
    -webkit-animation-delay: calc(var(--uib-speed) * -0.375);
            animation-delay: calc(var(--uib-speed) * -0.375);
  }
  
  .dot-spinner__dot:nth-child(7) {
    -webkit-transform: rotate(270deg);
        -ms-transform: rotate(270deg);
            transform: rotate(270deg);
  }
  
  .dot-spinner__dot:nth-child(7)::before {
    -webkit-animation-delay: calc(var(--uib-speed) * -0.25);
            animation-delay: calc(var(--uib-speed) * -0.25);
  }
  
  .dot-spinner__dot:nth-child(8) {
    -webkit-transform: rotate(315deg);
        -ms-transform: rotate(315deg);
            transform: rotate(315deg);
  }
  
  .dot-spinner__dot:nth-child(8)::before {
    -webkit-animation-delay: calc(var(--uib-speed) * -0.125);
            animation-delay: calc(var(--uib-speed) * -0.125);
  }
  
  @-webkit-keyframes pulse0112 {
    0%,
    100% {
      -webkit-transform: scale(0);
              transform: scale(0);
      opacity: 0.5;
    }
  
    50% {
      -webkit-transform: scale(1);
              transform: scale(1);
      opacity: 1;
    }
  }
  
  @keyframes pulse0112 {
    0%,
    100% {
      -webkit-transform: scale(0);
              transform: scale(0);
      opacity: 0.5;
    }
  
    50% {
      -webkit-transform: scale(1);
              transform: scale(1);
      opacity: 1;
    }
  }

`

export default Wrapper