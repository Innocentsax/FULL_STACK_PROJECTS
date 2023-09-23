import React from "react";
import "./NotificationBox.css";

const NotificationBox = (props) => {
  const { title, body, elapsedTime } = props;

  return (
      <div className="notification-box">
        <div className="notifications">
          <div className="taskInfo">
            <div className="doer_taskInfo_container">
              <h2 className="doer-name">{title}</h2>
              <h2 className="task-comment">{body}</h2>
            </div>
            <div className="taskStatus">
              <p>{elapsedTime}</p>
            </div>
          </div>
          {/*<hr />*/}
        </div>
      </div>
  );
};

export default NotificationBox;
