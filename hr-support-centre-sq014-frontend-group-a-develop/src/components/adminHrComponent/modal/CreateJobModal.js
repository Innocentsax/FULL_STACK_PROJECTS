import axios from "axios";
import React, {useState} from "react";
import { useStateContext } from "../../../context/ContextProvider";

const CreateJobModal = ( {closeCreateJobOpeningsModal , jobId, title, departmentName, closingDate, description, requirements} ) => {
    const [jobDetails, setJobDetails] = useState({id: jobId, title: title, departmentName: departmentName, closingDate: closingDate, description: description, requirements: requirements});
    // const [title, setTitle] = useState("");
    // const [departmentName, setDepartmentName] = useState("");
    // const [closingDate, setClosingDate] = useState("");
    // const [description, setDescription] = useState("");
    // const [requirements, setRequirements] = useState("");

    const context = useStateContext();
    const token = "Bearer "+context.token;

    const handleCreateJobOpeningsModalClick = () => {
        closeCreateJobOpeningsModal();
    }

    const closeJobOpeningsModalBgClick = (event) => {
        if (event.target.id === "create-job-modal-bg") {
            closeCreateJobOpeningsModal();
        }
    }
    const headers =  {
        'Content-Type': 'application/json',
        'Authorization': token
    };

    const handleCreateJobSubmit = async () => {
        try {
            const response = await axios.post("http://localhost:8080/api/v1/hr/create-job", jobDetails,  {
                headers
            })
            console.log(jobDetails);
            console.log(response.data);
            closeCreateJobOpeningsModal();
        } catch (error) {
            console.log(error);
        }
    }

    return (
        <div >
            {/*Modal is now working*/}
            <div id = "create-job-modal-bg" className = "absolute  bg-zinc-700/50 top-0 left-0 w-screen h-screen flex flex-col justify-center items-center" onClick = {closeJobOpeningsModalBgClick}>
                <div className = "relative bg-white p-3 h-540 w-640 place-content-center rounded-xl max-w-screen-md">
                    <a className = "absolute right-5 h-3.5 w-3.5 text-slate-400 hover:cursor-pointer" onClick = {handleCreateJobOpeningsModalClick}>X</a>
                    <div>
                        <h1 className = "text-2xl">Create Job openings</h1>
                    </div>
                    <div className = "flex ">
                        <div>
                            <label className = "text-sm">Job Title</label>
                            <input 
                            className = "h-10 p-2 text-slate-400 border-slate-300 border-2 rounded w-full" 
                            type = "text" 
                            placeholder="job title" 
                            value = {jobDetails.title} 
                            onChange = {(event) => {setJobDetails({...jobDetails, title: event.target.value})}}                           
                            />
                        </div>
                        <div className="px-4">
                            <label className = "text-sm">Department</label>
                            <input 
                            className = "h-10 p-2 text-slate-400 border-slate-300 border-2 rounded w-full" 
                            type = "text" 
                            placeholder="department" 
                            value = {jobDetails.departmentName} 
                            onChange = {(event) => {setJobDetails({...jobDetails, departmentName: event.target.value})}}

                            />
                        </div>
                        <div>
                            <label className = "text-sm">Closing date</label>
                            <input 
                            className = "h-10 p-2 border-slate-300 border-2 rounded w-full" 
                            type = "text" 
                            placeholder="mm/dd/yyyy" 
                            value = {jobDetails.closingDate} 
                            onChange = {(event) => {setJobDetails({...jobDetails, closingDate: event.target.value})}}/>
                        </div>
                    </div>
                    <div className="flex flex-col mt-6">
                        <label className = "text-sm">Job Description</label>
                        <textarea 
                        className = "text-slate-400 border-slate-300 border-2 rounded" 
                        rows="5" 
                        placeholder = "Lorem ipsum dolor sit amet consectetur. Pellentesque condimentum at " 
                        value = {jobDetails.description} 
                        onChange = {(event) => {setJobDetails({...jobDetails, description: event.target.value})}}>

                        </textarea>
                    </div>
                    <div className = "flex flex-col mt-6">
                        <label className = "text-sm">Requirement/s</label>
                        <textarea 
                        className = "text-slate-400 border-slate-300 border-2 rounded" 
                        rows="5" 
                        placeholder = "Lorem ipsum dolor sit amet consectetur. Pellentesque condimentum at " 
                        value = {jobDetails.requirements} 
                        onChange = {(event) => {setJobDetails({...jobDetails, requirements: event.target.value})}}>
                            
                        </textarea>
                    </div>
                    <div className = "flex justify-end mt-6">
                        <button className = "text-base text-blue-500 mr-6" onClick = {handleCreateJobOpeningsModalClick} >Cancel</button>
                        <button className = "bg-blue-800 text-white border-blue-800 w-36 h-11 rounded-md py-3 px-4 text-base" onClick = {handleCreateJobSubmit}>Post</button>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default CreateJobModal;