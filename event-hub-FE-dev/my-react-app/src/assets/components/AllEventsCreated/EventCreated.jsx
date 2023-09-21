import {UpcomingEvents, SetUp2, ConfirmTitle, SetUp3, TheDetails2, Filter, FilterContent, HorLine, TheContent,
    CreatedEvents, FirstDetails, SecondDetails, ThirdDetails, Active,InActive, TicketSold, Loader} from "../Styled/Styled.jsx";
import './EventCreated.css'
import {GrFormFilter} from "react-icons/gr";
import picture from '../HomePage/image/image-2.png'
import {FiEdit} from "react-icons/fi";
import { useState } from "react";
import axios from "axios";
import { useEffect } from "react";
import { useParams,Link } from 'react-router-dom';
import preloader from '../CreateAccount/image/preloader.gif'

export default function EventCreated(){
const TOKEN =localStorage.getItem("TOKEN");
const { id } = useParams();

console.log(TOKEN)

const [events,setList] = useState([])
const [loading,setLoading] = useState(false)


    useEffect(()=>{
        try{
            setLoading(true)

 
   axios.get("http://localhost:8999/events/view-event-by-user/",{
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*' ,
            'Authorization': `Bearer ${TOKEN}`
          },
          params:{
            pageNo:id
          }
    }).then((response)=>{
        setList(response.data.data.content)
        setLoading(false)

    })


}catch(e){
    console.log(e)
    setLoading(false)
}
},[])
    





    return(
        <div style={{background:"#f8f8f8",paddingBottom:"50px"}}>
           <UpcomingEvents>
               <SetUp2>
                   <ConfirmTitle>
                       <p className='eventCreated'>All events created</p>
                       <p className='total'>4 events created so far.</p>
                   </ConfirmTitle>
                   <Filter>
                       <FilterContent>
                           <TheContent>
                               <GrFormFilter className='filter'/>
                               <p className='filterText'>Filter</p>
                               <HorLine/>
                               <p className='filterText'>Date</p>
                           </TheContent>
                       </FilterContent>
                   </Filter>
               </SetUp2>
               {(loading) ?
  
  <center>
    <Loader style={{margin:"20px"}} src={preloader}></Loader>
  </center>


: <>
               {events.map((val,index)=>{
                return(
                    <SetUp3 key={val.id}  style={{marginBottom:"20px"}}>

                    <CreatedEvents>
                        <img src={val.bannerUrl} alt=''/>
                    </CreatedEvents>
                    <FirstDetails>
                        <TheDetails2>
                            <p>Event Title</p>
                            <span >{val.title}</span>
                        </TheDetails2>
                        <TheDetails2>
                            <p>Organizer</p>
                            <span >{val.organizer}</span>
                        </TheDetails2>
                        <TheDetails2>
                            <p>Ticket Class</p>
                            <span >VVIP, VIP & Regular</span>
                        </TheDetails2>
                    </FirstDetails>
                    <SecondDetails>
                        <input type="hidden" value={val.id} />
                        <TheDetails2>
                            <p>Category</p>
                            <span >{val.category.replaceAll("_"," ")}</span>
                        </TheDetails2>
                        <TheDetails2>
                            <p>Location</p>
                            <span >{val.location}</span>
                        </TheDetails2>
                        <TheDetails2>
                            <p>Date & Time</p>
                            <span >{val.startDate} - {val.endDate}  {val.startTime} - {val.endTime}</span>
                        </TheDetails2>
                    </SecondDetails>
                    <ThirdDetails>
                       
                           {(val.active) ? <Active><p>Active</p></Active> : <InActive><Link to={`/event/confirmation/${val.id}`}>Activate Event</Link></InActive> } 
                      
                        <TicketSold>View Tickets Sold</TicketSold>
                        <FiEdit className='editEvent'/>
                    </ThirdDetails>
                </SetUp3>
                )

               })}

               </>

            }
              
           </UpcomingEvents>
        </div>
    )
}


