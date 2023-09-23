import React, {useEffect} from "react";
import Box from "@mui/material/Box";
import Modal from "@mui/material/Modal";
import Button from "@mui/material/Button";
import "../Dashboard/ButtonDefault.css";
import "./ButtonView.css";
import "./ViewJob.css";
import "./Close.css";
import "./Completed.css";
import axios from "axios";
import DoerService from "../../service/DoerService";

function ChildModal(props) {
    const [open, setOpen] = React.useState(false);

    console.log(props?.id);

    const handleAccept = () => {
        const token = localStorage.getItem("token");
        console.log(token);
        DoerService.acceptTask(token, props?.id)
            .then((response) => {
                console.log(response);

                if (response.status === 200) {
                    handleOpen()
                }
            })
            .catch((error) => {
                console.log(error);
            });
    }
    const handleOpen = () => {
        setOpen(true);
    };
    const handleClose = () => {
        setOpen(false);
    };

    return (
        <React.Fragment>
            <div className="modal-button">
                <button className="dashboard-button" onClick={handleAccept}>
                    Accept Job
                </button>
                <button className="dashboard-primary-button" onClick={handleClose}>
                    Decline
                </button>
            </div>
            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="child-modal-title"
                aria-describedby="child-modal-description"
            >
                <Box>
                    <div className="task-completed-task-completed">
                        <div className="task-completed-main-accept">
                            <div className="success-success">
                                <svg
                                    width="100%"
                                    height="100%"
                                    preserveAspectRatio="none"
                                    viewBox="0 0 65 64"
                                    fill="none"
                                    xmlns="http://www.w3.org/2000/svg"
                                >
                                    <path
                                        d="M 32.5 8 C 27.753 8 23.113 9.408 19.166 12.045 C 15.22 14.682 12.143 18.43 10.327 22.816 C 8.51 27.201 8.035 32.027 8.961 36.682 C 9.887 41.338 12.173 45.614 15.529 48.971 C 18.886 52.327 23.162 54.613 27.818 55.539 C 32.473 56.465 37.299 55.99 41.684 54.173 C 46.07 52.357 49.818 49.281 52.455 45.334 C 55.092 41.387 56.5 36.747 56.5 32 C 56.5 25.635 53.971 19.53 49.471 15.029 C 44.97 10.529 38.865 8 32.5 8 Z M 43.82 27.18 L 32.5 38.48 C 31.375 39.604 29.85 40.235 28.26 40.235 C 26.67 40.235 25.145 39.604 24.02 38.48 L 21.18 35.66 C 20.994 35.474 20.846 35.252 20.745 35.009 C 20.644 34.765 20.592 34.504 20.592 34.24 C 20.592 33.976 20.644 33.715 20.745 33.472 C 20.846 33.228 20.994 33.007 21.18 32.82 C 21.367 32.634 21.588 32.486 21.832 32.385 C 22.075 32.284 22.336 32.232 22.6 32.232 C 22.864 32.232 23.125 32.284 23.369 32.385 C 23.612 32.486 23.834 32.634 24.02 32.82 L 26.84 35.66 C 27.026 35.848 27.247 35.996 27.491 36.098 C 27.735 36.199 27.996 36.252 28.26 36.252 C 28.524 36.252 28.785 36.199 29.029 36.098 C 29.273 35.996 29.494 35.848 29.68 35.66 L 40.98 24.34 C 41.167 24.154 41.388 24.006 41.632 23.905 C 41.875 23.804 42.136 23.752 42.4 23.752 C 42.664 23.752 42.925 23.804 43.169 23.905 C 43.412 24.006 43.634 24.154 43.82 24.34 C 44.007 24.527 44.154 24.748 44.255 24.992 C 44.356 25.235 44.408 25.496 44.408 25.76 C 44.408 26.024 44.356 26.285 44.255 26.529 C 44.154 26.772 44.007 26.994 43.82 27.18 Z"
                                        fill="#34A853"
                                    />
                                </svg>
                            </div>
                            <div className="task-completed-task-accept">
                                <p className="task-completed-task-accepted">Task accepted</p>
                                <div className="task-completed-task-last">
                                    <div className="task-completed-task-page">
                                        <p className="task-completed-you-have-accepted-th">
                                            You have accepted this job task starting
                                            <br/>
                                            {props.estimatedTime}
                                        </p>
                                    </div>
                                    <Button
                                        className="button-default-button-close"
                                        onClick={handleClose}
                                    >
                                        CLose
                                    </Button>
                                </div>
                            </div>
                        </div>
                    </div>
                </Box>
            </Modal>
        </React.Fragment>
    );
}

export default function NestedModal(props) {
    const [open, setOpen] = React.useState(false);
    const handleOpen = () => {
        setOpen(true);
    };
    const handleClose = () => {
        setOpen(false);
    };

    return (
        <div className="button-default-button-default">
            <Button className="dashboard-button" onClick={handleOpen}>
                View Task
            </Button>
            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="parent-modal-title"
                aria-describedby="parent-modal-description"
            >
                <Box>
                    <div className="view-job-view-job">
                        <div className="view-job-all-view-job">
                            <div className="view-job-task-details">
                                <p className="view-job-task-details-1">Task details</p>
                                <div className="close-close" onClick={handleClose}>
                                    <svg
                                        width="100%"
                                        height="100%"
                                        preserveAspectRatio="none"
                                        viewBox="0 0 24 24"
                                        fill="none"
                                        xmlns="http://www.w3.org/2000/svg"
                                    >
                                        <path
                                            d="M 15.71 8.29 C 15.617 8.196 15.506 8.122 15.385 8.071 C 15.263 8.02 15.132 7.994 15 7.994 C 14.868 7.994 14.737 8.02 14.615 8.071 C 14.494 8.122 14.383 8.196 14.29 8.29 L 12 10.59 L 9.71 8.29 C 9.522 8.102 9.266 7.996 9 7.996 C 8.734 7.996 8.478 8.102 8.29 8.29 C 8.102 8.478 7.996 8.734 7.996 9 C 7.996 9.266 8.102 9.522 8.29 9.71 L 10.59 12 L 8.29 14.29 C 8.196 14.383 8.122 14.494 8.071 14.616 C 8.02 14.737 7.994 14.868 7.994 15 C 7.994 15.132 8.02 15.263 8.071 15.385 C 8.122 15.507 8.196 15.617 8.29 15.71 C 8.383 15.804 8.494 15.878 8.615 15.929 C 8.737 15.98 8.868 16.006 9 16.006 C 9.132 16.006 9.263 15.98 9.385 15.929 C 9.506 15.878 9.617 15.804 9.71 15.71 L 12 13.41 L 14.29 15.71 C 14.383 15.804 14.494 15.878 14.615 15.929 C 14.737 15.98 14.868 16.006 15 16.006 C 15.132 16.006 15.263 15.98 15.385 15.929 C 15.506 15.878 15.617 15.804 15.71 15.71 C 15.804 15.617 15.878 15.507 15.929 15.385 C 15.98 15.263 16.006 15.132 16.006 15 C 16.006 14.868 15.98 14.737 15.929 14.616 C 15.878 14.494 15.804 14.383 15.71 14.29 L 13.41 12 L 15.71 9.71 C 15.804 9.617 15.878 9.507 15.929 9.385 C 15.98 9.263 16.006 9.132 16.006 9 C 16.006 8.868 15.98 8.737 15.929 8.616 C 15.878 8.494 15.804 8.383 15.71 8.29 V 8.29 Z M 19.07 4.93 C 18.148 3.975 17.044 3.213 15.824 2.689 C 14.604 2.165 13.292 1.889 11.964 1.878 C 10.636 1.866 9.319 2.119 8.09 2.622 C 6.861 3.125 5.745 3.867 4.806 4.806 C 3.867 5.745 3.125 6.862 2.622 8.091 C 2.119 9.32 1.866 10.636 1.877 11.964 C 1.889 13.292 2.165 14.604 2.689 15.824 C 3.213 17.044 3.975 18.148 4.93 19.07 C 5.852 20.025 6.956 20.787 8.176 21.311 C 9.396 21.835 10.708 22.111 12.036 22.123 C 13.364 22.134 14.681 21.881 15.91 21.378 C 17.139 20.876 18.255 20.133 19.194 19.194 C 20.133 18.255 20.875 17.139 21.378 15.91 C 21.881 14.681 22.134 13.364 22.123 12.036 C 22.111 10.708 21.835 9.396 21.311 8.176 C 20.787 6.956 20.025 5.853 19.07 4.93 V 4.93 Z M 17.66 17.66 C 16.352 18.97 14.631 19.785 12.789 19.967 C 10.947 20.15 9.099 19.688 7.559 18.661 C 6.02 17.634 4.884 16.104 4.346 14.334 C 3.808 12.563 3.9 10.66 4.607 8.95 C 5.314 7.24 6.592 5.827 8.224 4.954 C 9.855 4.08 11.74 3.799 13.555 4.159 C 15.37 4.519 17.005 5.497 18.18 6.927 C 19.355 8.356 19.999 10.149 20 12 C 20.004 13.051 19.799 14.093 19.397 15.065 C 18.995 16.036 18.405 16.918 17.66 17.66 V 17.66 Z"
                                            fill="#98A2B3"
                                        />
                                    </svg>
                                </div>
                            </div>
                            <div className="view-job-main-view-job">
                                <div className="view-job-text-body">
                                    <div className="view-job-frame-16515x">
                                        <div className="view-job-frame-16514x">
                                            <p className="view-job-request-date">Request Date</p>
                                            <p className="view-job-feb-620230750x">
                                                {props.taskDuration}
                                            </p>
                                        </div>
                                        <div className="view-job-frame-16513x">
                                            <p className="view-job-budget-rate">Budget Rate</p>
                                            <p className="view-job-n8000x">N{props.budgetRate}</p>
                                        </div>
                                        <div className="view-job-frame-16512x">
                                            <p className="view-job-job-type">{"Job Type "}</p>
                                            <p className="view-job-cleaning">{props.jobType}</p>
                                        </div>
                                        <div className="view-job-frame-16511x">
                                            <p className="view-job-posted">{"Posted "}</p>
                                            <p className="view-job-client">{props.status}</p>
                                        </div>
                                        <div className="view-job-frame-16510x">
                                            <p className="view-job-location">{"Location "}</p>
                                            <p className="view-job-festac-lagos">{props.taskAddress}</p>
                                        </div>
                                        <div className="view-job-frame-16509x">
                                            <p className="view-job-task-duration">Task Duration</p>
                                            <p className="view-job-hrs">{props.estimatedTime}hrs</p>
                                        </div>
                                        <div className="view-job-frame-16515x-1">
                                            <p className="view-job-task-description">
                                                Task Description
                                            </p>
                                            <p className="view-job-resume-ontime">{props.taskDescription}</p>
                                        </div>
                                    </div>
                                    <div className="view-job-frame-16516x">
                                        <div className="view-job-frame-16485x">
                                            <p className="view-job-otherinfo">OTHER INFO</p>
                                        </div>
                                        <div className="view-job-frame-16508x">
                                            <div className="view-job-frame-16506x">
                                                <p className="view-job-start-date">Start date</p>
                                                <p className="view-job-feb-820231030x">
                                                    {props.taskDuration}hrs
                                                </p>
                                            </div>
                                            <div className="view-job-frame-16507x">
                                                <p className="view-job-clients-name">Delivery Address</p>
                                                <p className="view-job-yusuf-taiwo">{props.taskDeliveryAddress}</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div className="view-job-button-body">
                                    <ChildModal
                                    id={props?.taskId}
                                    />
                                </div>
                            </div>
                        </div>
                    </div>
                </Box>
            </Modal>
        </div>
    );
}
