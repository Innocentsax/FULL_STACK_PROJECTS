import { FaSearch } from 'react-icons/fa';
import React from 'react';
import styled from 'styled-components';

const SearchBar = () => {
  return (
    <SearchBarDIV>
         
        <FaSearch /> 
        <SearchInputDIV className='SearchInputDIV ' placeholder='search for venue'></SearchInputDIV>
     
    </SearchBarDIV>
  );
};

export default SearchBar;

const SearchBarDIV = styled.div`

  box-sizing: border-box;
  background: white;
  width: 802px;
  height: 74px;
  left: 0px;
  top: 0px;

  border: 1px solid rgba(37, 45, 66, 0.29);
  border-radius: 4px;
  display:flex;
  align-items:center;
  justify-content: space-around;
`


 const SearchInputDIV = styled.input`
  width: 80%;
  height: 80%;
  border:0px;
  outline:none;

  font-family: 'Manrope';
  font-style: normal;
  font-weight: 400;
  padding-left:10px;
  font-size: 16px;
  line-height: 27px;
  color: #252d42;
`