import React from 'react'
import { Link } from 'react-router-dom'
import styled from "styled-components"
import logo from '../assets/footerLogo.png'
import Button from './Button'
export default function Logo({margin}) {
  return (
    <LogoStyle margin = {margin}>
      <Link className='link' style={{ textDecoration: 'none' }} to = "/home">
<img src={logo} alt="logo"  className='logoImage'/>
<p className='logoText'>DecaBlog</p>
</Link>
    </LogoStyle>
  )
}



const LogoStyle = styled.div`
display: flex;
flex-direction: row;
align-items: center;
padding: 0px;
margin: ${(props) => (props.margin ? props.margin : 0)}px;

width: 169px;
height: 48px;

/* Inside auto layout */

flex: none;
order: 0;
flex-grow: 0;

.logoImage{
width: 48px;
height: 48px;
}

.logoText{
height: 34px;

/* Headline Bold - 24 */

font-family: 'Inter';
font-style: normal;
font-size: 1.2rem;
font-weight: 600;
line-height: 140%;
/* identical to box height, or 34px */

display: flex;
align-items: center;

/* Main Text */

color: #101828;
}
.link{
  display: flex;
  justify-content: space-around;
  align-items: center;
  width:400px;
}
`