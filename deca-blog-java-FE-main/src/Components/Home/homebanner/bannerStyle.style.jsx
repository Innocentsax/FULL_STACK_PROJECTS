import styled from "styled-components"
import background from "../../../assets/Decadevs.png"
export const BannerStyle = styled.div`
height: 487px;
 /* background: rgb(65,121,9); */
/* background: linear-gradient(90 deg, rgba(65,121,9,1) 0%, rgba(255,255,255,1) 100%); */
position: relative;
width: 100%;
left: 0px;
top: 0px;
display: flex;
flex-direction:column;
justify-content: center;
align-items: center;
overflow-x: hidden;
overflow-y: hidden;
background: linear-gradient(101.01deg, #34A853 -21.26%, rgba(0, 0, 0, 0.08) 101.28%);
background-Position: center;
background-Size: cover;  
    
.overlay{
position: absolute;
width: 1440px;
height: 487px;
left: 0px;
top: 0px;
background: linear-gradient(101.01deg, #34A853 -60.26%, rgba(0, 0, 0, 0.08) 101.28%);
}
.firstText{
    width: 823px;

/* Headline Bold - 58 */

font-family: 'Inter';
font-style: normal;
font-weight: 700;
font-size: 58px;
/* or 81px */
align-items: center;
text-align: center;
letter-spacing: -0.5px;

/* White */
color: #FFFFFF;

}
.textDiv{
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: 100%;
    z-index: 234;
}
.buttonDiv{
    z-index: 243;
    display: flex;
    width: 30%;
    align-items: center;
    justify-content: space-around;
    margin-top: 30px;
}

`