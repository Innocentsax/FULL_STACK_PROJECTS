import { Avatar } from "@mui/material";
import './TestimonialCard.css'

const TestimonialCard = (props) =>{
    return (

        <div className="flex items-center flex-col text-center py-5 ">
            <Avatar src={props.avatar} style={{width: 120, height:120, border: '2px solid lightgray', padding: 0.5}} />
            <p  className="mt-5">{props.text}</p>
            <p className="mt-5 font-bold">{props.cName}</p>
            <p className="mt-2 text-[0.7rem] text-[#d8a600]">{props.occupation}</p>
        </div>

    );
}


export default TestimonialCard;