import React, {useState} from 'react'
import { CategoryItem } from "./CategoryItem"
import "./productcatogry.css"



export default function CategoryBar() {
    
    // This should be for logout implementation, to remove token from localStorage
    // localStorage.removeItem("token")
  
  return (
    <div className="navbar">
        <div className="wrapper">
            <div className="left">
            <a href="/">
                    <img src="https://uploads-ssl.webflow.com/5e80894f63c557e083ed96b4/5e808ce7dc544553a7f1b1e4_Black.svg" alt="Fitnesso" />
                </a>
            </div>
            <div>
                <ul className="cat-right">
                    {CategoryItem.map((item, index) => {
                        return(
                            <li key={index} >
                                <a className={item.className} href = {item.url}>
                                    {item.title}
                                </a>
                            </li>
                        )
                    })}                    
                </ul>
            </div>
        </div>
    </div>
  )
}
