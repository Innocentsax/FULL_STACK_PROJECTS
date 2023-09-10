import { useState } from "react";
import SuccessModal from "./SuccessModal";
import PropTypes from "prop-types";

const JobCard = ({ jobPostingList }) => {
    const [isOpen, setIsOpen] = useState(false);

    return (
        <div className="grid grid-cols-3 gap-20 ">
            {jobPostingList.map((job, index) => (
                <div className="" key={index}>
                    <div className="flex flex-col items-start p-4 gap-6 w-80 h-65 bg-white">
                        <h1 className="font-bold font-inter text-blue-500 text-xs">{job.title}</h1>
                        <div className="py-1">
                            <span className="text-slate-400 text-sm">Job Details</span>
                            <p className="text-sm">{job.description}</p>
                        </div>
                        <div className="mb-2">
                            <span className="text-slate-400 text-sm">Time/Date Posted</span>
                            <p className="text-sm">{job.createdOn}</p>
                        </div>
                        <div>
                            <button
                                className="flex flex-row justify-center items-center p-3 gap-2 w-76 h-4 bg-blue-500 hover:bg-blue-700 text-white font-bold rounded"
                                onClick={() => setIsOpen(true)}
                            >
                                Apply
                            </button>
                            {isOpen && <SuccessModal closeModal={() => setIsOpen(false)} />}
                        </div>
                    </div>

                </div>
            ))}
        </div>
    );
};


JobCard.propTypes = {
    jobPostingList: PropTypes.array
};


export default JobCard;