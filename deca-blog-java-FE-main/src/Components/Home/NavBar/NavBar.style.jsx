import styled from "styled-components"

export const NavBarStyle = styled.div`
display: flex;
flex-direction: row;
align-items: center;
padding: 0px 100px;
width: 100%;
justify-content: space-between;
overflow-y: hidden;
overflow-x: hidden;
height: 94px;
left: 0px;
top: 0px;
border-color: 1px solid black;

background: #FFFFFF;
box-shadow: 0px 4px 8px rgba(33, 33, 33, 0.08);

.iconDiv{
display: flex;
flex-direction: row;
align-items: center;
justify-content: space-between;
padding: 0px;
width: 104px;
height: 24px;
}

.searchDiv{
    box-sizing: border-box;

/* Auto layout */

display: flex;
flex-direction: row;
align-items: flex-start;
padding-top: 15px 24px;
padding-left: 45px;
gap: 8px;

width: 482px;
height: 36px;

/* White */

background: #FFFFFF;
/* Grey 300 */

border: 1px solid #D0D5DD;
}
.iconImage{
position: absolute;
top: 38px;
left: 630px;
}
`