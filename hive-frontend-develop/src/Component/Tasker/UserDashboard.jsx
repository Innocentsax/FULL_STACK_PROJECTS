import React from 'react'
import './UserDashboard.css'
import {Link} from 'react-router-dom';

function UserDashboard() {
  return (
    <div className="task-created-task-created">
      <p className="task-created-you-can-view-all-you">
        You can view all your tasks created
      </p>
      <p className="task-created-aservice">
        A service provider will accept a task.
      </p>
        <Link to="/tasker/create-job">
            <button className="gotocreate-btn">
                CREATE TASK
            </button>
        </Link>

    </div>
  )
}

export default UserDashboard