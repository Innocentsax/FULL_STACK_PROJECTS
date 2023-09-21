import { ContentBox ,Content,Box,spanDiv, Items, ButtonForm} from "../Styled/Styled"
export default function Tickets(){
    return (
        <>
        
      <Content>

   <ContentBox>
       <Items>
            <Box><span>+</span> 0<span>-</span></Box>
            <span >100 available</span>
        </Items>
        <Items>
            <spanDiv><p>Table for 4 (Gold)  VVIP “Eko All Night Pool Party Festival”</p></spanDiv>
        </Items>
        <Items className="amount">
            <span>N150,000</span>
        </Items>
  </ContentBox>
  <ContentBox>
       <Items>
            <Box><span>+</span> 0<span>-</span></Box>
            <span style={{marginTop:'20px'}}>80 available</span>
        </Items>
        <Items>
        <spanDiv><p>Table for 4 (Gold)  VVIP “Eko All Night Pool Party Festival”</p></spanDiv>

        </Items>
        <Items className="amount">
            <span >N200,000</span>
        </Items>
  </ContentBox>
  <ContentBox>
       <Items >
            <Box><span>+</span> 0<span>-</span></Box>
            <span >100 available</span>
        </Items>
        <Items>
        <spanDiv><p>For 4 Regular “Eko All Night Pool Party Festival</p></spanDiv>
        
        </Items>
        <Items className="amount">
            <span>N150,000</span>
        </Items>
  </ContentBox>
  <ContentBox>
    <ButtonForm style={{width:"200px" ,marginTop:"50px", display:"flex" , justifyContent:"center", textAlign:"center", padding:"10px"}}>continue</ButtonForm>
       
        
  </ContentBox>
    </Content>

        
        </>
    )
}






