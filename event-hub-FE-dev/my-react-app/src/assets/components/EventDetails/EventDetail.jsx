import './css/EventDetails.css'

import MapOfEvent from "./MapOfEvent.jsx";
import MoreEvents from "./MoreEvents.jsx";
import { BannerDiv,EventDate, UpcomingEvents,EvnetsBody,ButtonForm,ButtonFormWhite,
    GobackDiv, Divs, EventName, Caption, BuyModal, ModalCalender, DetailedEvent, Divs2,
    EventDesc, Description, DetailedDescription, StartDate, StartDateName, StartDateValue,
    EventMap, EventLocation, OtherEvents, ShareSocial, MapOfEvents, SecondDiv } from '../Styled/Styled'
import {Link, useParams} from "react-router-dom";
import { useState,useEffect } from 'react';
import axios from 'axios';


  
export default function EventDetail(){

    const home=()=>{
        window.location.href="/"
    }
    const TOKEN=localStorage.getItem("TOKEN")
    const { id } =useParams()
    const [loading, setLoading] = useState(false);
    const [event,setEvent] =useState([])
    const [appUser, setAppUser] = useState([]);
    const [allTickets,setAllTickets] =useState([])
    const [category,setCategory] =useState("")
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
              setAppUser(response.data.data.appUser);
              console.log(response.data.data);
            });
          } catch (e) {
            setLoading(false);
            console.log(e);
          }          

    },[])
  
    return (
        <div style={{background:"#f8f8f8",paddingBottom:"50px"}}>
         <BannerDiv >

               <GobackDiv onClick={home} style={{cursor:"pointer"}}>
                <Divs>
                    <p className='back'>← Go Back</p>
                <EventName>
                    <h1 className='h1'>{event.title}</h1>
                </EventName>
                <Caption>
                    <p className='details'>{event.caption}</p>
                </Caption>
                </Divs>
                
                <BuyModal>
                    <p className='datex'>Date & Time </p>
                    <p className='time'>Saturday, Nov 06, 2021 at 20:30PM </p>
                    <ModalCalender>
                        <p className='calender'>+ Add to Calender</p>
                    </ModalCalender>
                    <ButtonForm>
                    Buy Ticket
                    </ButtonForm>
                    <ButtonFormWhite>
                    Save Ticket
                    </ButtonFormWhite>
                    </BuyModal>

                </GobackDiv> 



            </BannerDiv>
            <UpcomingEvents>
            <EvnetsBody>
                <DetailedEvent>
                    <Divs2>
                        <EventDesc>
                            <Description>Description</Description>
                            <DetailedDescription>{event.description}.</DetailedDescription>
                        </EventDesc>
                        <EventDesc>
                            <p className='eventdate'>Date and Time</p>
                            <EventDate>
                                <StartDate>
                                    <StartDateName>Start Date</StartDateName>
                                    <StartDateValue>{event.startDate}</StartDateValue>
                                </StartDate>
                                <StartDate>
                                    <StartDateName>End Date</StartDateName>
                                    <StartDateValue>{event.endDate}</StartDateValue>
                                </StartDate>
                                <StartDate>
                                    <StartDateName>Time</StartDateName>
                                    <StartDateValue>{event.startTime}</StartDateValue>
                                </StartDate>
                            </EventDate>
                        </EventDesc>
                        <EventDesc>
                            <p className='eventdate'>How to contact the organizer</p>
                            <p className='contactInfo'>Please email <a href={`mailto:${appUser.email}`}>{appUser.email}</a> or call {appUser.phone}</p>
                    </EventDesc>
                    </Divs2>
                    <SecondDiv>
                        <EventMap>
                            <EventLocation>Event Location</EventLocation>
                            <MapOfEvents>
                                <MapOfEvent />
                            </MapOfEvents>
                        </EventMap>
                        <EventMap>
                            <ShareSocial>Share with Friends</ShareSocial>
                        </EventMap>
                    </SecondDiv>
                </DetailedEvent>
                <OtherEvents>
                    <p className='otherEvent'>Other event you might like</p>
                </OtherEvents>
                <MoreEvents/>
            </EvnetsBody>
            </UpcomingEvents>
        </div>
    )
    
}


