import React from 'react'
import styled from "styled-components"
import { Link } from "react-router-dom";

export default function Button(props) {
    const{width,height,background,text,color,url} = props
  return (
    <div>
        <Link className="link" to={url} style={{ textDecoration: 'none' }}>
    <ButtonStyle
        style={{ width: width, height:height, background:background, color:color}}
      >
        {text}
    </ButtonStyle>
    </Link>
    </div>
  )
}

export const ButtonStyle = styled.button`
display: flex;
flex-direction: row;
justify-content: center;
align-items: center;
padding: 12px 16px;
border-radius: 5px 5px;
text-decoration: none;

text-decoration: none;
border: none;
cursor: pointer;
z-index: 232345;
font-weight: 600;
font-size: 14px;
`

