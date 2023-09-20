import { BoxA, ImageDiv,
    Imgs, } from "../Styled/Styled"
const ImageContent = (props)=>{
    return (
        <>
        <BoxA>
            <ImageDiv>
                <Imgs src={props.image} alt=""></Imgs>
            </ImageDiv>
            <h1 style={{fontSize:"25px"}}> {props.type} Loan</h1>
            <p> {props.text}</p>

        </BoxA>
        
        </>
    )
}

export default ImageContent;
