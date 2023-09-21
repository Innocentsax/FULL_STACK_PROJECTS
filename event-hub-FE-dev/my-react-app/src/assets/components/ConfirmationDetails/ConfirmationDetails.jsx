import {ButtonForm, UpcomingEvents, SetUp2, ConfirmTitle, EditDetails, EditBox,
    AllEvent, Line, TheDetails, OtherConfirmation, Loader, } from "../Styled/Styled.jsx";
import './Confirmation.css'
import {AiFillEdit} from "react-icons/ai";
import { useParams } from "react-router-dom";
import { useEffect } from "react";
import preloader from '../CreateAccount/image/preloader.gif'
import { useState } from "react";
import axios from "axios";

export default function ConfirmationDetails(){
    const TOKEN=localStorage.getItem("TOKEN")
    const { id } = useParams();
    const [loading, setLoading] = useState(false);
    const [showButton, setButton] = useState(false);
    const [event,setEvent] =useState([])
    const [allTickets,setAllTickets] =useState([])
    const [category,setCategory] =useState("")
    console.log(id)

    useEffect(()=>{
        try {
            setLoading(true);
            axios.get(`http://localhost:8999/events/view-event/${id}`, {
              headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Authorization': `Bearer ${TOKEN}`
              },
            }).then((response) => {
              setLoading(false);
              setEvent(response.data.data);
              console.log(response.data.data);
          
              if (response.data.data) {
                const val = response.data.data.category;
                const change = val.replaceAll("_", " ");
                setCategory(change);
                console.log(response.data.data.active)
                if (response.data.data.active === true) {
                  setButton(true);
                }
        
                // console.log(response.data.data.tickets);
                // console.log(theTicket)
                setAllTickets(response.data.data.tickets);
                console.log(allTickets);
              }
            });
          } catch (e) {
            setLoading(false);
            console.log(e);
          }          

    },[])
    const handleSendAndPublish = () => {
        try {
        setLoading(true);
        axios
            .put(
                `http://localhost:8999/events/activate-event/${id}`,
                {},
                {
                    headers: {
                        "Content-Type": "application/json",
                        "Access-Control-Allow-Origin": "*",
                        Authorization: `Bearer ${TOKEN}`,
                    },
                }
            )
            .then((response) => {
                setLoading(false);
                setEvent(response.data.data);
                console.log(response.data.data);
                window.location.reload(`../event/confirmation/${id}`);
                // Handle the response or any additional logic here
            })
        } catch (e) {
            setLoading(false);
            console.log(e);
        }
    };
 
    return (

        <>
            
  
        <div style={{background:"#f8f8f8",paddingBottom:"50px"}}>
            <UpcomingEvents>
                <SetUp2>
                    <ConfirmTitle>
                        <p className='title'>Confirmation Details</p>
                        <p className = "text">Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                    </ConfirmTitle>
                    <EditDetails>
                        <EditBox>
                            <AiFillEdit className='editIcon'/>
                            <p className='edit'>Edit</p>
                        </EditBox>
                        <AllEvent href="/user-event-created/0">
                            <p className='allEvents'>View all Events created</p>
                        </AllEvent>
                    </EditDetails>
                </SetUp2>
                <Line/>
               
                    
                            <OtherConfirmation>
                            {(loading) ?
  
  <center>
    <Loader style={{margin:"20px"}} src={preloader}></Loader>
  </center>


: 
                      <>
                    <TheDetails>
                        <p className = 'theDetailsName'>Event Title</p>
                        <span className='theDetailsDesc'>{event.title}</span>
                    </TheDetails>
                    <TheDetails>
                        <p className = 'theDetailsName'>Organizer</p>
                        <span className='theDetailsDesc'>{event.organizer}</span>
                    </TheDetails>
                    <TheDetails>
                        <p className = 'theDetailsName'>Ticket Class</p>
                        <span className='theDetailsDesc'>
                        {allTickets.map((v, i) => {


  return (
    <span>
      {v.ticketClass+" "}
    </span>
  );
})}
                        </span>
                       
                    </TheDetails>
                    <TheDetails>
                        <p className = 'theDetailsName'>Category</p>

                     
                        <span className='theDetailsDesc'>{category}</span>
                    </TheDetails>
                    <TheDetails>
                        <p className = 'theDetailsName'>Banner</p>
                        <img className='theDetailsImage' src={event.bannerUrl} alt=''/>

                    </TheDetails>
                    <TheDetails>
                        <p className = 'theDetailsName'>Location</p>
                        <span className='theDetailsDesc'>{event.location}</span>
                    </TheDetails>
                    <TheDetails>
                        <p className = 'theDetailsName'>Date & Time</p>
                        <p className='theDetailsDesc'>{event.startDate} - {event.endDate}</p>
                        <p className='theDetailsDesc2'>{event.startTime} - {event.endTime}</p>

                       
                    </TheDetails>

                    {(showButton) ? "":<ButtonForm onClick={handleSendAndPublish}>Send & Publish</ButtonForm> }

</>
                            }
                </OtherConfirmation>


            </UpcomingEvents>
        </div>
        </>
    )
}