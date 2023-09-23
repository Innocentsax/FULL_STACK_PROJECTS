import Close from '../../close/close';
import Button from './ButtonView';
import './ViewJob.css'

function ViewJob() {
    return (
        <div className="view-job-view-job">

            <div className="view-job-all-view-job">
                <div className="view-job-task-details">
                    <p className="view-job-task-details-1">Task details</p>
                    <Close/>
                </div>
                <div className="view-job-main-view-job">
                    <div className="view-job-text-body">
                        <div className="view-job-frame-16515x">
                            <div className="view-job-frame-16514x">
                                <p className="view-job-request-date">Request Date</p>
                                <p className="view-job-feb-620230750x">
                                    Feb 6, 2023 - 07:50 PM
                                </p>
                            </div>
                            <div className="view-job-frame-16513x">
                                <p className="view-job-budget-rate">Budget Rate</p>
                                <p className="view-job-n8000x">N8,000</p>
                            </div>
                            <div className="view-job-frame-16512x">
                                <p className="view-job-job-type">{"Job Type "}</p>
                                <p className="view-job-cleaning">Cleaning</p>
                            </div>
                            <div className="view-job-frame-16511x">
                                <p className="view-job-posted">{"Posted "}</p>
                                <p className="view-job-client">Client</p>
                            </div>
                            <div className="view-job-frame-16510x">
                                <p className="view-job-location">{"Location "}</p>
                                <p className="view-job-festac-lagos">Festac, Lagos</p>
                            </div>
                            <div className="view-job-frame-16509x">
                                <p className="view-job-task-duration">Task Duration</p>
                                <p className="view-job-hrs">6hrs</p>
                            </div>
                            <div className="view-job-frame-16515x-1">
                                <p className="view-job-task-description">Task Description</p>
                                <p className="view-job-resume-ontime">Resume ontime</p>
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
                                        Feb 8, 2023 - 10:30 AM
                                    </p>
                                </div>
                                <div className="view-job-frame-16507x">
                                    <p className="view-job-clients-name">Client’s Name</p>
                                    <p className="view-job-yusuf-taiwo">Yusuf Taiwo</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="view-job-button-body">
                        <Button/>
                    </div>
                </div>
            </div>

        </div>
    );
}

export default ViewJob;