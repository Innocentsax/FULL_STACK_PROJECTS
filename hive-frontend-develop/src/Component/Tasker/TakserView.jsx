import React from 'react'
import TaskerService from './TaskerService'

function TakserView() {
  return (
    <div>
        <div className="dashboard-home-service">
            <div className="somthing-new-dashboard">
                <div className="dashboard-cleaning-service">
                    <TaskerService />
                </div>
                <div className="dashboard-cleaning-service">
                    <TaskerService />
                </div>
            </div>
            <div className="somthing-new-dashboard">
                <div className="dashboard-cleaning-service">
                    <TaskerService />
                </div>
                <div className="dashboard-cleaning-service">
                    <TaskerService />
                </div>
            </div>
            <div className="somthing-new-dashboard">
                <div className="dashboard-cleaning-service">
                    <TaskerService />
                </div>
                <div className="dashboard-cleaning-service">
                    <TaskerService />
                </div>
            </div>
           

        </div>
    </div>
  )
}

export default TakserView