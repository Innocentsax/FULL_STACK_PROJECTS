import React, {useEffect, useRef, useState} from 'react'
import { Button } from './StaffDashboard';
import profilepic from "../../assets/images/dp.png";
import "./staffprofile.css";
import { AiOutlineFile,AiOutlineGift  } from "react-icons/ai";
import {GrUpload} from  "react-icons/gr";
import { FaUserClock } from "react-icons/fa";
import { AiOutlineTeam } from "react-icons/ai";
import { VscLocation } from "react-icons/vsc";
import icon from "../../assets/images/reward.png";
import { BsArrowRightShort,BsArrowLeftShort } from "react-icons/bs";
import axios from 'axios';
import { useStateContext } from "../../context/ContextProvider";



const initialProf ={
    "firstName": "",
    "lastName": "",
    "nickName": null,
    "position": "",
    "phoneNo": "",
    "email": "",
    "startDate": null,
    "social": null,
    "reportTo": null,
    "resumeUrl": null,
    "birthday": null,
    "address": null,
    "nextOfKin": null,
    "maritalStatus": null,
    "contractType": "",
    "imageUrl": null,
    "workLocation": null,
    "createdOn":"",

    "role": {
        "id": "",
        "name": "",
        "permissions": [],
        "authorities": [
            {
                "authority": ""
            }
        ]
    },
    "department": "",
    "nationality": null
}



function StaffProfile (props) {

    const [imageToggle, setToggle]= useState(false)
    const [profiletoggle, setProfileToggle] =useState(false)
    const [file, setFile] = useState({ "fileName":"No file", "fileType":"NAN"})
    const [personalDetails, setPersonalDetails] = useState({displayDOB:"",  dob:"", ms:"", nationality:"", phonenumber:"", address:""})
    const [nextOfKinDetails, setNextOfKinDetails] = useState({name:"", relationship:"", occupation:"", phonenumber:"", address:""})
    const [profile, setProfile]= useState(initialProf)
    const context = useStateContext();
    const token = "Bearer "+context.token;
    const headers =  {
        'Content-Type': 'application/json',
        'Authorization': token
    };
    const getprofile = ()=>{
        

        const url = "http://localhost:8080/api/v1/staff/viewProfile";

        axios.get(url,{headers})
            .then((response)=> {

                // console.log(response.data)
                setProfile(response.data)
                startDate(response.data.createdOn)
            })
            .catch((error)=>{
                console.log(error)
            })
    }

    const submitProfile = ()=>{
        const token = context.token;
        const headers = {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        }
        const url = "http://localhost:8080/api/v1/staff/profile";
        axios.put(url, {...profile} , {
            headers
        })
            .then(response => {
                // Handle successful response
                console.log("getprofile", response.data);
            })
            .catch(error => {
                // Handle error
                console.error(error);
            });


    }

    const startDate = (created)=>{
        // console.log('real date', created)

        const date = new Date(Date.parse(created));
        const currentDate = new Date();
        const currentMonth = currentDate.getMonth()+1;
        const currentYear = currentDate.getFullYear();

        // console.log("current", currentDate, currentMonth, currentYear)

        const day = date.getDate();
        const aMonth = date.getMonth()+1;
        const month = date.toLocaleString('default', { month: 'long' });
        const year = date.getFullYear();
        // console.log("acctual",aMonth, year )

        const startDate = `${month}-${year}`;

        const calculateYear = currentYear - year;
        const calculateMonth = currentMonth - aMonth;

        const value = (calculateYear > 0 )? `${calculateYear} year ${calculateMonth} month` : (calculateMonth > 0 )? `${currentMonth} month` : "less than a month";

        console.log("startdate" ,startDate )
        console.log("tenure" ,value)
        setStarting({startDate:startDate, tenure:value})



    }



    useEffect(()=>{
        getprofile()



    },[])


    console.log(profile)
    console.log("here", profile.nextOfKin !== null? profile.nextOfKin:"false");
    return (
        <div className='ml-[45px] mr-[40px] relative' >

            {/* this sections hold the header and button */}
            <div className='flex flex-row justify-between mt-[24px] mb-[32px]'>
                <span className="block font-bold text-2xl not-italic">Profile</span>
                <span className='block'><Button onClick={()=>{}}   text={ `view calender`}
                                                className="text-cardcolor bg-white text-sm p-2 rounded-lg border border-viewcalendarborder border-solid"> Saved changes </Button> </span>
            </div>
            {/* this is the first section that contains the image */}
            <div className='flex flex-row justify-between mb-[32px]'>
                <div className='p-[16px] flex flex-row bg-white w-[737px] h-[261px]'>
                    <div><img src={profilepic} alt="#" width="180px" height="180px" className='rounded-[50%]' /></div>
                    <div>
                        <p className='font-medium text-[24px] '>{ profile.firstName + " " + profile.lastName}</p>
                        <div className='grid grid-cols-2 gap-x-[5px]'>
                            <div>

                                <p className='text-[16px] m-[0px] p-[0px] leading-[38px]'><span style={{color:"#98A2B3"}} className='text'>Department:&nbsp;{profile.department}</span></p>
                                <p className='text-[16px] m-[0px] p-[0px] leading-[38px]'><span style={{color:"#98A2B3"}} className='text'>Nickname:&nbsp;{profile.nickName}</span></p>
                                <p className='text-[16px] m-[0px] p-[0px] leading-[38px]'><span style={{color:"#98A2B3"}} className='text'>Position:&nbsp;{profile.position}</span></p>
                                <p className='text-[16px] m-[0px] p-[0px] leading-[38px]'><span style={{color:"#98A2B3"}} className='text'>Phone no:&nbsp;{profile.phoneNo}</span></p>
                                <p className='text-[16px] m-[0px] p-[0px] leading-[38px]'><span style={{color:"#98A2B3"}} className='text'>E-mail:&nbsp;{profile.email}</span></p>
                            </div>
                            <div>

                                <p className='text-[16px] m-[0px] p-[0px] leading-9'><span style={{color:"#98A2B3"}} className='text'>Start Date:&nbsp;{(starting?starting.startDate:"")}</span></p>
                                <p className='text-[16px] m-[0px] p-[0px] leading-9'><span style={{color:"#98A2B3"}} className='text'>Tenure:&nbsp;{(starting?starting.tenure:"")}</span></p>
                                <p className='text-[16px] m-[0px] p-[0px] leading-9'><span style={{color:"#98A2B3"}} className='text'>Report to:&nbsp;{profile.reportTo}</span></p>
                                <p className='text-[16px] m-[0px] p-[0px] leading-9'><span style={{color:"#98A2B3"}} className='text'>Socials:&nbsp;</span></p>
                            </div>
                        </div>
                    </div>


                </div>
                <div className='p-[16px] bg-white w-[281px]'>
                    <div className='flex flex-row justify-between mb-[15px]'>
                        <span className="block font-medium text-[16px] not-italic">Upcoming Event</span>
                        <span className='block'><Button onClick={()=>{}}   text={ `view calender`}
                                                        className="text-cardcolor bg-white text-[12px] h-[17px] w-[95px] rounded-[4px] border border-viewcalendarborder border-solid"> View Calender </Button> </span>

                    </div>
                    {/* the event section */}
                    <div >
                        <div>
                            <div className='p-[12px]  bg-cardcolor'>
                                <div className='flex flex-row justify-between mb-[16px]'> <span style={{color: "#ECFDF3"}} className='block text-[12px] font-normal'>15 May 2017</span>  <span className='block relative w-[80px] '>
        <div style={{backgroundColor: "#81C784"}} className='absolute w-[24px] h-[24px] border-[1.5px] border-solid border-white rounded-[200px] z-[1] left-[0px]'> </div>
        <div style={{backgroundColor: "#FFB74D"}} className='absolute w-[24px] h-[24px] border-[1.5px] border-solid border-white rounded-[200px] z-[2] left-[21px]'> </div>
        <div style={{backgroundColor: "#E57373"}} className='absolute w-[24px] h-[24px] border-[1.5px] border-solid border-white rounded-[200px] z-[3] left-[42px]'> </div>
        <div style={{backgroundColor: "#757575"}} className='absolute w-[24px] h-[24px] border-[1.5px] border-solid border-white rounded-[200px] z-[4] left-[63px]'> </div>

         </span> </div>
                                <p className='text-[12px]'>hfhfh fjhf jfjfjj jndn jfneuej fbfu3j hfuw nne d idi in ie udbfu cbhd db</p>
                            </div>
                            <div className='p-[12px] mt-[8px] bg-cardcolor'>
                                <div className='flex flex-row justify-between mb-[16px]'> <span style={{color: "#ECFDF3"}} className='block text-[12px] font-normal'>15 May 2017</span>  <span className='block relative w-[80px] '>
        <div style={{backgroundColor: "#81C784"}} className='absolute w-[24px] h-[24px] border-[1.5px] border-solid border-white rounded-[200px] z-[1] left-[0px]'> </div>
        <div style={{backgroundColor: "#FFB74D"}} className='absolute w-[24px] h-[24px] border-[1.5px] border-solid border-white rounded-[200px] z-[2] left-[21px]'> </div>
        <div style={{backgroundColor: "#E57373"}} className='absolute w-[24px] h-[24px] border-[1.5px] border-solid border-white rounded-[200px] z-[3] left-[42px]'> </div>
        <div style={{backgroundColor: "#757575"}} className='absolute w-[24px] h-[24px] border-[1.5px] border-solid border-white rounded-[200px] z-[4] left-[63px]'> </div>

         </span> </div>
                                <p className='text-[12px]'>hfhfh fjhf jfjfjj jndn jfneuej fbfu3j hfuw nne d idi in ie udbfu cbhd db</p>
                            </div>

                        </div>

                    </div>

                </div>
            </div>
            {/* last section */}
            <div className='flex flex-row justify-between'>
                {/* profile */}
                {/* resume */}
                <div className='p-[24px] w-[520px] bg-white '>
                    <div className='border-b-[1px] border-solid border-[#EEEEEE] pb-[16px]'>
                        <div className='flex flex-row justify-between items-center'> <span className='block text-[24px] font-[500]'>Resume Information</span>  <span className='block text-[16px] text-[#0E82F6]'>Edit</span>  </div>
                        <table id="first-table">
                            <th className='w-[100px]'>File Type</th>
                            <th>File Name</th>
                            <th>Last Updated</th>
                            <tr>
                                <td className='flex flex-row items-center'><span> <AiOutlineFile className='m-[0px] mr-[5px]' /> </span> {file.fileType}</td>
                                <td>{file.fileName}</td>
                                <td>5days ago</td>

                            </tr>

                        </table>
                        <span onClick={()=>{setToggle(!imageToggle)}} style={{color:"#175CD3"}} className='flex flex-row w-[183px]   items-center border-[1px] border-solid border-[#D0D5DD] p-[10px] rounded-[8px] cursor-pointer'> <span className='block mr-[14px]'><GrUpload style={{color:"#175CD3"}} className='text-[#175CD3]'/></span>   Upload Resume</span>


                    </div>
                    {/* personal information */}
                    <div>
                        <div className='border-b-[1px] border-solid border-[#EEEEEE] pb-[16px]'>
                            <div className='flex flex-row justify-between items-center'> <span className='block text-[24px] font-[500]'>Personal Information</span>  <span className='block text-[16px] text-[#0E82F6] cursor-pointer' onClick={()=>{setProfileToggle(!profiletoggle)}}>Edit</span>  </div>
                            <table id="second-table">
                                <th className='w-[100px]'>Birthday</th>
                                <th>Address</th>
                                <th>Next of Kin</th>
                                <th>Marital Status</th>
                                <tr>
                                    <td className=''>{profile.birthday?displayDateOfBirth(profile.birthday):""}</td>
                                    <td>{profile.address? profile.address.length>10?profile.address.substring(0, 11)+"...." :profile.address:""}</td>
                                    <td>{profile.nextOfKin  !== null? profile.nextOfKin.name.length >10? profile.nextOfKin.name.substring(0,11)+"..." : profile.nextOfKin.name:""}</td>
                                    <td>{profile.maritalStatus}</td>

                                </tr>

                            </table>

                        </div>

                    </div>

                    {/* occupation information section */}
                    <div>
                        <div className='border-b-[1px] border-solid border-[#EEEEEE] pb-[16px]'>
                            <div className='flex flex-row justify-between items-center'> <span className='block text-[24px] font-[500]'>Occupation Information</span>    </div>
                            {/* the three columns */}
                            <div className='flex flex-row justify-between'>
                                {/* first column */}
                                <div className='flex flex-row items-center p-[8px] bg-[#F9FAFB]'>
                                    <div><FaUserClock className='m-[12px]'/></div>
                                    <div>
                                        <span className='block text-[16px] text-[#004BBB] font-[500]'>Contract Type</span>
                                        <span className='block'>{profile.contractType}</span>
                                    </div>
                                </div>
                                {/* first column */}
                                <div className='flex flex-row items-center p-[8px] bg-[#F9FAFB]'>
                                    <div><AiOutlineTeam className='m-[12px]'/></div>
                                    <div>
                                        <span className='block text-[16px] text-[#004BBB] font-[500]'>Team</span>
                                        <span className='block'>{profile.department}</span>
                                    </div>
                                </div>
                                {/* end second column */}
                                <div className='flex flex-row items-center p-[8px] bg-[#F9FAFB]'>
                                    <div><VscLocation className='m-[12px]'/></div>
                                    <div>
                                        <span className='block text-[16px] text-[#004BBB] font-[500]'>Location</span>
                                        <span className='block'>{profile.workLocation}</span>
                                    </div>
                                </div>


                            </div>

                        </div>
                    </div>


                </div>
                {/* award section */}
                <div>
                    <div className="w-[518px] pl-[24px] pr-[27px] pt-[42px] bg-white border-2 border-mainBackground">
                        <div className="flex items-center mb-2">
                            <AiOutlineGift />
                            <h1 className=" px-2 font-bold text-2xl">Awards & Recognition</h1>
                        </div>
                        <hr />
                        <div className="row flex items-center p-2 border-b-2">
                            <div className="col-1 p-0 mr-[4px] flex flex-row"><img src={icon} alt="" /></div>
                            <div className="col-7 p-0">
                                <h1 className="font-bold my-2">Leadership Award</h1>
                                <p className="text-lightFont"> For setting the peace, keeping the vision and taking the initiated </p>
                            </div>
                            <div className="col-3">2023</div>
                        </div>

                        <div className="row flex items-center p-2 border-b-2">
                            <div className="col-1 p-0 mr-[4px] flex flex-row"><img src={icon} alt="" /></div>
                            <div className="col-7 p-0">
                                <h1 className="font-bold my-2">Leadership Award</h1>
                                <p className="text-lightFont"> For setting the peace, keeping the vision and taking the initiated </p>
                            </div>
                            <div className="col-3">2023</div>
                        </div>

                        <div className="row flex items-center p-2 border-b-2">
                            <div className="col-1 p-0 mr-[4px] flex flex-row"><img src={icon} alt="" /></div>
                            <div className="col-7 p-0">
                                <h1 className="font-bold my-2">Leadership Award</h1>
                                <p className="text-lightFont"> For setting the peace, keeping the vision and taking the initiated </p>
                            </div>
                            <div className="col-3">2023</div>
                        </div>
                    </div>
                </div>

            </div>
            {imageToggle && <FileUploadForm setFile={setFile} close={setToggle} toggle={imageToggle}/>}
            { profiletoggle && <PersonalInformation close={setProfileToggle} toggle={profiletoggle}
                                                    personal={personalDetails} setPersonal={setPersonalDetails}
                                                    next={nextOfKinDetails} setNext={setNextOfKinDetails}

                                                    profile={profile} setProfile={setProfile} submitProfile={submitProfile}
            />}
        </div>
    )
}




function FileUploadForm(props) {
    const [selectedFile, setSelectedFile] = useState(null);

    const handleFileChange = (event) => {
        const file = event.target.files[0];
        setSelectedFile(file);

        // Access file name and type



    };

    const handleSubmit = (event) => {
        event.preventDefault();
        // Use the selectedFile for further processing or upload
        if (selectedFile && isFileValid()) {
            console.log("here")
            const fileName = selectedFile.name;
            const fileExtension = fileName.split(".").pop().toUpperCase();


            props.setFile({"fileName":"Your CV", "fileType":fileExtension})



            props.close(!props.toggle)
            return true;

        }
        alert("please select a valid .docx or .pdf file");
        return;

    };


    const isFileValid = ()=>{
        if(selectedFile){
            const fileName = selectedFile.name;
            const fileExtension = fileName.split(".").pop().toLowerCase();

            const allowedExtensions = ["docx", "pdf"];

            return allowedExtensions.includes(fileExtension)
        }

        return false;
    }

    return (
        <div className='w-[100%] text-[#000000] h-[100%] absolute flex flex-row justify-center items-center top-[0]'>
            <div className='bg-white opacity-100 p-[25px] z-[2000] rounded-[10px]'>
                <form style={{color:"black"}} className='block opacity-100 ' onSubmit={handleSubmit}>
                    <input type="file" onChange={handleFileChange} />
                    <button type="submit">Upload</button> <span onClick={()=>{props.close(!props.toggle)}} className='cursor-pointer'>Cancel</span>
                </form> </div>
            <div onClick={()=>{props.close(!props.toggle)}} className='w-[100%] absolute top-[0] right-[0] left-[0] bottom-[0] bg-[#f2f4f7] opacity-30'></div>
        </div>
    );
}

const userProfileInitial = {dob:"", nationality:"", phoneNumber:"", maritalStatus:"", homeAddress:""};

function PersonalInformation(props){

    const nextofKinData= (props.profile.nextOfKin !==null)?{ name:props.profile.nextOfKin.name,

        occupation:props.profile.nextOfKin.occupation,

        relationship:props.profile.nextOfKin.relationship,

        phonenumber:props.profile.nextOfKin.phoneNumber,

        address:props.profile.nextOfKin.address}:{ name:"",

        occupation:"",

        relationship:"",

        phonenumber:"",

        address:""}

    const [personalDetails, setPersonalDetails] = useState(props.profile)
    const [nextOfKinDetails, setNextOfKinDetails] = useState(nextofKinData)


    const profileRef = useRef(null)
    const nextOfKinRef = useRef(null)

    const next = ()=>{
        profileRef.current.classList.remove("z-[200]")
        profileRef.current.classList.add("z-[100]")
        profileRef.current.classList.add("invisible")
        nextOfKinRef.current.classList.add("z-[200]")
        nextOfKinRef.current.classList.remove("z-[100]")
        console.log(personalDetails)


    }

    const previous = ()=>{
        profileRef.current.classList.add("z-[200]")
        profileRef.current.classList.remove("z-[100]")
        profileRef.current.classList.remove("invisible")
        nextOfKinRef.current.classList.remove("z-[200]")
        nextOfKinRef.current.classList.add("z-[100]")


    }

    const save = ()=>{
        // props.setNext(nextOfKinDetails)
        // props.setPersonal(personalDetails)
        props.close(!props.toggle)

        let nextOfkin = {
            name: nextOfKinDetails.name,

            occupation:nextOfKinDetails.occupation,

            relationship:nextOfKinDetails.relationship,

            phoneNumber:nextOfKinDetails.phonenumber,

            address:nextOfKinDetails.address
        }
        props.setProfile({ ...personalDetails, nextOfKin:nextOfkin,})

      //  props.submitProfile();


    }

    //  const changedate= (event)=>{
    //   const date = new Date(event.target.value);

    //   const day = date.getDate();
    //    const month = date.toLocaleString('default', { month: 'long' });
    //    const year = date.getFullYear();

    //    const dayWithSuffix = getDayWithSuffix(day);

    //    const formattedDate = `${dayWithSuffix}-${month}-${year}`;
    //    const displayDOB = `${dayWithSuffix}-${month}`;
    //    setPersonalDetails({...personalDetails, dob:event.target.value, displayDOB})
    //    // Set the formatted date as the value of the input field


    //  }

    // function getDayWithSuffix(day) {
    //   const suffixes = ['th', 'st', 'nd', 'rd'];
    //   const specialCases = [11, 12, 13]; // 11th, 12th, 13th

    //   const lastDigit = day % 10;
    //   const suffixIndex =
    //     specialCases.includes(day) || (lastDigit > 3 && lastDigit <= 9)
    //       ? 0
    //       : lastDigit;

    //   return day + suffixes[suffixIndex];
    // }




    return(
        // main container
        <div  className='absolute w-[100%] top-[0px]  bottom-[0px] '>
            {/* forms container */}
            <div style={{backgroundColor:"yellow"}} className='absolute top-[20%] left-[20%] z-[2000]'>
                {/* the form card */}
                <div ref={profileRef} className='p-[24px] bg-white w-[640px] rounded-[12px] absolute left-[0px] z-[200]'>
                    <div className='mb-[36px]'>
                        <div className='flex flex-row justify-between items-center mb-[4px] '> <p className='text-[24px] font-[700]'>  Personal information </p> <span onClick={()=>{props.close(!props.toggle)}} className='block cursor-pointer'>X</span> </div>
                        <p className='text-[14px] text-[#98A2B3]'>These are your personal details they are not visible on your public profile</p>
                    </div>
                    <div>
                        <form id="form-one">
                            {/* first row */}
                            <div>
                                <div>
                                    <label htmlFor='dob'>Date of Birth</label>
                                    <input type="date" id='dob' pattern='\d{4}-\d{2}-\d{2}' value={personalDetails.birthday} onChange={(event)=>{setPersonalDetails({...personalDetails, birthday:event.target.value})}} />
                                </div>
                                <div>
                                    <label htmlFor='status'>Marital Status</label>
                                    <select id='status'  value={personalDetails.maritalStatus}  onChange={(event)=>{setPersonalDetails({...personalDetails, maritalStatus:event.target.value})}}>
                                        <option value={""}> select </option>
                                        <option value={"Single"}> Single </option>
                                        <option value={"Married"}> Married </option>
                                        <option value={"Complicated"}> Complicated </option>

                                    </select>
                                </div>
                            </div>
                            {/* second row */}
                            <div>
                                <div>
                                    <label htmlFor='country'>Nationality</label>
                                    <select id='country' value={personalDetails.nationality} onChange={(event)=>{setPersonalDetails({...personalDetails, nationality:event.target.value})}}>
                                        <option value=''> Select a country </option>
                                        {countries.map((country, index)=>{return (<option key={index} value={country.name}>{country.name}</option>)})}

                                    </select>
                                </div>
                                <div>
                                    <label htmlFor='number'>Phone number</label>
                                    <input type="tel"  id='number' placeholder='+234 810 000 0000' value={personalDetails.phoneNo} onChange={(event)=>{setPersonalDetails({...personalDetails, phoneNo:event.target.value})}}/>
                                </div>
                            </div>

                            {/* additional */}
                            <div>
                                <div>
                                    <label htmlFor='nickname'>Nickname</label>
                                    <input type="text"  id='nickname' placeholder='nickname' value={personalDetails.nickName} onChange={(event)=>{setPersonalDetails({...personalDetails, nickName:event.target.value})}}/>
                                </div>
                                <div>
                                    <label htmlFor='report'>Report to</label>
                                    <input type="text"  id='report' placeholder='report to' value={personalDetails.reportTo} onChange={(event)=>{setPersonalDetails({...personalDetails, reportTo:event.target.value})}}/>
                                </div>
                            </div>
                            {/* location */}


                            {/* row three */}
                            <div id='last-row'>
                                <label htmlFor='address'>Home Address</label>
                                <input type="text" id="address" placeholder='Home no, street, state country' value={personalDetails.address} onChange={(event)=>{setPersonalDetails({...personalDetails, address:event.target.value})}}/>
                            </div>
                            <div id='additional'>
                                <label htmlFor='location'>workLocation</label>
                                <select id='location'  value={personalDetails.workLocation}  onChange={(event)=>{setPersonalDetails({...personalDetails, workLocation:event.target.value})}}>
                                    <option value={""}> select </option>
                                    <option value={"Remote"}> Remote </option>
                                    <option value={"Hybrid"}> Hybrid </option>
                                    <option value={"Onsite"}> Onsite </option>

                                </select>
                            </div>
                            <div id='buttons' className='flex flex-row justify-end mt-[36px]'>

                                <span className='block px-[16px] py-[12px] mr-[10px] text-[#004BBB] font-[600] cursor-pointer' onClick={()=>{props.close(!props.toggle)}}>Cancel</span>
                                <span className='block w-[97px] px-[16px] py-[12px] bg-[#004BBB] rounded-[6px] text-white font-[600] flex flex-row items-center justify-center gap-[8px] cursor-pointer' onClick={next} >Next <BsArrowRightShort/></span>
                            </div>
                        </form>

                    </div>

                </div>

                {/* next of kin section */}
                <div ref={nextOfKinRef} className='p-[24px] bg-white w-[640px] rounded-[12px] absolute left-[0px] z-[100]'>
                    <div className='mb-[36px]'>
                        <div className='flex flex-row justify-between items-center mb-[4px] '> <p className='text-[24px] font-[700]'>  Next of Kin Information </p> <span onClick={()=>{props.close(!props.toggle)}} className='block cursor-pointer'>X</span> </div>

                    </div>
                    <div>
                        <form id="form-one">
                            {/* first row */}
                            <div>
                                <div>
                                    <label htmlFor='name'>Name</label>
                                    <input type="text" id='name' value={nextOfKinDetails.name} onChange={(event)=>{setNextOfKinDetails({...nextOfKinDetails, name:event.target.value})}} />
                                </div>
                                <div>
                                    <label htmlFor='relationship'>Relationship</label>
                                    <input type={"text"} id="relationship" placeholder='Enter relationship' value={nextOfKinDetails.relationship} onChange={(event)=>{setNextOfKinDetails({...nextOfKinDetails, relationship:event.target.value})}}/>
                                </div>
                            </div>
                            {/* second row */}
                            <div>
                                <div>
                                    <label htmlFor='occupation'>Occupation</label>
                                    <input type={"text"}  id="occupation" placeholder='Enter occupation' value={nextOfKinDetails.occupation} onChange={(event)=>{setNextOfKinDetails({...nextOfKinDetails, occupation:event.target.value})}}/>
                                </div>
                                <div>
                                    <label htmlFor='number'>Phone number</label>
                                    <input type="tel"  id='number' placeholder='+234 810 000 0000' value={nextOfKinDetails.phonenumber} onChange={(event)=>{setNextOfKinDetails({...nextOfKinDetails, phonenumber:event.target.value})}}/>
                                </div>
                            </div>
                            {/* row three */}
                            <div id='last-row'>
                                <label htmlFor='address'>Address</label>
                                <input type="text" id="address" placeholder='Enter next of kin address' value={nextOfKinDetails.address} onChange={(event)=>{setNextOfKinDetails({...nextOfKinDetails, address:event.target.value})}}/>
                            </div>
                            <div id='buttons' className='flex flex-row justify-end mt-[36px]'>

                                <span className='block px-[16px] py-[12px] mr-[10px] text-[#004BBB] font-[600] cursor-pointer flex flex-row items-center justify-center gap-[8px]'  onClick={previous}> <BsArrowLeftShort/> previous</span>
                                <span className='block w-[97px] px-[16px] py-[12px] bg-[#004BBB] rounded-[6px] text-white font-[600] flex flex-row items-center justify-center gap-[8px] cursor-pointer' onClick={save} >Save </span>
                            </div>
                        </form>

                    </div>

                </div>

            </div>
            <div onClick={()=>{props.close(!props.toggle)}} className='w-[100%] absolute top-[0] right-[0] left-[0] bottom-[0] bg-[#f2f4f7] opacity-30'></div>
        </div>
    )
}

const countries = [
    { name: 'Afghanistan' },
    { name: 'Albania' },
    { name: 'Algeria' },
    { name: 'Andorra' },
    { name: 'Angola' },
    { name: 'Antigua and Barbuda' },
    { name: 'Argentina' },
    { name: 'Armenia' },
    { name: 'Australia' },
    { name: 'Austria' },
    { name: 'Azerbaijan' },
    { name: 'Bahamas' },
    { name: 'Bahrain' },
    { name: 'Bangladesh' },
    { name: 'Barbados' },
    { name: 'Belarus' },
    { name: 'Belgium' },
    { name: 'Belize' },
    { name: 'Benin' },
    { name: 'Bhutan' },
    { name: 'Bolivia' },
    { name: 'Bosnia and Herzegovina' },
    { name: 'Botswana' },
    { name: 'Brazil' },
    { name: 'Brunei' },
    { name: 'Bulgaria' },
    { name: 'Burkina Faso' },
    { name: 'Burundi' },
    { name: 'Cabo Verde' },
    { name: 'Cambodia' },
    { name: 'Cameroon' },
    { name: 'Canada' },
    { name: 'Central African Republic' },
    { name: 'Chad' },
    { name: 'Chile' },
    { name: 'China' },
    { name: 'Colombia' },
    { name: 'Comoros' },
    { name: 'Congo, Democratic Republic of the' },
    { name: 'Congo, Republic of the' },
    { name: 'Costa Rica' },
    { name: 'Croatia' },
    { name: 'Cuba' },
    { name: 'Cyprus' },
    { name: 'Czech Republic' },
    { name: 'Denmark' },
    { name: 'Djibouti' },
    { name: 'Dominica' },
    { name: 'Dominican Republic' },
    { name: 'East Timor' },
    { name: 'Ecuador' },
    { name: 'Egypt' },
    { name: 'El Salvador' },
    { name: 'Equatorial Guinea' },
    { name: 'Eritrea' },
    { name: 'Estonia' },
    { name: 'Eswatini' },
    { name: 'Ethiopia' },
    { name: 'Fiji' },
    { name: 'Finland' },
    { name: 'France' },
    { name: 'Gabon' },
    { name: 'Gambia' },
    { name: 'Georgia' },
    { name: 'Germany' },
    { name: 'Ghana' },
    { name: 'Greece' },
    { name: 'Grenada' },
    { name: 'Guatemala' },
    { name: 'Guinea' },
    { name: 'Guinea-Bissau' },
    { name: 'Guyana' },
    { name: 'Haiti' },
    { name: 'Honduras' },
    { name: 'Hungary' },
    { name: 'Iceland' },
    { name: 'India' },
    { name: 'Indonesia' },
    { name: 'Iran' },
    { name: 'Iraq' },
    { name: 'Ireland' },
    { name: 'Israel' },
    { name: 'Italy' },
    { name: 'Jamaica' },
    { name: 'Japan' },
    { name: 'Jordan' },
    { name: 'Kazakhstan' },
    { name: 'Kenya' },
    { name: 'Kiribati' },
    { name: 'Korea, North' },
    { name: 'Korea, South' },
    { name: 'Kosovo' },
    { name: 'Kuwait' },
    { name: 'Kyrgyzstan' },
    { name: 'Laos' },
    { name: 'Latvia' },
    { name: 'Lebanon' },
    { name: 'Lesotho' },
    { name: 'Liberia' },
    { name: 'Libya' },
    { name: 'Liechtenstein' },
    { name: 'Lithuania' },
    { name: 'Luxembourg' },
    { name: 'Madagascar' },
    { name: 'Malawi' },
    { name: 'Malaysia' },
    { name: 'Maldives' },
    { name: 'Mali' },
    { name: 'Malta' },
    { name: 'Marshall Islands' },
    { name: 'Mauritania' },
    { name: 'Mauritius' },
    { name: 'Mexico' },
    { name: 'Micronesia' },
    { name: 'Moldova' },
    { name: 'Monaco' },
    { name: 'Mongolia' },
    { name: 'Montenegro' },
    { name: 'Morocco' },
    { name: 'Mozambique' },
    { name: 'Myanmar' },
    { name: 'Namibia' },
    { name: 'Nauru' },
    { name: 'Nepal' },
    { name: 'Netherlands' },
    { name: 'New Zealand' },
    { name: 'Nicaragua' },
    { name: 'Niger' },
    { name: 'Nigeria' },
    { name: 'North Macedonia' },
    { name: 'Norway' },
    { name: 'Oman' },
    { name: 'Pakistan' },
    { name: 'Palau' },
    { name: 'Palestinian Territories' },
    { name: 'Panama' },
    { name: 'Papua New Guinea' },
    { name: 'Paraguay' },
    { name: 'Peru' },
    { name: 'Philippines' },
    { name: 'Poland' },
    { name: 'Portugal' },
    { name: 'Qatar' },
    { name: 'Romania' },
    { name: 'Russia' },
    { name: 'Rwanda' },
    { name: 'Saint Kitts and Nevis' },
    { name: 'Saint Lucia' },
    { name: 'Saint Vincent and the Grenadines' },
    { name: 'Samoa' },
    { name: 'San Marino' },
    { name: 'Sao Tome and Principe' },
    { name: 'Saudi Arabia' },
    { name: 'Senegal' },
    { name: 'Serbia' },
    { name: 'Seychelles' },
    { name: 'Sierra leone'},
    { name: 'Singapore' },
    { name: 'Slovakia' },
    { name: 'Slovenia' },
    { name: 'Solomon Islands' },
    { name: 'Somalia' },
    { name: 'South Africa' },
    { name: 'South Sudan' },
    { name: 'Spain' },
    { name: 'Sri Lanka' },
    { name: 'Sudan' },
    { name: 'Suriname' },
    { name: 'Sweden' },
    { name: 'Switzerland' },
    { name: 'Syria' },
    { name: 'Taiwan' },
    { name: 'Tajikistan' },
    { name: 'Tanzania' },
    { name: 'Thailand' },
    { name: 'Togo' },
    { name: 'Tonga' },
    { name: 'Trinidad and Tobago' },
    { name: 'Tunisia' },
    { name: 'Turkey' },
    { name: 'Turkmenistan' },
    { name: 'Tuvalu' },
    { name: 'Uganda' },
    { name: 'Ukraine' },
    { name: 'United Arab Emirates' },
    { name: 'United Kingdom' },
    { name: 'United States' },
    { name: 'Uruguay' },
    { name: 'Uzbekistan' },
    { name: 'Vanuatu' },
    { name: 'Vatican City' },
    { name: 'Venezuela' },
    { name: 'Vietnam' },
    { name: 'Yemen' },
    { name: 'Zambia' },
    { name: 'Zimbabwe' }

]


const displayDateOfBirth= (value)=>{
    const date = new Date(value);

    const day = date.getDate();
    const month = date.toLocaleString('default', { month: 'long' });
    const year = date.getFullYear();

    const dayWithSuffix = getDayWithSuffix(day);

    const formattedDate = `${dayWithSuffix}-${month}-${year}`;
    const displayDOB = `${dayWithSuffix}-${month}`;

    return displayDOB;


}

function getDayWithSuffix(day) {
    const suffixes = ['th', 'st', 'nd', 'rd'];
    const specialCases = [11, 12, 13]; // 11th, 12th, 13th

    const lastDigit = day % 10;
    const suffixIndex =
        specialCases.includes(day) || (lastDigit > 3 && lastDigit <= 9)
            ? 0
            : lastDigit;

    return day + suffixes[suffixIndex];
}




export default StaffProfile


