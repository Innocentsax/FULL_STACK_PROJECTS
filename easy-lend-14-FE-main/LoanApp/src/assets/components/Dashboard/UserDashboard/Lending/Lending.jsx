import Header from "../../../Header/Header";
import { Flex } from "../../../Styled/Styled";
import { App } from "../DashboardStyled";
import SidebarD from "../SidebarD";
import { Button ,LendingContainer,Layout,Layout1,TimeFormat,Buxrate,ButtonLoan} from "../../RegisterDashboard/Styled-dashboard";
import { FaAddressCard } from "react-icons/fa";

const Lending = ()=>{
  
    return (
        <>
        <Header navbar={[]} />
        <Flex>
            <SidebarD />
            <App>
            <h1>Lending Offers</h1>
            <p>See list of people willing to lend money at a repayment percentage.</p>

            <LendingContainer>
            <Layout>
                <Layout1><TimeFormat>Today Wednesday 30,2023</TimeFormat>
                <Button>Accespt Loan</Button>
                
                </Layout1>
                <span>&nbsp;Micheal John</span>
                <Layout1>
                    <span style={{width:"60%"}}>Lpariatur  fugit saepe dolorem obcaecati autem dolore reprehenderit quia alias sapiente. Sapiente, labore.</span>

     <Button style={{background:"white",color:"black",border:"1px solid #ccc"}}>Message <FaAddressCard /></Button>
                </Layout1>
                <Layout1>
                   <Buxrate>
                   <ButtonLoan>Offering N200,000</ButtonLoan>
                   <ButtonLoan style={{color:"green"}}>0.8% Percentage</ButtonLoan>
                   <ButtonLoan>90days Repayment</ButtonLoan>
                   </Buxrate>

  
                </Layout1>
                </Layout>
                <Layout>
                <Layout1><TimeFormat>Today Wednesday 30,2023</TimeFormat>
                <Button>Accespt Loan</Button>
                
                </Layout1>
                <span>&nbsp;Micheal John</span>
                <Layout1>
                    <span style={{width:"60%"}}>Lpariatur  fugit saepe dolorem obcaecati autem dolore reprehenderit quia alias sapiente. Sapiente, labore.</span>

     <Button style={{background:"white",color:"black",border:"1px solid #ccc"}}>Message <FaAddressCard /></Button>
                </Layout1>
                <Layout1>
                   <Buxrate>
                   <ButtonLoan>Offering N200,000</ButtonLoan>
                   <ButtonLoan style={{color:"red"}}>0.8% Percentage</ButtonLoan>
                   <ButtonLoan>90days Repayment</ButtonLoan>
                   </Buxrate>

  
                </Layout1>
                </Layout>
                <Layout>
                <Layout1><TimeFormat>Today Wednesday 30,2023</TimeFormat>
                <Button>Accespt Loan</Button>
                
                </Layout1>
                <span>&nbsp;Micheal John</span>
                <Layout1>
                    <span style={{width:"60%"}}>Lpariatur  fugit saepe dolorem obcaecati autem dolore reprehenderit quia alias sapiente. Sapiente, labore.</span>

     <Button style={{background:"white",color:"black",border:"1px solid #ccc"}}>Message <FaAddressCard /></Button>
                </Layout1>
                <Layout1>
                   <Buxrate>
                   <ButtonLoan>Offering N200,000</ButtonLoan>
                   <ButtonLoan style={{color:"green"}}>0.8% Percentage</ButtonLoan>
                   <ButtonLoan>90days Repayment</ButtonLoan>
                   </Buxrate>

  
                </Layout1>
                </Layout>
               


            </LendingContainer>


            </App>
        </Flex>

        
        </>

    );
}

export default Lending;

