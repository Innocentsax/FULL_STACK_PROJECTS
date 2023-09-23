import React from 'react'
import { useState } from 'react'
import Modal from '../Modal/TaskerViewJob'
import '../Dashboard/ServiceText.css'
import '../Dashboard/ButtonDefault.css'

function TaskerService() {
    const [isOpen, setOpen] = useState(false)
  return (
    <div className="service-text-service-text" >
        <div className="service-text-card-text-work">

        <p className="service-text-home-cleaning-servic">Home Cleaning Service</p>
        <div className="service-text-home-text">
        <div className="service-text-frame-16472x">
        <p className="service-text-request-date">Request Date</p>
        <p className="service-text-feb-620200750x">Feb 6, 2020 - 07:50 PM</p>
        </div>
        <div className="service-text-frame-16473x">
        <p className="service-text-budget-rate">Budget Rate</p>
        <p className="service-text-n8000x">N8,000</p>
        </div>
        <div className="service-text-frame-16474x">
        <p className="service-text-job-type">Job Type</p>
        <p className="service-text-cleaning">Cleaning</p>
        </div>
        <div className="service-text-frame-16475x">
        <p className="service-text-posted">Posted</p>
        <p className="service-text-client">Client</p>
        </div>
        <div className="service-text-frame-16476x">
        <p className="service-text-location">Location</p>
        <p className="service-text-festac-lagos">Festac, Lagos</p>
        </div>
        <div className="service-text-frame-16477x">
        <p className="service-text-task-duration">Task Duration</p>
        <p className="service-text-hrs">6hrs</p>
        </div>
        </div>
        </div>
        <div className='button-default-button-default'>
            <button className='dashboard-button'
            onClick={() => setOpen(true)}
            >View Task</button>
        </div>
        {/* <Modal open={isOpen} onClose={() => setOpen(false)} /> */}
        {isOpen && <Modal setOpen={setOpen} />}
        {/* <ButtonDefault /> */}
    </div>
  )
}

export default TaskerService