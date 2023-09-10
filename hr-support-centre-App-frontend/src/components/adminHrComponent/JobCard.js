import { useState } from "react";
import ButtonForModal from "./ButtonForModal";
import EditJobOpeningsModal from "./modal/EditJobOpeningsModal";

const JobCard = ({id, title, department, closingDate, description, requirements}) => {
    const [status, setStatus] = useState("Deactivate");
    const [buttonAttributes, setButtonAttributes] = useState("bg-blue-800 w-full h-10 rounded text-white");
    const [editJobOpenings, setEditJobOpenings] = useState(false);
    const [jobDetails, setJobDetails] = useState({id: id, title: title, department: department, closingDate: closingDate, description: description, requirements: requirements});


    const handleClick = () => {
        setStatus("Deactivated");
        setButtonAttributes("bg-slate-500 w-full h-10 rounded text-white");
    };

    const openEditJobOpeningsModal = () => {
        console.log("opening");
        setEditJobOpenings(true);
    };

    const closeEditJobOpeningsModal = () => {
        console.log("closing");
        setEditJobOpenings(false);
    };

    return (
        <div className="col-3 bg-white h-80 w-80 p-2 mt-4 ml-4">
            {/* Job card header */}
            <ButtonForModal
                // className="text-blue-500 text-xs"
                handleButtonForModalClick={openEditJobOpeningsModal}
                buttonForModalTitle={jobDetails.title}
            />
            {/* this is a hyperlink */}
            {editJobOpenings && <EditJobOpeningsModal closeEditJobOpeningsModal = {closeEditJobOpeningsModal} jobDetails = {jobDetails} setJobDetails = {setJobDetails}/>}
            {/* Job Details */}
            <div className="py-2">
                <span className="text-slate-400 text-sm">Job Details</span>
                <p className="text-sm">
                    {jobDetails.description}
                </p>
            </div>
            {/* Date and Time */}
            <div className="mb-6">
                <span className="text-slate-400 text-sm">Closing Date</span>
                <p className="text-sm">{jobDetails.closingDate}</p>
            </div>
            {/* Number of applications */}
            <div className="h-12 bg-slate-200 mb-6 rounded px-2 py-1">
                <span className="text-slate-500 text-xs">2 applications</span>
                <p className="text-blue-500 text-xs">view</p> {/*This is a hyperlink*/}
            </div>
            {/* Activate/Deactivate button */}
            <div>
                <button className={buttonAttributes} onClick={handleClick}>
                    {status}
                </button>
            </div>
        </div>
    );
};

export default JobCard;