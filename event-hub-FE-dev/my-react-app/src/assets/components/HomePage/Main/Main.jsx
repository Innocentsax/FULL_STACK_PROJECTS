
import '../css/HomePage.css'
import { useEffect } from "react";
import { useState } from "react";
import axios from "axios";

import arrow from '../image/svg/Arrow 1.svg'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faAngleDown, faArrowRight} from '@fortawesome/free-solid-svg-icons';
import EventData from './EventData';
import { BannerDiv,ButtonArrow,ButtonFilter,ButtonLoad,Div,DivRoleContent,Events,EvnetsBody,InsideDiv,Label,Loader,PositionForm, RoleEventFilter, UpcomingEvents } from '../../Styled/Styled';
import styled from 'styled-components';
import preloader from '../../CreateAccount/image/preloader.gif'


export default function Main(){
    const [loading, setLoading] = useState(false);
    const [events,setEvent] =useState([])

    useEffect(()=>{
        try {
            setLoading(true);
            axios.get(`http://localhost:8999/events/view-event/`, {
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*',
                },
            }).then((response) => {
                setLoading(false);
                setEvent(response.data.data.content);
                console.log(response.data.data.content);
            })
        }catch (e) {
            setLoading(false);
            console.log(e)
        }
    },[])
   
  

    return (
        <>
        <div style={{background:"#f8f8f8",paddingBottom:"50px"}}>
        <BannerDiv>
                <h1 className='h1'>Connect to all the event</h1>
                <h1 className='h1 mag-up'>happening around you</h1>
                <p className='expore'>Explore More <img src={arrow} width={35} /> </p>
        </BannerDiv>
        <PositionForm>
            <InsideDiv>
                <DivRoleContent>
                    <DivPad>
                    <Label>
                        What are you looking?<br />
                        <input type='text' placeholder='Search events' />
                    </Label>
                    <Label>
                        Location<br />
                        <input type='text' placeholder='Lagos, Nigeria' />
                    </Label>
                    <Label>
                        When<br />
                        <select>
                        <option>Most Recent</option>
                        <option>Later</option>
                        </select>
                    </Label>

                    <ButtonArrow>
                        <FontAwesomeIcon className='icon' icon={faArrowRight} />
                    </ButtonArrow>
                    </DivPad> 
                </DivRoleContent>
            </InsideDiv>
        </PositionForm>
        <UpcomingEvents>
            <EvnetsBody>
                <RoleEventFilter>
                    <h1 className='upcoming'>Upcoming events</h1>
                    <Div>
                    <ButtonFilter>
                           <span className='ccc'>All Category   <FontAwesomeIcon icon={faAngleDown} style={{marginLeft:"15px",color:"red"}}/></span>
                        </ButtonFilter>
                        <ButtonFilter>
                           <span className='ccc'>Event Type   <FontAwesomeIcon icon={faAngleDown} style={{marginLeft:"18px",color:"red"}}/></span>
                        </ButtonFilter>
                    </Div>
                </RoleEventFilter>

                {(loading) ? <>
                    <center><Loader src={preloader} alt="" ></Loader></center>
                <h1></h1>
                </>
                :

                <Events>
                    {console.log(events)}
                    {events.map((val,index)=>{
                        const date = new Date(val.startDate);
                        const monthName = new Intl.DateTimeFormat('en-US', { month: 'long' }).format(date);
                        const realVal =monthName.substring(0,3);

// Get the day of the week name

                        return (
                            <EventData
                            key={val.id} 
                            id={val.id}
                            eventName="eventName"
                            image ={val.bannerUrl}
                            name={val.title}
                            date="date" 
                            text={val.caption}
                            month={realVal}
                            day={date.getDay()}
                            />
                    
                        )
                    })
                    }
                </Events>
}

                <center>
                <ButtonLoad>
                    Load More Events
                </ButtonLoad>
                </center>

            </EvnetsBody>

        </UpcomingEvents>
        </div>
        </>
    )
}

const DivPad=styled.div`
width: 90%;
display:flex;
justify-content: space-between;
`
