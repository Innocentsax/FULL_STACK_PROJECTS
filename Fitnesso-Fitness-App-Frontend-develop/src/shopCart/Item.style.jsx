import styled from 'styled-components'
export const Wrapper = styled.div`
 display: flex;
 justify-content: space-between;
 align-items: center;
 flex-direction: column;
 width: 100%;
 border-radius: 20px;
 height: 25rem;
 .prod_btn {
  border-radius: 5px;
  width: 80%;
  background-color: #ffbc42;
  position: absolute;
  bottom: 10px;
  left: 10%;
 }
 .prod_btn:hover{
    background-color: #ffa500;
 }
 .prod_desc, .prod_title, .prod_category, .prod_price{
   margin-bottom: 5px;
 }
.prod_title{
   text-transform: capitalize;
   white-space: nowrap;
   overflow: hidden;
   text-overflow: ellipsis;
   font-size: 13px;
}
.prod_desc{
   height: 70px; 
   background-color: #eee;
   margin-right: 3px;
   padding: 3px;
   overflow: hidden;
   color: #333;
}
.prod_desc::first-letter{
   text-transform: uppercase;
}
 .prod_category, .prod_desc{
    text-transform: capitalise;
    font-weight: normal;
    font-size: 12px;
 }
 img {
    height: 50%;
    width: 100%;
    object-fit: cover;
 }
 div {
    width: 100%;
    font-family: Arial, Helvetica, sans-serif;
    padding: 7px 7px 20px;
    height: 50%;
    position: relative;
    font-size: 14px;
 }
`