import React, { useEffect } from 'react';
import { HiOutlineInboxArrowDown } from "react-icons/hi2";
import { BiFilterAlt } from "react-icons/bi";
import JobCard from '../../components/adminHrComponent/JobCard';
import { useState } from 'react';
import axios from 'axios';
import ReactPaginate from 'react-paginate';
import CreateJobModal from "../../components/adminHrComponent/modal/CreateJobModal";
import { useStateContext } from "../../context/ContextProvider";

const AdminHrJobBoard = () => {
    const [jobPosting, setJobPosting] = useState({});
    const [pageNumber, setPageNumber] = useState(0);
    const [createJobOpenings, setCreateJobOpenings] = useState(false);
    const context = useStateContext();
    const openCreateJobOpeningsModal = () => {
        console.log("opening");
        setCreateJobOpenings(true);
    };

    const closeCreateJobOpeningsModal = () => {
        console.log("closing");
        setCreateJobOpenings(false);
    };

    const getJobPosting = async () => {
        const token = "Bearer "+context.token;
        const headers = {
            "Content-Type": "application/json",
            "Authorization": token
        };
        axios.get("http://localhost:8080/api/v1/job/filter?page="+pageNumber , { headers })
            .then(
                response => {
                    setJobPosting(response.data.content[0]);
        console.log(response.data.content.jobResponseDtos)
                }  
            )
            .catch(error => {
                console.log(error);
            })
    }

    useEffect( () => {
            getJobPosting();
        }, [getJobPosting]


    )

    const changePage = ({selected}) => {
        setPageNumber(selected);
    }

    const jobs = jobPosting.jobResponseDtos ? jobPosting.jobResponseDtos : [];
    const paginationNumber = jobPosting.totalPages + 1;

    return (
        <div className='p-4'>
            <div className="">
                {/* Header */}
                <div className="flex justify-between mb-8 mr-24">
                    <div>
                        <h1 className="font-bold text-4xl">Job Posting</h1>
                    </div>

                    <div className="flex items-center">
                        <button className="mr-4 px-2 bg-white border border-gray-300 rounded h-11">
                            <div className = "flex justify-center items-center">
                                <div className="mr-2">
                                    <BiFilterAlt className = " text-blue-500" />

                                </div>
                                {/* <label for="filter">Filter By</label> */}

                                <select className = "appearance-none my-2.5" id="filter">
                                    <option value="newest bg-white">Filter By</option>
                                    <option value="newest">Newest</option>
                                    <option value="past-week">Past Week</option>
                                    <option value="oldest">Oldest</option>
                                    <option value="department">Department</option>
                                </select>

                            </div>
                        </button>
                        <button className="flex items-center space-x-4 mr-4 px-2 bg-white border border-blue-800 rounded text-blue-500 h-10 w-48 text-sm py-2.5">
                            <div>
                                <HiOutlineInboxArrowDown className="w-4 h-4" />
                            </div>
                            <p>View All Applications</p>
                        </button>
                        <button className="mr-4 px-4 border-2 rounded h-11 h-12 w-32 bg-blue-800 text-white border-blue-800 text-xs py-3" onClick={openCreateJobOpeningsModal}>
                            Create New
                        </button>
                        {createJobOpenings && <CreateJobModal closeCreateJobOpeningsModal = {closeCreateJobOpeningsModal}/>}
                    </div>
                </div>

                {/* Job card */}

                <div className='row'>
                    {jobs.map((job, index) => 
                    <JobCard
                        title = {job.title}
                        department = {job.department}
                        closingDate = {job.closingDate}
                        description = {job.description}
                        requirements = {job.requirements}  

                        />)}
                </div>


            </div>

            <div className="bottom-0 w-full p-4 flex items-center justify-center">

                <ReactPaginate
                    previousLabel={"Previous"}
                    nextLabel={"Next"}
                    pageCount={paginationNumber}
                    onPageChange={changePage}
                    containerClassName={"flex items-center justify-center space-x-4 mt-10"} // Added mt-10 for top margin
                    previousLinkClassName={"bg-white border border-gray-300 shadow-xs rounded px-3 py-1 hover:bg-blue-500 hover:text-white"}
                    nextLinkClassName={"bg-white border border-gray-300 shadow-xs rounded px-3 py-1 hover:bg-blue-500 hover:text-white"}
                    disabledClassName={"paginationDisabled"}
                    activeClassName={"paginationActive bg-blue-500 rounded"} // Added rounded background
                    pageLinkClassName={"text-sm"} // Added text-sm for smaller font size
                />
            </div>



        </div>
    )
}

export default AdminHrJobBoard