import React, {  useEffect, useState } from "react";
import Calendar from "react-calendar";
import frame from "../../data/image/Frame.png";
import 'react-calendar/dist/Calendar.css';
// import "./dashboard.css";
import { IoCheckmark } from "react-icons/io5";
import { useNavigate } from "react-router-dom";
import { useStateContext } from "../../context/ContextProvider";

export function Button(props){

    return <button onClick={props.onClick} className={props.className}  > {props.children}</button>
}


function TaskBox(props){
return (<div className={`h-[84px] flex flex-col items-center justify-between px-[24px] py-[8px] rounded-2xl ${props.className}`}>
        <p className="font-normal"  >{props.title}</p>
        <p className="font-semibold">{props.number}</p>
    </div>)
}

function AwardBox(props){
    return (<div className={`h-[84px] flex flex-col items-center justify-between px-[13.5px] py-[8px] rounded-2xl ${props.className}`}>
            <p className="font-normal"  >{props.title}</p>
            <p className="font-semibold">{props.number}</p>
        </div>)
    }





export default function Dashboard(props){

 const [date, setDate] = useState(null);
 const [calendarDate, setCalendarDate] = useState(new Date());
 const [calendar, showCalender] = useState(false);
 const navigate = useNavigate()

const formateDate = ()=>{
    const currentDate = calendarDate;
 
    const year = currentDate.getFullYear(); 
    const month = String(currentDate.getMonth() + 1).padStart(2, '0'); 
    const day = String(currentDate.getDate()).padStart(2, '0');
  
    const formattedDate = `${year}/${month}/${day}`;
         return formattedDate;
}

const showCalenderFunction = ()=>{
   
    showCalender(!calendar);
   
}


const setDateFunction= ()=>{
 
  showCalenderFunction();
    setDate(formateDate());
}


 useEffect(()=>{
   setDate(formateDate());


 },[])

    const context = useStateContext();

  return (<div className="px-12 pt-6 flex-auto flex-col bg-dashboardbg w-full">
      <div className="flex flex-row justify-between w-full">
       <span className="block font-bold text-2xl not-italic">Dashboard</span>
       <span className="block font-normal text-base text-grey"> {date}  </span>
      </div>
      {/* this is the card section */}
      <div className="mt-10 px-16 pt-10 pb-10 bg-cardcolor rounded-md flex flex-row justify-between mb-[24px]"> 
       <div className="">
       <p className="text-white text-2xl mb-6 font-semibold">Welcome, {context.firstname} </p>
       <p className="text-white text-sm max-w-[552px] text-justify mb-6 font-normal">
        this is is djejef rjf jjjfejfjr jjfj jfj nhnehe ujehvfbeybfhj fbfuf
        bfhrhrhrej3rhrhrh ubfeh bfbrufh ufbfuhb ufufru ufbueuh u hrhfjhh
        fhfhh fbvbruehsn udheh yh jrhrhfbr jhebsbs hjejeh hjjjee b
       </p>

       <Button onClick={showCalenderFunction}   text={ `view calender`}  className="text-cardcolor bg-white text-sm p-2 rounded-lg border border-viewcalendarborder border-solid"> view calendar </Button>
      
       </div>
       <div className="hidden xl:block">

        <img width="260px" alt="frame" height="216px" src={frame}/>
       </div>
      
       </div>
       {/* tasks of the month section */}
       <div className="bg-white py-[25px]  px-[32px] mb-[24px] relative">
        <div className="flex flex-row justify-between items-center pb-[33px]">
        <p> Tasks for the month </p>
         
       <Button children="view all tasks" className="block text-cardcolor bg-white text-sm p-2 rounded-lg border border-viewcalendarborder border-solid"/>
        {/* calendar section */}
        { calendar && <div className="absolute z-[200] bg-white"> 

            <Calendar onChange={(value, event)=>{setCalendarDate(value)}} value={calendarDate} className="calendar bg-white border-0 px-[10px] py-[10px] mb-[10px]"/>
            <div className="flex flex-row justify-between pb-[20px] px-[20px]">
            <Button onClick={showCalenderFunction} children="Cancel" className="block text-cardcolor bg-dashboardbg px-[25px] py-[10px] text-sm  rounded-lg border-viewcalendarborder border-solid"/>
            <Button onClick={setDateFunction} children={<div className="flex flex-row justify-center items-center"> <IoCheckmark className="mr-[5px] text-lg"/> Set date</div>} className="block text-white bg-cardcolor px-[25px] text-sm p-2 rounded-lg border-viewcalendarborder border-solid"/>

            </div>
        </div> }
        </div>

        <div className="flex flex-row justify-between gap-x-1 flex-wrap gap-y-1">
         <TaskBox number={6} title="Ongoing" className="bg-ongoing w-[166px]"/>
         <TaskBox number={6} title="Completed" className="bg-completed w-[166px]"/>
         <TaskBox number={6} title="Pending" className="bg-pending w-[166px]"/>
         <TaskBox number={6} title="Canceled" className="bg-canceled w-[166px]"/>
         <TaskBox number={6} title="Total Task" className="bg-total w-[166px]"/>
        </div>
        
        
       </div>

       {/* Award and recognition */}
       <div className="bg-white py-[25px]  px-[32px] mb-[24px]">
        <div className="flex flex-row justify-between items-center pb-[33px]">
        <p> Awards and Recognitions </p>
         
       <Button onClick={()=>{
        navigate("/staff/reward-recognition")
       }} children="view Awards" className="block text-cardcolor bg-white text-sm p-2 rounded-lg border border-viewcalendarborder border-solid"/>
      
        </div>

        <div className="flex flex-row justify-between gap-x-1  flex-wrap gap-y-1">
         <AwardBox number={6} title="Leardership" className="bg-award w-[190px]"/>
         <AwardBox number={6} title="Others-first" className="bg-award  w-[190px]"/>
         <AwardBox number={6} title="Employee of the year" className="bg-award  w-[190px]"/>
         <AwardBox number={6} title="Loyalty" className="bg-award  w-[190px]"/>
         <AwardBox number={6} title="Character" className="bg-award  w-[190px]"/>
        </div>
        
        
       </div>

       {/* meeting section */}

       <div className="bg-white py-[25px]  px-[32px] mb-[24px]">
        
        {"You have 0 urgent meeting"}
        
        
       </div>


  </div>)

}


