import ButtonForModal from "../ButtonForModal";
import axios from "axios";

const EditJobOpeningsModal = ( {closeEditJobOpeningsModal, jobDetails, setJobDetails} ) => {
    const handleButtonForModalClick = () => {
        closeEditJobOpeningsModal();
    }

    const closeModalBgClick = (event) => {
        if (event.target.id === "modal-bg") {
            closeEditJobOpeningsModal();
        }
    }

    return (
        <div id = "modal-bg" className = "absolute  bg-zinc-700/50 top-0 left-0 w-screen h-screen flex flex-col justify-center items-center" onClick = {closeModalBgClick}>
            <div className = "relative bg-white p-3 h-540 w-640 place-content-center rounded-xl max-w-screen-md">
                <a className = "absolute right-5 h-3.5 w-3.5 text-slate-400 hover:cursor-pointer" onClick = {handleButtonForModalClick}>X</a>
                <div>
                    <h1 className = "text-2xl">Edit Job openings</h1>
                </div>
                <div className = "flex ">
                    <div>
                        <label className = "text-sm">Job Title</label>
                        <input className = "h-10 p-2 text-slate-400 border-slate-300 border-2 rounded w-full" type = "text" placeholder="job title" value = {jobDetails.jobTitle} onChange = {(event) => {setJobDetails({...jobDetails, jobTitle: event.target.value})}}/>
                    </div>
                    <div className="px-4">
                        <label className = "text-sm">Department</label>
                        <input className = "h-10 p-2 text-slate-400 border-slate-300 border-2 rounded w-full" type = "text" placeholder="department" value = {jobDetails.department} onChange = {(event) => {setJobDetails({...jobDetails, department: event.target.value})}}/>
                    </div>
                    <div>
                        <label className = "text-sm">Closing date</label>
                        <input className = "h-10 p-2 border-slate-300 border-2 rounded w-full" type = "text" placeholder="closing date" value = {jobDetails.closingDate} onChange = {(event) => {setJobDetails({...jobDetails, closingDate: event.target.value})}}/>
                    </div>
                </div>
                <div className="flex flex-col mt-6">
                    <label className = "text-sm">Job Description</label>
                    <textarea className = "text-slate-400 border-slate-300 border-2 rounded" rows="5" placeholder = "Lorem ipsum dolor sit amet consectetur. Pellentesque condimentum at " value = {jobDetails.jobDescription} onChange = {(event) => {setJobDetails({...jobDetails, jobDescription: event.target.value})}}></textarea>
                </div>
                <div className = "flex flex-col mt-6">
                    <label className = "text-sm">Requirement/s</label>
                    <textarea className = "text-slate-400 border-slate-300 border-2 rounded" rows="5" placeholder = "Lorem ipsum dolor sit amet consectetur. Pellentesque condimentum at " value = {jobDetails.requirements} onChange = {(event) => {setJobDetails({...jobDetails, requirements: event.target.value})}}></textarea>
                </div>
                <div className = "flex justify-end mt-6">
                    <button className = "text-base text-blue-500 mr-6" onClick = {handleButtonForModalClick} >Cancel</button>
                    <button className = "bg-blue-800 text-white border-blue-800 w-36 h-11 rounded-md py-3 px-4 text-base">Save Changes</button>
                </div>
            </div>

        </div>
    )
}

export default EditJobOpeningsModal;