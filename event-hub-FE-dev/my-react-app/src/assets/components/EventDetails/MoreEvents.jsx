import image from '../HomePage/image/event_1.png'
import EventData from '../HomePage/Main/EventData';
import { Events } from '../Styled/Styled';

export default function MoreEvents(){
    const event = [
        {
            text:"Lorem ipsum dolor, sit amet consectetur adipisicing elit. Cum molestias, atque nam quas soluta dolore ut,",
            name:"Event Name which can be long",
            date:"Oct",
            month:"Oct"
        },
        {
            text:"Lorem ipsum dolor, sit amet consectetur adipisicing elit. Cum molestias, atque nam quas soluta dolore ut,",
            name:"Event Name which can be long",
            date:"Oct",
            month:"Oct"
        },
        {
            text:"Lorem ipsum dolor, sit amet consectetur adipisicing elit. Cum molestias, atque nam quas soluta dolore ut,",
            name:"Event Name which can be long",
            date:"Oct",
            month:"Oct"
        }
    ]

    return (
        <>
            <Events>
                {event.map((val,index)=>{
                    return (
                        <EventData
                        key={index} 
                        eventName="eventName"
                        image ={image} 
                        name={val.name} 
                        date="date" 
                        text={val.text} 
                        month={val.month}
                        day={val.date}
                        />
                
                    )
                })
                }
            </Events>
        </>
    )
}
        