import styled from 'styled-components'

export const ContentStyle = styled.div`
    height: auto;
    width: 80%;
    margin-left: auto;
    margin-right: auto;
    padding-top: 60px;
    padding-bottom: 30px;


.contentHeader{
font-weight: 700;
font-size: 1.5rem;
color: #101828;

}

.contentText{
    margin-left: auto;
    margin-left: auto;
    text-align: center;
width: 100%;
} 

.contentSubtitle{
    width: 100%;
height: 20px;
margin-top: 15px;

font-weight: 400;
font-size: 14px;
line-height: 20px;
color: #101828;
}

.contentFlex{
    margin-top: 40px
}

.story{
    margin-top: 10px;
}

.hide{
    display: none;
}

@media only screen and (min-width: 450px){

    .contentFlex{
        display: grid;
        justify-content: center;
        grid-template-columns: 50% 50%;
        grid-template-rows: 200px;
        width: 100%;
        text-align: center;
    }

    .hide{
        display: block
    }
}

@media only screen and (min-width: 600px){
    .contentHeader{
        font-size: 2rem;
        
        }
        .contentFlex{
            grid-template-columns: 33% 33% 33%;
        }

}

@media only screen and (min-width: 950px){
    .contentFlex{
        grid-template-columns: 20% 20% 20% 20%;
        column-gap: 50px;
    }
}

@media only screen and (min-width: 1200px){
    .contentFlex{
        column-gap: 20px;
    }
}

`