import React, { useState } from "react";
import { useStateContext } from "../../../context/ContextProvider";
import axios from "axios";

const AddEmployeeModal = ({ onClose, onCreateProfile }) => {
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [startDate, setStartDate] = useState("");
    const [department, setDepartment] = useState("");
    const [position, setPosition] = useState("");
    const [contractType, setContractType] = useState("");
    const [reportsTo, setReportsTo] = useState("");
    const [email, setEmail] = useState("");
    const [location, setLocation] = useState("");
    // Add state variables for other input fields
    const context = useStateContext();
    const token = "Bearer "+context.token;

    const headers =  {
        'Content-Type': 'application/json',
        'Authorization': token
    };

    const handleCreateProfile = async () => {
        // Perform any necessary validation or processing
        // before creating the employee profile
        const employeeData = {
            firstName,
            lastName,
            startDate,
            department,
            position,
            contractType,
            reportsTo,
            email,
            location
            // Add other fields to the employeeData object
        };
        console.log(employeeData) 
        // Call the create profile function with the employee data
        try {
            const response = await axios.post("http://localhost:8080/api/v1/hr/register", employeeData,  {
                headers
            })
            console.log(employeeData);
            console.log(response);
        } catch (error) {
            console.log(error);
        }
    };

    return (
        <div className="fixed top-0 left-0 flex items-center justify-center w-screen h-screen bg-gray-900 bg-opacity-75">
            <div className="bg-white rounded-lg p-8">
                <h2 className="text-2xl font-bold mb-1">New Employee Profile</h2>
                <p className="text-gray-600 text-xs mb-6">These details are not editable by the employee</p>
                {/* Input fields for employee profile */}
                <div className="grid grid-cols-3 gap-4 mb-4">
                <div className="mb-4">
                    <label className="block text-sm font-medium text-gray-700 mb-1">First Name:</label>
                    <input
                        type="text"
                        value={firstName}
                        placeholder={"Enter first name"}
                        onChange={(e) => setFirstName(e.target.value)}
                        className="border border-gray-300 rounded-md px-3 py-2 w-full"
                    />
                </div>
                <div className="mb-4">
                    <label className="block text-sm font-medium text-gray-700 mb-1">Last Name:</label>
                    <input
                        type="text"
                        value={lastName}
                        placeholder={"Enter last name"}
                        onChange={(e) => setLastName(e.target.value)}
                        className="border border-gray-300 rounded-md px-3 py-2 w-full"
                    />
                </div>
                <div className="mb-4">
                    <label className="block text-sm font-medium text-gray-700 mb-1">Start Date</label>
                    <input
                        type="text"
                        value={startDate}
                        placeholder={"mm/dd/yyyy"}
                        onChange={(e) => setStartDate(e.target.value)}
                        className="border border-gray-300 rounded-md px-3 py-2 w-full"
                    />
                </div>
                <div className="mb-4">
                    <label className="block text-sm font-medium text-gray-700 mb-1">Department</label>
                    <input
                        type="text"
                        value={department}
                        placeholder={"Enter department"}
                        onChange={(e) => setDepartment(e.target.value)}
                        className="border border-gray-300 rounded-md px-3 py-2 w-full"
                    />
                </div>
                <div className="mb-4">
                    <label className="block text-sm font-medium text-gray-700 mb-1">Position</label>
                    <input
                        type="text"
                        value={position}
                        placeholder={"Enter position"}
                        onChange={(e) => setPosition(e.target.value)}
                        className="border border-gray-300 rounded-md px-3 py-2 w-full"
                    />
                </div>
                <div className="mb-4">
                    <label className="block text-sm font-medium text-gray-700 mb-1">ReportsTo</label>
                    <input
                        type="text"
                        value={reportsTo}
                        placeholder={"Enter line manager"}
                        onChange={(e) => setReportsTo(e.target.value)}
                        className="border border-gray-300 rounded-md px-3 py-2 w-full"
                    />
                </div>
                <div className="mb-4">
                    <label className="block text-sm font-medium text-gray-700 mb-1">Contract Type:</label>
                    <select
                        value={contractType}
                        onChange={(e) => setContractType(e.target.value)}
                        className="border border-gray-300 rounded-md px-3 py-2 w-full"
                    >
                        <option value="">Select</option>
                        <option value="fulltime">Full-Time</option>
                        <option value="parttime">Part-Time</option>
                        <option value="contract">Contract</option>
                    </select>
                </div>
                <div className="mb-4">
                    <label className="block text-sm font-medium text-gray-700 mb-1">Official Email</label>
                    <input
                        type="text"
                        value={email}
                        placeholder={"Enter email"}
                        onChange={(e) => setEmail(e.target.value)}
                        className="border border-gray-300 rounded-md px-3 py-2 w-full"
                    />
                </div>
                <div className="mb-4">
                    <label className="block text-sm font-medium text-gray-700 mb-1">Location</label>
                    <input
                        type="text"
                        value={location}
                        placeholder={"Enter work location"}
                        onChange={(e) => setLocation(e.target.value)}
                        className="border border-gray-300 rounded-md px-3 py-2 w-full"
                    />
                </div>
                </div>
                {/* Add input fields for other employee profile data */}
                <div className="flex justify-end">
                    <button
                        className="ml-2 border border-gray-300 rounded-md px-4 py-2"
                        onClick={onClose}
                    >
                        Cancel
                    </button>
                    <button
                        className="bg-blue-700 text-white rounded-md px-4 py-2"
                        onClick={handleCreateProfile}
                    >
                        Create Profile
                    </button>
                </div>
            </div>
        </div>
    );
};

export default AddEmployeeModal;
