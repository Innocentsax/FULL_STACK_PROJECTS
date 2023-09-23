import React from 'react'
import Information from '../Tasker/Information'
import Close from '../Tasker/Close'
import '../Dashboard/ButtonDefault.css'
import './TaskerViewJob.css'

function TaskerViewJob() {
  return (
    <div className="view-job-view-job">
      <div className="view-job-rectangle-312x" />
      <div className="view-job-frame-16483x">
        <p className="view-job-task-details">Task details</p>
        <Close />
      </div>
      <div className="view-job-frame-16520x">
        <div className="view-job-frame-16487x">
          <Information />
          <div className="view-job-frame-16486x">
            <p className="view-job-important">Important</p>
            <div className="view-job-this-job-has-been-ac">
              <p className="view-job-this-job-has-been-ac-text-0x">
                {"This job has been accepted by "}
              </p>
              <p className="view-job-chigozie-emenike-text-1x">
                Chigozie Emenike
              </p>
            </div>
          </div>
        </div>
        <div className="view-job-frame-16519x">
          <div className="view-job-frame-16518x">
            <div className="view-job-frame-16517x">
              <div className="view-job-frame-16488x">
                <p className="view-job-request-date">Request Date</p>
                <p className="view-job-feb-620230750x">
                  Feb 6, 2023 - 07:50 PM
                </p>
              </div>
              <div className="view-job-frame-16489x">
                <p className="view-job-budget-rate">Budget Rate</p>
                <p className="view-job-n8000x">N8,000</p>
              </div>
              <div className="view-job-frame-16490x">
                <p className="view-job-job-type">{"Job Type "}</p>
                <p className="view-job-cleaning">Cleaning</p>
              </div>
              <div className="view-job-frame-16491x">
                <p className="view-job-posted">{"Posted "}</p>
                <p className="view-job-client">Client</p>
              </div>
              <div className="view-job-frame-16492x">
                <p className="view-job-location">{"Location "}</p>
                <p className="view-job-festac-lagos">Festac, Lagos</p>
              </div>
              <div className="view-job-frame-16516x">
                <p className="view-job-task-description">Task Description</p>
                <p className="view-job-lorem-ipsum-dolor-si">
                  Lorem ipsum dolor sit amet
                </p>
              </div>
              <div className="view-job-frame-16493x">
                <p className="view-job-task-duration">Task Duration</p>
                <p className="view-job-hrs">6hrs</p>
              </div>
            </div>
            <div className="view-job-frame-16498x">
              <div className="view-job-frame-16485x">
                <p className="view-job-otherinfo">OTHER INFO</p>
              </div>
              <div className="view-job-frame-16497x">
                <div className="view-job-frame-16496x">
                  <p className="view-job-start-date">Start date</p>
                  <p className="view-job-feb-820231030x">
                    Feb 8, 2023 - 10:30 AM
                  </p>
                </div>
                <div className="view-job-frame-16495x">
                  <p className="view-job-providers-name">Provider’s Name</p>
                  <p className="view-job-chigozie-emenike">Chigozie Emenike</p>
                </div>
              </div>
            </div>
          </div>
          <div className='button-default-button-default'>
            <button className='dashboard-button'>Mark as completed</button>
          </div>
        </div>
      </div>
    </div>
  )
}

export default TaskerViewJob