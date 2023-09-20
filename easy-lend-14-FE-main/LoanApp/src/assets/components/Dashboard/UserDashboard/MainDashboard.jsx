import { Flex, Input, LabelI, Modar } from "../../Styled/Styled";
import SidebarD from "./SidebarD";
import frame from './Images/Frame 38813610.png'
import {useState,useEffect} from 'react'
import axios from 'axios'
import swal from "sweetalert";
import reciept from './Images/receipt-alt.png'
import { Button } from "../RegisterDashboard/Styled-dashboard";
import { DivI,App,
    DivIm, 
    DivImp,
    DivImp2,AppContent, 
    LoanModar, RoleModar
    ,RoleModar2,
    FormI } from "./DashboardStyled";
import { config } from "../../../Utils/AppUtils";




const MainDashboard =()=>{

    const [year,setYear] = useState(0)
    const [month,setMonth] = useState(0)
    const [day,setDay] = useState("")
    const [loan,setLoan] = useState(false)
    const [loading, setLoading] = useState(false);
    const [state, setState] = useState(false);





    const [confirmState, setConfirm] = useState(false);
    const [formData, setFormData] = useState({
        loanAmt: "",
        date: "",
        purpose: "",
        interestRate: "",
        requiredDocuments: "",
      });

    const loanIn =()=>{
        console.log(loading)
        setLoan(true)
        setConfirm(false)
    }

    const goBack =()=>{
        setLoan(false)
        setConfirm(false)

    }
      const handleChange = (event) => {
        const { name, value } = event.target;
        setFormData((prevFormData) => ({
          ...prevFormData,
          [name]: value
        }));
    }

    const [createdAt,setCreated] = useState("")
    const [interestRate,setinterestRate] = useState("")
    const [loanAmt,setloanAmt] = useState(0)
    const [purpose,setpurpose] = useState("")
    const [repaymentTerm,setrepaymentTerm] = useState("")
    const [updatedAt,setupdatedAt] = useState("")
    const [totalRepayment,settotalRepayment] = useState("")
    const [data,setData] = useState([])


useEffect(()=>{
    console.log(data)

    fetch("http://localhost:8084/api/loanApplication",config)
    .then(response=> response.json())
    .then((data)=>{
    
        console.log(createdAt)
        console.log(purpose)
        console.log(repaymentTerm)
        console.log(updatedAt)
        console.log(data)
        if(data.length>0){
            setState(true)
            setData(data)
            console.log(data[0])
            setCreated(data[0].createdAt)
            setinterestRate(data[0].interestRate)
            const loanAm = convert(data[0].loanAmt)
            setloanAmt(loanAm)
            setpurpose(data[0].purpose)
            setrepaymentTerm(data[0].repaymentTerm)
            setupdatedAt(data[0].updatedAt)
            settotalRepayment(data[0].totalRepayment)
        }
    })
    const dates = new Date();
    setYear(dates.getFullYear())
    setMonth(dates.getMonth())
    const week = [
        "Sunday",  "Monday",  "Tuesday",  "Wednessday",  "Thursday",  "Friday",  "Saturday"
    ]
    setDay(week[dates.getDay()])
},[])

function convert(loanAmt){
    const nairaFormat = loanAmt.toLocaleString("en-NG", {
        style: "currency",
        currency: "NGN",
      });
      
      console.log(loanAmt); // Output: ₦1,234,567.89
      console.log(nairaFormat)
      
      const reducedFloat = loanAmt.toFixed(2);
      console.log(reducedFloat);

     
      return  reducedFloat;
}
// convert(loanAmt)

    const confirm =()=>{
        setLoan(false)
        setConfirm(true)

    }

    const saved =(e)=>{
        e.preventDefault()


       axios.post("http://localhost:8084/api/loanApplication/apply",{
            loanAmt:formData.loanAmt,
            interestRate:formData.interestRate,
            repaymentTerm:12,
            totalRepayment:2334,
            purpose:formData.purpose
        },config)
        

          .then((response) => {
            setLoading(false)
            console.log(response);
            swal("ALERT","SUCCESS","success")
           
          })
          .catch((error) => {
            setLoading(false)
            console.error(error);
          });

    }
  
    return (
        <>
        <Flex>
            <SidebarD />
            <App>
                <h1>Welcome to the dashboard</h1>
            <p>{day} {month} ,{year}</p>
         
           
           {(state) ?
           <DivI>
            <DivImp>
                <h2>Total Amount Borrowed</h2>
                <h1 style={{color:"#222",fontSize:"30px"}}><strike style={{color:"#222",fontSize:"30px"}}>N</strike>{loanAmt}</h1>
                <p>Interest Rate 0.5%</p>

            </DivImp>
            <DivImp>
                <h2>OutStanding Payemnt</h2>
                <h1 style={{color:"red",fontSize:"30px"}}><strike style={{color:"red",fontSize:"30px"}}>N</strike>{totalRepayment}</h1>
                <p>Interest Rate {interestRate}%</p>

            </DivImp>
            <DivImp2>
                <h2>Total Amount Borrowed</h2>
                <p>Amount</p>
                <p>Repayemt</p>

                <p>Date</p>
                <p>Interest per day</p>
                <p>Next Per day</p>

            </DivImp2>


           </DivI>
           :
           <>
              <DivIm>
            <img src={frame}  alt="" width={100} />
            </DivIm>
            

            </>
           }

            <AppContent>
                <img src={reciept} alt=" " width={50} height={50} />
                <h1>No Loans Invited yet</h1>
                <p>You have not initiated any loan applications thus far.</p>
                <Button onClick = {loanIn}>Request Loan</Button>


            </AppContent>
      

            </App>

            {(loan && !confirmState) ?
            <>
            <LoanModar>
                <Modar>
                    <RoleModar>
                        <h1>Loan Request</h1>
                        <button onClick={goBack}>x</button>
                    </RoleModar>
                    <FormI>
                    <LabelI style={{width:"100%"}}>
                        <p></p>
                        Loan Amount
                        <Input type="number" 
                        onChange={handleChange} 
                        value={formData.loanAmt} 
                        name="loanAmt"
                        placeholder="Loan Amount"></Input>
                    </LabelI>
                    <LabelI style={{width:"100%"}}>
                        <p></p>
                        Select Date
                        <Input type="date" 
                        onChange={handleChange} 
                        value={formData.date} 
                        name="date"
                        placeholder="Select Date"></Input>
                    </LabelI>
                    <LabelI style={{width:"100%"}}>
                        <p></p>
                        Select Purpose
                        <Input type="purpose" 
                        onChange={handleChange} 
                        value={formData.purpose} 
                        name="purpose"
                        placeholder="Select Purpose"></Input>
                    </LabelI>
              
                    <LabelI style={{width:"100%"}}>
                        <p></p>
                        Interest Rate 
                        <Input type="number" 
                        onChange={handleChange} 
                        value={formData.interestRate} 
                        name="interestRate"
                        placeholder="Enter  Interest Rate"></Input>
                    </LabelI>
                    <LabelI style={{width:"100%"}}>
                        <p></p>
                        Upload Supporting Document
                        <Input type="file" 
                        onChange={handleChange} 
                        value={formData.requiredDocuments} 
                        name="requiredDocuments"
                       ></Input>
                    </LabelI>

                    <Button onClick={confirm}>Continue</Button>
                    </FormI>
                </Modar>
            </LoanModar>

            
            </>
            :""
            
        }

{confirmState ? (
  <LoanModar>
    <Modar>
    <RoleModar>
                        <h1>Confirmation</h1>
                        <button onClick={goBack}>x</button>
                    </RoleModar>

                    <RoleModar2 >
                    <RoleModar>
                    <p>Amount</p>
                  
                 
                        <p><strike>N</strike>{formData.loanAmt}</p>
                        </RoleModar>
                        <RoleModar>
                        <p>Date</p>
                        <p>{formData.date}</p>
                        </RoleModar>
                        <RoleModar>
                        <p>Purpose</p>
                        <p>{formData.purpose}</p>
                        </RoleModar>
                        <RoleModar>
                        <p>Interest rate</p>
                        <p>{formData.interestRate}</p>
                        </RoleModar>
                        <RoleModar>
                        <p>Supporting Document</p>
                        <p>NIN Card</p>
                        </RoleModar>
                        <Button onClick={saved}>Submit</Button>

                    </RoleModar2>
    </Modar>
  </LoanModar>
) : (
  ""
)}


        </Flex>
        </>
    )
}

export default MainDashboard;


