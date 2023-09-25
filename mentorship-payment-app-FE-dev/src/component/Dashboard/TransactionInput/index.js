import React from 'react'
import './index.css';
import { ReactComponent as Search } from "./images/search.svg";

export const TransactionInput = () => (
  <div className='search-container'>
    <Search className='textimg'/>
     <input type="text"  placeholder="Search transactions" className="input" />

  </div>
);