import React from 'react'
import JobCard from '../../components/staffComponent/JobCard';
import { BiFilterAlt } from "react-icons/bi";
import ReactPaginate from "react-paginate";
import {useState, useEffect} from 'react';
import axios from 'axios';
import JobFilterModal from '../../components/staffComponent/JobFilterModal';
import { useStateContext } from "../../context/ContextProvider";

const StaffJobBoard = () => {
    const [jobPosting, setJobPosting] = useState({});
    const [pageNumber,  setPageNumber] = useState(0);
    const [filter, setFilter] = useState("newest");
    const [showFilterModal, setShowModal] = useState(false)
    const context = useStateContext();

    useEffect(() =>{
            const getJobPosting = async () =>{


                const token = "Bearer "+context.token;

                const headers = {
                    'Content-Type': 'application/json',
                    'Authorization': token
                };

                axios.get('http://localhost:8080/api/v1/job/filter?page='+pageNumber+'&filter='+filter, { headers })
                    .then(response => {
                        setJobPosting(response.data.content[0])
                        console.log(response.data)
                    })
                    .catch(error => {
                        console.log(error);
                    });
            };

            getJobPosting();

        }, [pageNumber, filter]
    )

    const openFilterModal = () => {
        setShowModal(true);
    };

    const closeFilterModal = () => {
        setShowModal(false);
    }

    const changePage = ({selected}) => {
        setPageNumber(selected)
    }
    const changeFilter = (selectedFilter)=>{
        setFilter(selectedFilter)
        closeFilterModal();
    }

    const jobPostingList = jobPosting.jobResponseDtos ? jobPosting.jobResponseDtos:[];
    const paginationNumber = jobPosting.totalPages + 1;



    return (
        <div className='p-4 relative'>
            {/* Header */}
            <div className="flex justify-between py-6">
                <div>
                    <h1 className="font-bold text-4xl">Job Posting</h1>
                </div>
                <div className="relative">
                    <button
                        className="mr-4 px-2 bg-white border border-gray-300 rounded h-11 w-28 h-10"
                        onClick={openFilterModal}
                    >
                        <div className="flex items-center justify-between h-4">
                            <BiFilterAlt className="text-blue-500" />
                            <p className="my-2.5">Filter By</p>
                        </div>
                    </button>

                    {showFilterModal && (
                        <div className="absolute top-12 left-0 z-10 bg-white border border-gray-300 rounded p-4">
                            <JobFilterModal
                                selectedFilter={filter}
                                onFilterChange={changeFilter}
                                closeFilterModal={closeFilterModal}
                            />
                        </div>
                    )}
                </div>
            </div>
            {/* Job card */}
            <div className="mb-1 ">
                <div> <JobCard jobPostingList ={jobPostingList}/> </div>
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

export default StaffJobBoard
