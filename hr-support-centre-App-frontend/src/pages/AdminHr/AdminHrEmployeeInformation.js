import React from "react";

import { BiFilterAlt } from "react-icons/bi";
import { AiOutlineEdit, AiOutlineDelete } from "react-icons/ai";
import { IoArrowUpOutline } from "react-icons/io5";
import axios from "axios";
import ReactPaginate from "react-paginate";
import { useState, useEffect } from "react";
import { useStateContext } from "../../context/ContextProvider";
import AddEmployeeModal from "../../components/adminHrComponent/modal/AddEmployeeModal";

const StaffEmployees = () => {
    const [users, setUsers] = useState({});
    const [pageNo, setPageNo] = useState(0);
    const [showModal, setShowModal] = useState(false);

    const context = useStateContext();
    const token = "Bearer "+context.token;
    const headers =  {
        'Content-Type': 'application/json',
        'Authorization': token
    };

    const fetchUsers = async () => {
        try {
            const response = await axios.get(
                "http://localhost:8080/api/v1/hr/view-all-staff?pageNo=" + pageNo , { headers }
            );
            setUsers(response.data);
            console.log(response.data);
        } catch (error) {
            console.error("Error fetching users", error);
        }
    };
    useEffect(() => {
        fetchUsers();
    }, [pageNo, fetchUsers]);

    // const handleCreateProfile = (employeeData) => {
    //     // Handle the creation of the employee profile
    //     console.log("Creating employee profile:", employeeData);
    //     // Close the modal
    //     handleCloseModal();
    // };

    const handleOpenModal = () => {
        setShowModal(true);
    };
    const handleCloseModal = () => {
        setShowModal(false);
    };
    const changePage = ({selected}) => {
        setPageNo(selected);
    };

    const userss = users.content ? users.content : [];
    const paginatioNo = users.pageNo+1

    return (
        <div className="relative p-4">
            <div className="flex justify-between mb-16">
                <h1 className="text-4xl font-bold">Employee Information</h1>
                <div>
                    <button className="flex items-center bg-white rounded-md justify-center relative h-12 w-28 min-w-250">
                        <BiFilterAlt className="h-full  flex items-center justify-center left-1" />
                        <select className="border-none  appearance-none p-2 ">
                            <option>Filter by</option>
                            <option>Newest</option>
                            <option>Oldest</option>
                            <option>Department</option>
                        </select>
                    </button>
                </div>
                <button className="mr-4 px-3 border-2 rounded h-11 w-40 bg-blue-800 text-white border-blue-800 text-bold py-3 flex items-center justify-center" onClick={handleOpenModal}>
                    Add Employee
                </button>
                {showModal &&
                    <AddEmployeeModal
                        onClose={handleCloseModal}
                        // onCreateProfile={handleCreateProfile}
                    />}
            </div>


            <div>
                <table className="table-fixed border-collapse border-spacing-2 w-full">
                    <thead className="">
                    <tr>
                        <th>
                            <div className="flex items-center px-4">
                                <p className="m-2">Employee</p>
                                <IoArrowUpOutline style={{ width: "20px", height: "60px" }} />
                            </div>
                        </th>

                        <th>
                            <div className="flex items-center px-4">
                                <p className="m-2">Department</p>
                                <IoArrowUpOutline style={{ width: "20px", height: "60px" }} />
                            </div>
                        </th>

                        <th className="px-4">
                            <div className="flex items-center px-4">
                                <p className="m-2">Status</p>
                                <IoArrowUpOutline style={{ width: "20px", height: "60px" }} />
                            </div>
                        </th>

                        <th className="px-4">
                            <div className="flex items-center px-4">
                                <p className="m-2">Manage</p>
                            </div>
                        </th>
                    </tr>
                    </thead>
                    {userss.map((user, index) => (


                        <tbody className="bg-white">
                        <tr className="border-4 border-mainBackground">
                            <td className="py-2 px-4">
                                <div>
                                    <h1>{user.fullName}</h1>
                                    <p className="text-lightFont text-sm">{user.position}</p>
                                </div>
                            </td>

                            <td className="px-4">
                                <div>
                                    <h1>{user.department}</h1>
                                    <p className="text-lightFont text-sm">{user.contractType}</p>
                                </div>
                            </td>

                            <td className="px-4">{user.status}</td>

                            <td className="px-4">
                                <div className="flex items-center">
                                    <span className="text-gray-500 mr-2"><AiOutlineEdit /></span>
                                    <span>|</span>
                                    <span className="text-red-600 ml-2"><AiOutlineDelete /></span>
                                </div>
                            </td>

                        </tr>
                        </tbody>
                    ))}

                </table>
            </div>

            <div className="bottom-0 w-full p-4 flex items-center justify-center">

                <ReactPaginate
                    previousLabel={"Previous"}
                    nextLabel={"Next"}
                    pageCount={paginatioNo}
                    onPageChange={changePage}
                    containerClassName={"flex items-center justify-center space-x-4"}
                    previousLinkClassName={
                        "bg-white border border-gray-300 shadow-xs rounded px-4 py-2 hover:bg-blue-500 hover:text-white"
                    }
                    nextLinkClassName={
                        "bg-white border border-gray-300 shadow-xs rounded px-4 py-2 hover:bg-blue-500 hover:text-white"
                    }
                    disabledClassName={"paginationDisabled"}
                    activeClassName={"paginationActive bg-blue-500"}
                />

            </div>


        </div>
    );
};

export default StaffEmployees;


