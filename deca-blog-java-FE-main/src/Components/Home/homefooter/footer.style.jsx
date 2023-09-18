import styled from "styled-components";

export const FooterStyle = styled.div`
    height: auto;
    width: 100%;
    background: #F2F4F7;
    padding-bottom: 40px;

.span{
    display: flex;
    margin-left: auto;
    margin-right: auto;
    width: 140px;
    padding-top: 39px;
}

.decablog{
    margin-top: 11px;
    margin-left: 10px;
    font-size: 1.2rem;
    font-weight: 600;

}

.footermini{
    max-width: 80%;
    margin-left: auto;
    margin-right: auto;
    text-align: center;
}

.foot{
    font-weight: 400;
    font-size: 0.8rem;
    line-height: 17px;
    color: #000000;
}

.footlast{
    margin-top: 20px;
}

.landingfooterText{
    margin-top: 30px;
}
.logo{
    display: flex;
  justify-content: center;
  align-items: center;
  width:400px;
}


@media only screen and (min-width: 450px){
    .foot{
        font-weight: 400;
        font-size: 1rem;
        line-height: 20px;
    }
}


`